package com.mrfourfour.ichi.matchmakerproxy.infrastructure

import com.mrfourfour.ichi.matchmakerproxy.application.MatchMakerProxy
import org.apache.http.HttpHeaders

import org.springframework.beans.factory.annotation.Qualifier
import org.springframework.beans.factory.annotation.Value
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.http.HttpStatus
import org.springframework.http.MediaType
import org.springframework.http.ReactiveHttpOutputMessage
import org.springframework.stereotype.Component
import org.springframework.web.reactive.function.BodyInserter
import org.springframework.web.reactive.function.BodyInserters
import org.springframework.web.reactive.function.client.WebClient
import org.springframework.web.reactive.function.client.bodyToMono
import java.lang.IllegalArgumentException
import java.util.*
@Component
class IchiMatchMakerProxy(
    @Qualifier("matchMakerWebClient")
        private val webClient: WebClient
) : MatchMakerProxy {
    override fun request(userId: String){

        val createFormData = createFormData(userId)
        fetchRequest(createFormData)
    }

    private fun fetchRequest(userIdDto: BodyInserter<UserIdDto, ReactiveHttpOutputMessage>) {
        webClient.post()
                .uri{uriBuilder -> uriBuilder.path("/match/request").build()}
                .header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
                .body(userIdDto)
                .retrieve()
                .onStatus({status-> status.is4xxClientError}){
                    it.bodyToMono<Throwable>().map { IllegalArgumentException() }
                }
                .bodyToMono<Unit>()
                .block();
    }



    private fun createFormData(userId: String): BodyInserter<UserIdDto, ReactiveHttpOutputMessage> {
        val userIdDto = UserIdDto(userId)
        return BodyInserters.fromValue(userIdDto)
    }

}



data class UserIdDto(
        val userId: String
)

@Configuration
class MatchMakerWebClientConfig{

    @Bean
    fun matchMakerWebClient(
           @Value("\${matchmaker.url}")
           matchMakerUrl: String
    ): WebClient {

        return WebClient
                .builder()
                .baseUrl(matchMakerUrl)
                .build()
    }

}

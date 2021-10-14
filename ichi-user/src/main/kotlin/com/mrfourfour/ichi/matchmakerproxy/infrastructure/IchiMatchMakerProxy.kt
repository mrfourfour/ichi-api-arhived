package com.mrfourfour.ichi.matchmakerproxy.infrastructure

import com.mrfourfour.ichi.matchmakerproxy.application.MatchMakerProxy
import org.apache.http.HttpHeaders

import org.springframework.beans.factory.annotation.Qualifier
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.http.HttpStatus
import org.springframework.http.MediaType
import org.springframework.stereotype.Component
import org.springframework.web.reactive.function.BodyInserters
import org.springframework.web.reactive.function.client.WebClient
import org.springframework.web.reactive.function.client.bodyToMono
import java.lang.IllegalArgumentException


@Component
class IchiMatchMakerProxy(
    @Qualifier("matchMakerWebClient")
        private val webClient: WebClient
) : MatchMakerProxy {
    override fun request(userId: String): HttpStatus? {
        val createFormData = createFormData(userId)
        return fetchRequest(createFormData)
    }

    private fun fetchRequest(userId: BodyInserters.FormInserter<String>): HttpStatus? =
        webClient.post()
                .uri{uriBuilder -> uriBuilder.build()}
                .header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_FORM_URLENCODED_VALUE)
                .body(userId)
                .retrieve()
                .onStatus({status-> status.is4xxClientError}){
                    it.bodyToMono<Throwable>().map { IllegalArgumentException() }
                }
                .bodyToMono<HttpStatus>(HttpStatus::class.java)
                .block();


    private fun createFormData(userId: String): BodyInserters.FormInserter<String> {
        return BodyInserters.fromFormData("user_id", userId)

    }



}

@Configuration
class MatchMakerWebClientConfig{

    @Bean
    fun matchMakerWebClient(
    ): WebClient {
        val baseUrl = getBaseUrl()
        return WebClient
                .builder()
                .baseUrl(baseUrl)
                .build()
    }

    private fun getBaseUrl() = "172.30.1.12:port/match/request"

}

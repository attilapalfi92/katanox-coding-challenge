package com.katanox.api.search

import com.katanox.api.log.LogInterface
import org.springframework.beans.factory.annotation.Value
import org.springframework.http.HttpStatus
import org.springframework.http.MediaType
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import java.util.List

@RestController
@RequestMapping("search")
class SearchController(
    @Value("\${env}")
    private val environment: String,
    private val logWriterService: LogInterface,
    private val searchService: SearchService,
) {

    @PostMapping(
        path = ["/"],
        consumes = [MediaType.APPLICATION_JSON_VALUE],
        produces = [MediaType.APPLICATION_JSON_VALUE]
    )
    fun search(@RequestBody request: SearchRequest): ResponseEntity<SearchResponse> {
        val response: SearchResponse = searchService.search(request)
        val result = ArrayList(List.of<Any>())
        if (environment == "local") {
            logWriterService.logStringToConsoleOutput(result.toString())
        }
        TODO("finish")
//        return ResponseEntity(SearchResponse(), HttpStatus.ACCEPTED)
    }
}
package com.katanox.api.search

import org.springframework.http.HttpStatus
import org.springframework.http.MediaType
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("search")
class SearchController(
    private val searchService: SearchService,
) {

    @PostMapping(
        path = ["/"],
        consumes = [MediaType.APPLICATION_JSON_VALUE],
        produces = [MediaType.APPLICATION_JSON_VALUE]
    )
    fun search(@RequestBody request: SearchRequest): ResponseEntity<SearchResponse> {
        val response: SearchResponse = searchService.search(request)
        return ResponseEntity(response, HttpStatus.ACCEPTED)
    }
}
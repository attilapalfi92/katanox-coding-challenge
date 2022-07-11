package com.katanox.api.log

import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.stereotype.Service

@Service
class LogWriterService : LogInterface {
    private val log: Logger = LoggerFactory.getLogger(LogWriterService::class.java)

    override fun logStringToConsoleOutput(o: String) {
        log.warn(o)
    }
}
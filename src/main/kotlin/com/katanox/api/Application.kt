package com.katanox.api

import org.springframework.boot.SpringApplication
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.autoconfigure.r2dbc.R2dbcAutoConfiguration
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration
import org.springframework.context.annotation.PropertySource
import java.util.*

@SpringBootApplication(exclude = [SecurityAutoConfiguration::class, R2dbcAutoConfiguration::class])
@PropertySource("classpath:application.properties")
class Application

fun main(args: Array<String>) {
    SpringApplication.run(Application::class.java, *args)
    TimeZone.setDefault(TimeZone.getTimeZone("Etc/UTC"))
}
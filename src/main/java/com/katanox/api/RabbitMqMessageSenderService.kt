package com.katanox.api

import org.springframework.amqp.core.AmqpTemplate
import org.springframework.beans.factory.annotation.Value
import org.springframework.stereotype.Component

@Component
class RabbitMqMessageSenderService(
    private val rabbitTemplate: AmqpTemplate,

    @Value("\${katanox.rabbitmq.exchange}")
    private val exchange: String,

    @Value("\${katanox.rabbitmq.routingkey}")
    private val routingKey: String,
) {

    fun sendMessage(any: Any) {
        rabbitTemplate.convertAndSend(exchange, routingKey, any)
    }
}
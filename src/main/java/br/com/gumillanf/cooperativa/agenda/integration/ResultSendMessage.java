package br.com.gumillanf.cooperativa.agenda.integration;

import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
public class ResultSendMessage {

    @Value("${spring.rabbitmq.exchange.result}")
    String exchange;

    @Value("${spring.rabbitmq.routingkey.result}")
    String routingKey;

    public final RabbitTemplate rabbitTemplate;

    @Autowired
    public ResultSendMessage(RabbitTemplate rabbitTemplate) {
        this.rabbitTemplate = rabbitTemplate;
    }

    public void sendMessage(ResultEvent event) {
        rabbitTemplate.convertAndSend(exchange, routingKey, event);
    }

}

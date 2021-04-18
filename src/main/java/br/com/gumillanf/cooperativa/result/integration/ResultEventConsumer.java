package br.com.gumillanf.cooperativa.result.integration;

import br.com.gumillanf.cooperativa.agenda.AgendaQuery;
import br.com.gumillanf.cooperativa.config.exception.ResourceNotFoundException;
import br.com.gumillanf.cooperativa.result.ResultCommand;
import lombok.AllArgsConstructor;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.amqp.rabbit.annotation.RabbitListenerConfigurer;
import org.springframework.amqp.rabbit.listener.RabbitListenerEndpointRegistrar;
import org.springframework.stereotype.Component;


@Component
@AllArgsConstructor
public class ResultEventConsumer implements RabbitListenerConfigurer {

    private final ResultCommand resultCommand;
    private final AgendaQuery agendaQuery;

    @Override
    public void configureRabbitListeners(RabbitListenerEndpointRegistrar rabbitListenerEndpointRegistrar) {
    }

    @RabbitListener(queues = "${spring.rabbitmq.queue.result}")
    public void receivedMessage(ResultEvent event) throws ResourceNotFoundException {
        resultCommand.create(event.to(agendaQuery.findOne(event.getAgendaId())));
    }

}

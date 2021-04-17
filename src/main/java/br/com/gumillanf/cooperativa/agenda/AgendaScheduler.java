package br.com.gumillanf.cooperativa.agenda;

import lombok.AllArgsConstructor;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
@AllArgsConstructor
class AgendaScheduler {

    private final AgendaCommand command;

    @Scheduled(cron = "${app.schedule.finalized}")
    public void processAgendaFinalized() {
        command.finish();
    }

}

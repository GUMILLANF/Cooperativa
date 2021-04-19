package br.com.gumillanf.cooperativa.agenda.integration;

import br.com.gumillanf.cooperativa.agenda.Agenda;
import br.com.gumillanf.cooperativa.support.TestSupport;
import br.com.six2six.fixturefactory.Fixture;
import org.junit.Test;

import static br.com.gumillanf.cooperativa.templates.FixtureTemplates.*;
import static org.junit.Assert.assertEquals;

public class ResultCreateEventTest extends TestSupport {

    private static ResultCreateEvent resultCreateEvent;

    @Override
    public void init() {
        resultCreateEvent = Fixture.from(ResultCreateEvent.class).gimme(VALID.name());
    }

    @Test
    public void should_return_result_create_event_class() {
        Agenda agenda = Fixture.from(Agenda.class).gimme(FINISHED.name());
        assertEquals(expected(agenda), ResultCreateEvent.of(agenda, resultCreateEvent.getAmountYes(),
                resultCreateEvent.getAmountNo(), resultCreateEvent.getFinalResult()));
    }

    private ResultCreateEvent expected(Agenda agenda) {
        return ResultCreateEvent.builder().agendaId(agenda.getId()).amountYes(resultCreateEvent.getAmountYes())
                .amountNo(resultCreateEvent.getAmountNo()).finalResult(resultCreateEvent.getFinalResult()).build();
    }
}

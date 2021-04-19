package br.com.gumillanf.cooperativa.result;

import br.com.gumillanf.cooperativa.agenda.Agenda;
import br.com.gumillanf.cooperativa.support.TestSupport;
import br.com.six2six.fixturefactory.Fixture;
import org.junit.Test;

import static br.com.gumillanf.cooperativa.templates.FixtureTemplates.VALID;
import static org.junit.Assert.assertEquals;

public class ResultTest extends TestSupport {

    private Result result;

    @Override
    public void init() {
        result = Fixture.from(Result.class).gimme(VALID.name());
    }

    @Test
    public void should_get_agenda_id() {
        Agenda agenda = result.getAgenda();
        assertEquals(result.getAgendaId(), agenda.getId());
    }

}

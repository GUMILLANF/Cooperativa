package br.com.gumillanf.cooperativa.agenda;

import br.com.gumillanf.cooperativa.support.TestSupport;
import br.com.six2six.fixturefactory.Fixture;
import org.junit.Test;

import static br.com.gumillanf.cooperativa.templates.FixtureTemplates.*;
import static org.junit.Assert.*;

public class AgendaTest extends TestSupport {

    private static final String DESCRIPTION = "TESTE";

    @Override
    public void init() {
        // not to do
    }

    @Test
    public void should_start_voting_agenda() {
        assertEquals(Agenda.of(DESCRIPTION).start(1).getStatus(), AgendaStatus.VOTING);
    }

    @Test
    public void not_should_start_voting_agenda() {
        Agenda agenda = Fixture.from(Agenda.class).gimme(FINISHED.name());
        assertEquals(agenda.start(1).getStatus(), AgendaStatus.FINISHED);
    }

    @Test
    public void should_finish_voting_agenda() {
        Agenda agenda = Fixture.from(Agenda.class).gimme(STARTED.name());
        assertEquals(agenda.finish().getStatus(), AgendaStatus.FINISHED);
    }

    @Test
    public void not_should_finish_voting_agenda() {
        assertEquals(Agenda.of(DESCRIPTION).finish().getStatus(), AgendaStatus.WAITING);
    }

    @Test
    public void shoul_return_that_is_not_open_to_voting() {
        Agenda agenda = Fixture.from(Agenda.class).gimme(FINISHED.name());
        assertFalse(agenda.isOpen());
    }

    @Test
    public void should_build_agenda_class() {
        assertEquals(expected(), Agenda.of(DESCRIPTION));
    }

    private Agenda expected() {
        return Agenda.builder().description(DESCRIPTION).status(AgendaStatus.WAITING).build();
    }


}

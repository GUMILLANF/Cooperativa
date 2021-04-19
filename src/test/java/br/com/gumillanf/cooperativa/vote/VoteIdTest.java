package br.com.gumillanf.cooperativa.vote;

import br.com.gumillanf.cooperativa.support.TestSupport;
import br.com.six2six.fixturefactory.Fixture;
import org.junit.Test;

import static br.com.gumillanf.cooperativa.templates.FixtureTemplates.VALID;
import static org.junit.Assert.assertEquals;

public class VoteIdTest extends TestSupport {

    @Override
    public void init() {
        // not to do
    }

    @Test
    public void should_get_agenda_id() {
        VoteId voteId = Fixture.from(VoteId.class).gimme(VALID.name());
        Long agendaId = voteId.getAgendaId();
        assertEquals(voteId.getAgendaId(), agendaId);
    }

}

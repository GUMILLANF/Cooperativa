package br.com.gumillanf.cooperativa.vote;

import br.com.gumillanf.cooperativa.agenda.Agenda;
import br.com.gumillanf.cooperativa.support.TestSupport;
import br.com.gumillanf.cooperativa.vote.web.VoteResource;
import br.com.six2six.fixturefactory.Fixture;
import org.junit.Test;

import java.time.LocalDateTime;

import static br.com.gumillanf.cooperativa.templates.FixtureTemplates.STARTED;
import static br.com.gumillanf.cooperativa.templates.FixtureTemplates.VALID;
import static org.junit.Assert.assertEquals;

public class VoteTest extends TestSupport {

    @Override
    public void init() {
        // not to do
    }

    @Test
    public void should_return_vote_class() {
        Agenda agenda = Fixture.from(Agenda.class).gimme(STARTED.name());
        VoteResource voteResource = Fixture.from(VoteResource.class).gimme(VALID.name());
        Vote voteExpected = expected(agenda, voteResource);
        Vote voteReturn = Vote.of(voteResource, agenda);
        assertEquals(voteExpected.getId(), voteReturn.getId());
        assertEquals(voteExpected.getResponse(), voteReturn.getResponse());
    }

    private Vote expected(Agenda agenda, VoteResource voteResource) {
        return Vote.builder().id(VoteId.builder().agenda(agenda).associetedCpf(voteResource.getAssocietedCpf()).build())
                .date(LocalDateTime.now()).response(voteResource.getResponse()).build();
    }
}

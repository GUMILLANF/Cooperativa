package br.com.gumillanf.cooperativa.templates;

import br.com.gumillanf.cooperativa.vote.Vote;
import br.com.gumillanf.cooperativa.vote.VoteId;
import br.com.gumillanf.cooperativa.vote.VoteResponse;
import br.com.six2six.fixturefactory.Fixture;
import br.com.six2six.fixturefactory.Rule;
import br.com.six2six.fixturefactory.loader.TemplateLoader;

import java.time.LocalDateTime;

import static br.com.gumillanf.cooperativa.templates.FixtureTemplates.VALID;

public class VoteTemplateLoader implements TemplateLoader {

    @Override
    public void load() {
        Fixture.of(Vote.class).addTemplate(VALID.name(), new Rule() {
            {
                add("id", one(VoteId.class, VALID.name()));
                add("date", LocalDateTime.now());
                add("response", random(VoteResponse.YES, VoteResponse.NO));
            }
        });
    }
}

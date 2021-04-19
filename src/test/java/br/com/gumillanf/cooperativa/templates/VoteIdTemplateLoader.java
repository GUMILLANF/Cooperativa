package br.com.gumillanf.cooperativa.templates;

import br.com.gumillanf.cooperativa.agenda.Agenda;
import br.com.gumillanf.cooperativa.vote.VoteId;
import br.com.six2six.fixturefactory.Fixture;
import br.com.six2six.fixturefactory.Rule;
import br.com.six2six.fixturefactory.loader.TemplateLoader;

import static br.com.gumillanf.cooperativa.templates.FixtureTemplates.*;

public class VoteIdTemplateLoader implements TemplateLoader {

    @Override
    public void load() {
        Fixture.of(VoteId.class).addTemplate(VALID.name(), new Rule() {
            {
                add("agenda", one(Agenda.class, STARTED.name()));
                add("associetedCpf", random("63312830052", "27343247093", "02015873007"));
            }
        });
    }
}

package br.com.gumillanf.cooperativa.templates;

import br.com.gumillanf.cooperativa.vote.VoteResponse;
import br.com.gumillanf.cooperativa.vote.web.VoteResource;
import br.com.six2six.fixturefactory.Fixture;
import br.com.six2six.fixturefactory.Rule;
import br.com.six2six.fixturefactory.loader.TemplateLoader;

import static br.com.gumillanf.cooperativa.templates.FixtureTemplates.VALID;

public class VoteResourceTemplateLoader implements TemplateLoader {

    @Override
    public void load() {
        Fixture.of(VoteResource.class).addTemplate(VALID.name(), new Rule() {
            {
                add("agendaId", random(Long.class, range(1, 20)));
                add("associetedCpf", random("63312830052", "27343247093", "02015873007"));
                add("response", random(VoteResponse.YES, VoteResponse.NO));
            }
        });
    }
}

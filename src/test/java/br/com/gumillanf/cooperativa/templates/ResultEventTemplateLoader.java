package br.com.gumillanf.cooperativa.templates;

import br.com.gumillanf.cooperativa.result.FinalResult;
import br.com.gumillanf.cooperativa.result.integration.ResultEvent;
import br.com.six2six.fixturefactory.Fixture;
import br.com.six2six.fixturefactory.Rule;
import br.com.six2six.fixturefactory.loader.TemplateLoader;

import static br.com.gumillanf.cooperativa.templates.FixtureTemplates.VALID;

public class ResultEventTemplateLoader implements TemplateLoader {

    @Override
    public void load() {
        Fixture.of(ResultEvent.class).addTemplate(VALID.name(), new Rule() {
            {
                add("agendaId", random(Long.class, range(1, 100)));
                add("finalResult", random(FinalResult.APPROVED_AGENDA.name(),
                        FinalResult.UNAPPROVED_AGENDA.name(), FinalResult.UNDEFINED.name()));
                add("amountYes", random(Integer.class, range(1, 20)));
                add("amountNo", random(Integer.class, range(1, 20)));
            }
        });
    }
}

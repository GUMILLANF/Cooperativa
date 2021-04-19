package br.com.gumillanf.cooperativa.templates;

import br.com.gumillanf.cooperativa.agenda.integration.ResultCreateEvent;
import br.com.gumillanf.cooperativa.result.FinalResult;
import br.com.six2six.fixturefactory.Fixture;
import br.com.six2six.fixturefactory.Rule;
import br.com.six2six.fixturefactory.loader.TemplateLoader;

import static br.com.gumillanf.cooperativa.templates.FixtureTemplates.*;

public class ResultCreateEventTemplateLoader implements TemplateLoader {

    @Override
    public void load() {
        Fixture.of(ResultCreateEvent.class).addTemplate(VALID.name(), new Rule() {
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

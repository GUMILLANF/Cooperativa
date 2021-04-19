package br.com.gumillanf.cooperativa.templates;

import br.com.gumillanf.cooperativa.agenda.web.AgendaResource;
import br.com.six2six.fixturefactory.Fixture;
import br.com.six2six.fixturefactory.Rule;
import br.com.six2six.fixturefactory.loader.TemplateLoader;

import static br.com.gumillanf.cooperativa.templates.FixtureTemplates.VALID;

public class AgendaResourceTemplateLoader implements TemplateLoader {

    @Override
    public void load() {
        Fixture.of(AgendaResource.class).addTemplate(VALID.name(), new Rule() {
            {
                add("description", random("Teste 1", "Teste 2", "Teste 3"));
            }
        });
    }

}

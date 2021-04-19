package br.com.gumillanf.cooperativa.vote.web;

import br.com.gumillanf.cooperativa.commons.core.BeanUtil;
import br.com.gumillanf.cooperativa.config.exception.handler.ExceptionHandler;
import br.com.gumillanf.cooperativa.vote.Vote;
import br.com.gumillanf.cooperativa.vote.VoteCommand;
import br.com.six2six.fixturefactory.Fixture;
import br.com.six2six.fixturefactory.loader.FixtureFactoryLoader;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import io.restassured.module.mockmvc.RestAssuredMockMvc;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.web.PageableHandlerMethodArgumentResolver;
import org.springframework.hateoas.mediatype.MessageResolver;
import org.springframework.hateoas.mediatype.hal.CurieProvider;
import org.springframework.hateoas.mediatype.hal.Jackson2HalModule;
import org.springframework.hateoas.server.core.DefaultLinkRelationProvider;
import org.springframework.http.MediaType;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.test.web.servlet.setup.StandaloneMockMvcBuilder;

import static br.com.gumillanf.cooperativa.templates.FixtureTemplates.VALID;
import static java.util.Collections.singletonList;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@DirtiesContext
@RunWith(SpringRunner.class)
@SuppressWarnings("unchecked")
@SpringBootTest(classes = { VoteWebBase.Config.class })
public class VoteWebBase {

    @MockBean
    private DefaultLinkRelationProvider relProvider;

    @MockBean
    private VoteCommand voteCommand;

    @BeforeClass
    public static void setUp() {
        FixtureFactoryLoader.loadTemplates("br.com.gumillanf.cooperativa.templates");
    }

    @Before
    public void setup() throws Throwable {
        Vote vote = Fixture.from(Vote.class).gimme(VALID.name());

        create(vote);

        StandaloneMockMvcBuilder standaloneMockMvcBuilder = MockMvcBuilders
                .standaloneSetup(new VoteRestService(voteCommand))
                .setMessageConverters(hal()).setControllerAdvice(new ExceptionHandler() {
                }).setCustomArgumentResolvers(
                        new PageableHandlerMethodArgumentResolver());
        RestAssuredMockMvc.standaloneSetup(standaloneMockMvcBuilder);
    }

    private void create(Vote vote) throws Throwable {
        when(voteCommand.create(any(VoteResource.class))).thenReturn(vote);
    }

    private MappingJackson2HttpMessageConverter hal() {
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.registerModule(new JavaTimeModule());
        objectMapper.registerModule(new Jackson2HalModule());
        objectMapper.disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);
        objectMapper.setHandlerInstantiator(new Jackson2HalModule.HalHandlerInstantiator(relProvider,
                CurieProvider.NONE, MessageResolver.DEFAULTS_ONLY));
        MappingJackson2HttpMessageConverter halConverter = new MappingJackson2HttpMessageConverter();
        halConverter.setObjectMapper(objectMapper);
        halConverter.setSupportedMediaTypes(singletonList(MediaType.APPLICATION_JSON));
        return halConverter;
    }

    @Configuration
    static class Config {

        @Bean
        public BeanUtil beanUtil() {
            return new BeanUtil();
        }

    }

}

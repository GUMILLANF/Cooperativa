package br.com.gumillanf.cooperativa.support;

import br.com.six2six.fixturefactory.loader.FixtureFactoryLoader;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.runner.RunWith;
import org.mockito.InOrder;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.MockitoJUnitRunner;
import org.mockito.stubbing.OngoingStubbing;

import java.lang.reflect.Constructor;
import java.lang.reflect.Modifier;

@RunWith(MockitoJUnitRunner.class)
public abstract class TestSupport {

    @BeforeClass
    public static void setUp() {
        FixtureFactoryLoader.loadTemplates("br.com.gumillanf.cooperativa");
    }

    @Before
    public void setUpTest() {
        MockitoAnnotations.initMocks(this);
        this.init();
    }

    public abstract void init();

    public InOrder inOrder(Object... mocks) {
        return Mockito.inOrder(mocks);
    }

    public <T> OngoingStubbing<T> when(T methodCall) {
        return Mockito.when(methodCall);
    }

    public <T> T verify(T mock) {
        return Mockito.verify(mock);
    }

    public <T> T verify(T mock, int wantedNumberOfInvocations) {
        return Mockito.verify(mock, Mockito.times(wantedNumberOfInvocations));
    }

    public <T> boolean testPrivateConstructor(Class<T> clazz) {

        boolean hasPrivateConstructor = false;
        Constructor<?>[] declaredConstructors = clazz.getDeclaredConstructors();
        if (declaredConstructors != null && declaredConstructors[0] != null) {
            hasPrivateConstructor = Modifier.isPrivate(declaredConstructors[0].getModifiers());
        }

        return hasPrivateConstructor;
    }

}

package com.scaffold.mockito;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.powermock.api.mockito.PowerMockito;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.when;

@RunWith(PowerMockRunner.class)
@PrepareForTest(StaticMethod.class)
public class MockitoPowerMockTest {

    @Test
    public void staticMethodMock() {
        // not mocked
        assertEquals("hello", StaticMethod.sayHello());
        // mocked
        PowerMockito.mockStatic(StaticMethod.class);
        when(StaticMethod.sayHello()).thenReturn("mock hello");
        assertEquals("mock hello", StaticMethod.sayHello());
    }
}

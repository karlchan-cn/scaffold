package com.scaffold.mockito;


import com.google.common.base.Stopwatch;
        import org.junit.Rule;
        import org.junit.Test;
        import org.junit.runner.RunWith;
        import org.mockito.*;
        import org.mockito.invocation.InvocationOnMock;
        import org.mockito.junit.MockitoJUnit;
        import org.mockito.junit.MockitoRule;
        import org.powermock.api.mockito.PowerMockito;
        import org.powermock.core.classloader.annotations.PrepareForTest;
        import org.powermock.modules.junit4.PowerMockRunner;

        import java.util.ArrayList;
        import java.util.List;
        import java.util.function.Function;

        import static org.junit.Assert.assertEquals;
        import static org.junit.Assert.assertNotNull;
        import static org.mockito.BDDMockito.given;
        import static org.mockito.Mockito.*;

//

/**
 * Mockitto测试demo,
 * 主要内容参考
 *
 * @see <a href="https://javadoc.io/doc/org.mockito/mockito-core/latest/org/mockito/Mockito.html">mockito</a>
 * @see <a href="https://github.com/powermock/powermock/wiki/Mockito">powermock</a>
 */
//@RunWith(MockitoJUnitRunner.class)
@RunWith(PowerMockRunner.class)
@PrepareForTest(StaticMethod.class)
public class MockitoTest {
    //@Captor, @Spy, @InjectMocks自动生效
    @Rule
    public MockitoRule rule = MockitoJUnit.rule();

    @Mock
    private List<String> mock;

    @InjectMocks
    private InnerService service;

    @Mock
    private Function<String, String> function;

    @Test
    public void staticMethodMock() {
        PowerMockito.mockStatic(StaticMethod.class);
        when(StaticMethod.sayHello()).thenReturn("mock hello");
        assertEquals("mock hello", StaticMethod.sayHello());
    }

    private static class InnerService {

        public InnerService(Function<String, String> func) {
            this.functionField = func;
        }

        /**
         * 1. {"flag":true}--
         * 2. {"flag":false} ---
         * 这个就是中间件.
         */
        private Function<String, String> functionField;
    }

    @Test
    public void injectMockDemo() {
        //do return when.
        doReturn("empty String").when(function).apply(anyString());
        assertEquals("empty String", service.functionField.apply(""));
    }



    @Test
    public void verifyDemo() {
        assertEquals(0, mock.size());
        // verify invocation
        verify(mock).size();
        // verify invocation times
        verify(mock, times(1)).size();
        verify(mock, atLeastOnce()).size();
        verify(mock, atMost(1)).size();
    }

    @Test
    public void inOrderDemo() {
        List sigMock = mock(List.class);
        sigMock.add("first");
        sigMock.add("second");
        InOrder inOrder = inOrder(sigMock);
        // 校验顺序执行
        inOrder.verify(sigMock).add("first");
        inOrder.verify(sigMock).add("second");
    }

    @Test
    public void neverHappen() {
        List<String> sigMock = mock(List.class);
        sigMock.add("first");
        verify(sigMock, never()).add("test");
    }

    @Test
    public void consecutiveReturn() {
        List<String> sigMock = mock(List.class);
        doReturn("one", "two", "three").when(sigMock).get(anyInt());
        assertEquals("one", sigMock.get(anyInt()));
        assertEquals("two", sigMock.get(anyInt()));
        assertEquals("three", sigMock.get(anyInt()));
        assertEquals("three", sigMock.get(anyInt()));
    }

    @Test
    public void answerDemo() {
        List sigMock = mock(List.class);
        doAnswer((InvocationOnMock var) -> {
            Integer argument = var.getArgument(0);
            return argument > 2 ? ">2" : "<=2";
        }).when(sigMock).get(anyInt());
        assertEquals(">2", sigMock.get(3));
        assertEquals("<=2", sigMock.get(2));
    }

    @Test
    public void answerDemoWithSleep() {
        List sigMock = mock(List.class);

        doAnswer((InvocationOnMock var) -> {
            Integer argument = var.getArgument(0);
            Thread.sleep(1000);
            return argument > 2 ? ">2" : "<=2";
        }).when(sigMock).get(anyInt());
        Stopwatch stopwatch = Stopwatch.createStarted();
        assertEquals(">2", sigMock.get(3));
        System.out.println(String.format("eclepse:%s", stopwatch.elapsed().toString()));
        assertEquals("<=2", sigMock.get(2));
        System.out.println(String.format("eclepse:%s", stopwatch.elapsed().toString()));
    }

    @Test
    public void timeoutDemo() {
        //spy 监控对象使用,有真实调用
        List<Integer> spyList = mock(List.class);
        Runnable ONE_SECOND_RUNNABLE = () -> {
            try {
                Thread.sleep(1000L);
                spyList.addAll(null);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        };
        new Thread(ONE_SECOND_RUNNABLE).start();
        verify(spyList, timeout(2000)).addAll(any());

    }

    @Test
    public void abstractDemo() {
        Function spy = spy(Function.class);
        doAnswer((InvocationOnMock var1) -> "null result").when(spy).apply(any());
        assertEquals("null result", spy.apply("test"));
    }

    @Test
    public void doCapture() {
        List<String> sigMock = mock(List.class);
        ArgumentCaptor<Integer> captor = ArgumentCaptor.forClass(Integer.class);
        doReturn("any result!").when(sigMock).get(anyInt());
        assertEquals("any result!", sigMock.get(0));
        //catch the parameter passed to mock-object
        verify(sigMock).get(captor.capture());
        assertEquals(0, captor.getValue().intValue());
    }
    //doReturn(Object)
    //
    //doThrow(Throwable...)
    //
    //doThrow(Class)
    //
    //doAnswer(Answer)
    //
    //doNothing()
    //
    //doCallRealMethod()

    @Test
    public void spyDemo() {
        //spy 监控对象使用,有真实调用
        List<String> list = new ArrayList<>();
        List<String> spyList = spy(list);
        // 列表被方法真实使用
        spyList.add("test");
        // 真实方法调用
        assertEquals(1, spyList.size());
        // 控制响应-Stubbing a Spy
        doReturn(100).when(spyList).size();
        list.add("one");
        list.add("two");
        list.add("three");
        //Impossible: real method is called so spy.get(0) throws IndexOutOfBoundsException (the list is yet empty)
        when(spyList.get(0)).thenReturn("foo");
        assertEquals("foo", spyList.get(0));
        // assertEquals("two", spyList.get(1));
        // 若spyList被方法体使用,则返回100
        assertEquals(100, spyList.size());
    }

    @Test
    public void whenDemo() {
        List<String> mockList = mock(List.class);
        // 非真实调用
        mockList.add("one");
        mockList.add("two");
        // error
        try {
            assertEquals(1, mockList.size());
        } catch (AssertionError e) {
            //print error.
            System.out.println("assertEquals error");
        }
        verify(mockList, times(0)).add("three");
        verify(mockList, times(1)).add("one");
        verify(mockList, times(1)).add("two");
        // mock behavior
        doReturn(2).when(mockList).size();
        assertEquals(2, mockList.size());

        //when(mockList.size()).thenCallRealMethod();
        //when(mockList.add(anyString())).thenCallRealMethod();


    }

    @Test
    public void mockingDetail() {
        List<String> mock = mock(List.class);
        MockingDetails details = mockingDetails(mock);
        details.getInvocations();
        details.getStubbings();
        assertNotNull(mock);
    }

    @Test
    public void bdd() {
        List<Integer> mock = mock(List.class);
        given(mock.get(anyInt())).willAnswer((InvocationOnMock iom) -> 1);
        assertEquals(1, mock.get(10).intValue());
        verify(mock).get(anyInt());
    }

    @Test
    public void incubatingDemo() {
        // 普通mock
        FinalClass mock = mock(FinalClass.class);
        when(mock.sayHello()).thenReturn("no hello");
        assertEquals("no hello", mock.sayHello());
        // power mocked
        PowerMockito.mockStatic(FinalClass.class);
        mock = PowerMockito.mock(FinalClass.class);
        PowerMockito.when(mock.sayHello()).thenReturn("power mock hello");
        assertEquals("power mock hello", mock.sayHello());

    }

    //private
    private static final class FinalClass {
        /**
         * hello.
         *
         * @return "hello"
         */
        public String sayHello() {
            return "hello";
        }
    }

}

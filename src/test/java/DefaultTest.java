import org.junit.Test;

public class DefaultTest {

    // Default test to test build on travis ci
    // TODO: remove this test when code starts to be integrated
    @Test
    public void defaultMethod1Test() {
        Default defaultClass = new Default();
        defaultClass.method1();
    }
}

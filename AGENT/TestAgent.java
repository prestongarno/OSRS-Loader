import java.lang.instrument.Instrumentation;

/**
 *
 * @author Preston Garno
 */
public class TestAgent {
    public static void premain(String agentArgument, Instrumentation instrument) {
        System.out.println("Test Java Agent");
    }
}

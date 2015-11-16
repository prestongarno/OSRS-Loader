package Agent;


import java.lang.instrument.Instrumentation;

public class TestAgent {
    public static void premain(String agentArguments, Instrumentation instrumentation) {
        System.out.println("Java Agent Loaded!");
        SimpleClassTransformer transformer = new SimpleClassTransformer();
        instrumentation.addTransformer(transformer);
    }
}

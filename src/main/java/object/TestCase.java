package object;

import java.util.List;

public class TestCase {
    public String streamName;
    public String featureName;
    public String testName;
    public String priority;
    public String authorTK;
    public String statusAT;
    public String typeTestSuite;

    public List<Step> steps;

    public TestCase(String streamName, String featureName, String testName, String priority, String authorTK, String statusAT, String typeTestSuite, List<Step> steps) {
        this.streamName = streamName;
        this.featureName = featureName;
        this.testName = testName;
        this.priority = priority;
        this.authorTK = authorTK;
        this.statusAT = statusAT;
        this.typeTestSuite = typeTestSuite;
        this.steps = steps;
    }
}

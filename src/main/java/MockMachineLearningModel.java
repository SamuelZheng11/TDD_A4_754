import java.util.*;

public class MockMachineLearningModel implements IMachineLearningModel {
    private String branchName = "";
    private Map<List<Integer>, AnomalyType> anomalies = new HashMap<List<Integer>, AnomalyType>();

    public void loadBranch(IBranch targetBranch) {
        branchName = targetBranch.getName();
    }

    public void runModelOnBranch() {

    }

    public Map<List<Integer>, AnomalyType> getResults() {
        return null;
    }
}

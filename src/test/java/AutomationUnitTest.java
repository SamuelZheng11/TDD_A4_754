import org.junit.Test;

import java.io.*;
import java.net.URISyntaxException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.fail;

public class AutomationUnitTest {

    /**
     * Requirement #: 5
     */
    @Test
    public void noLintingNeededToPassTest() {
        CommandLineRunner cmd= new CommandLineRunner();
        cmd.setCommand("npm run lint;");
        IBranch branch = new GitBranch("noLintNeededBranch", new GitCommit[]{});
        List<String> results = cmd.runOnBranch(branch);

        for (String result: results) {
            if (result.equals("Linter completed with no linting errors")) {
                return;
            }
        }

        fail();
    }

    /**
     * Requirement #: 5
     */
    @Test
    public void linterAutomaticallyFixesErorrsTest() {
        CommandLineRunner cmd = new CommandLineRunner();
        cmd.setCommand("npm run lint");
        IBranch branch = new GitBranch("lintAutoFixBranch", new GitCommit[]{});
        List<String> results = cmd.runOnBranch(branch);

        if (results.get(0) != "Fixed 2 error(s) in app/misc/repo.ts") {
            fail();
        }

        if (results.get(1) != "All linting errors resolved") {
            fail();
        }
    }

    /**
     * Requirement #: 5
     */
    @Test
    public void LinterFailsAndRequiresFurtherAssistanceTest() {
        CommandLineRunner cmd = new CommandLineRunner();
        cmd.setCommand("npm run lint");
        IBranch branch = new GitBranch("linterRequiresFurtherAssistanceBranch", new GitCommit[]{});
        List<String> results = cmd.runOnBranch(branch);

        for (String result: results) {
            if (result.equals("ERROR: app/misc/router.ts:116:1 - non-arrow functions are forbidden")) {
                return;
            }
        }

        fail();
    }

    /**
     * Requirement #: 6
     */
    @Test
    public void AutomatedCodeInspectionBadCodeDetectedTest() {
        IBranch branch = new GitBranch("allPassMLCheckBranch", new GitCommit[]{});
        MachineLearningModelHandler mlmp = new MachineLearningModelHandler();

        Map<List<Integer>, AnomalyType> anomalyTypeMap = mlmp.identifyAnomalyLines(branch);

        if (anomalyTypeMap.size() != 0) {
            fail();
        }

        return;
    }

    /**
     * Requirement #: 6
     */
    @Test
    public void AutomatedCodeInspectionFailSmallDefectsTest() {
        IBranch branch = new GitBranch("failSmallDefectsMLCheckBranch", new GitCommit[]{});
        MachineLearningModelHandler mlmp = new MachineLearningModelHandler();

        Map<List<Integer>, AnomalyType> anomalyTypeMap = mlmp.identifyAnomalyLines(branch);

        for (List<Integer> lineNumberSet: anomalyTypeMap.keySet()) {
            if (anomalyTypeMap.get(lineNumberSet) == AnomalyType.SmallDefect) {
                return;
            }
        }

        fail();
    }
}

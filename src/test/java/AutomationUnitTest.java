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

import static org.junit.Assert.*;

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
     * Requirement #: 5
     */
    @Test
    public void linterAttemptsToLintAUnexpectedBranchTest() {
        CommandLineRunner cmd = new CommandLineRunner();
        cmd.setCommand("npm run lint");
        IBranch branch = new GitBranch("unexpectedBranch", new GitCommit[]{});
        List<String> results = cmd.runOnBranch(branch);

        if (results.get(0) == "") {
            return;
        }
    }

    /**
     * Requirement #: 6
     */
    @Test
    public void AutomatedCodeInspectionNoDefectsDetectedTest() {
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
    public void AutomatedCodeInspectionDetectedSmallDefectsTest() {
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

    /**
     * Requirement #: 6
     */
    @Test
    public void AutomatedCodeInspectionDetectedMaliciousCodeBlocksTest() {
        IBranch branch = new GitBranch("failMaliciousCodeBlocksMLCheckBranch", new GitCommit[]{});
        MachineLearningModelHandler mlmp = new MachineLearningModelHandler();

        Map<List<Integer>, AnomalyType> anomalyTypeMap = mlmp.identifyAnomalyLines(branch);

        for (List<Integer> lineNumberSet: anomalyTypeMap.keySet()) {
            if (anomalyTypeMap.get(lineNumberSet) == AnomalyType.MaliciousCodeBlock) {
                return;
            }
        }

        fail();
    }

    /**
     * Requirement #: 6
     */
    @Test
    public void AutomatedCodeInspectionDetectedBadCodeSmellsTest() {
        IBranch branch = new GitBranch("failBadCodeSmellsMLCheckBranch", new GitCommit[]{});
        MachineLearningModelHandler mlmp = new MachineLearningModelHandler();

        Map<List<Integer>, AnomalyType> anomalyTypeMap = mlmp.identifyAnomalyLines(branch);

        for (List<Integer> lineNumberSet: anomalyTypeMap.keySet()) {
            if (anomalyTypeMap.get(lineNumberSet) == AnomalyType.BadCodeSmell) {
                return;
            }
        }

        fail();
    }

    /**
     * Requirement #: 6
     */
    @Test
    public void AutomatedCodeInspectionIsAppliedToUnexpectedBranchTest() {
        IBranch branch = new GitBranch("unexpectedBranch", new GitCommit[]{});
        MachineLearningModelHandler mlmp = new MachineLearningModelHandler();

        Map<List<Integer>, AnomalyType> anomalyTypeMap = mlmp.identifyAnomalyLines(branch);

        if (anomalyTypeMap != null) {
            fail();
        }
    }

    /**
     * Requirement #: 7
     */
    @Test
    public void SuccessfullyGeneratedCodeAbstractionsTest() {
        IAbstractionExtension ae = new MockAbstractionExtension();
        IBranch branch = new GitBranch("passCodeAbstractionGenerationBranch", new GitCommit[]{});
        ae.setBranch(branch);
        String generatedFileName = ae.generateCodeAbstraction();

        String expectedFileName = "MockAutoGeneratedCodeAbstractionPass.txt";
        String expectedCodeAbstractionAsAString = "";
        String actualCodeAbstractionAsAString = "";
        Stream<String> lines;

        try {
            Path path = Paths.get(getClass().getClassLoader().getResource(expectedFileName).toURI());
            lines = Files.lines(path);
            expectedCodeAbstractionAsAString = lines.collect(Collectors.joining("\n"));
            lines.close();

            path = Paths.get(getClass().getClassLoader().getResource(generatedFileName).toURI());
            lines = Files.lines(path);
            actualCodeAbstractionAsAString = lines.collect(Collectors.joining("\n"));
            lines.close();
        }
        catch(FileNotFoundException ex) {
            fail();
        }
        catch(IOException ex) {
            fail();
        }
        catch (URISyntaxException urise) {
            fail();
        }

        assertEquals(expectedCodeAbstractionAsAString, actualCodeAbstractionAsAString);
    }

    /**
     * Requirement #: 7
     */
    @Test
    public void GenerateCodeAbstractionUnexpectedLineInGeneratedFileTest() {
        IAbstractionExtension ae = new MockAbstractionExtension();
        IBranch branch = new GitBranch("failCodeAbstractionGenerationBranch", new GitCommit[]{});
        ae.setBranch(branch);
        String generatedFileName = ae.generateCodeAbstraction();

        String expectedFileName = "MockAutoGeneratedCodeAbstractionPass.txt";
        String expectedCodeAbstractionAsAString = "";
        String actualCodeAbstractionAsAString = "";
        Stream<String> lines;

        try {
            Path path = Paths.get(getClass().getClassLoader().getResource(expectedFileName).toURI());
            lines = Files.lines(path);
            expectedCodeAbstractionAsAString = lines.collect(Collectors.joining("\n"));
            lines.close();

            path = Paths.get(getClass().getClassLoader().getResource(generatedFileName).toURI());
            lines = Files.lines(path);
            actualCodeAbstractionAsAString = lines.collect(Collectors.joining("\n"));
            lines.close();
        }
        catch(FileNotFoundException ex) {
            fail();
        }
        catch(IOException ex) {
            fail();
        }
        catch (URISyntaxException urise) {
            fail();
        }

        assertNotEquals(expectedCodeAbstractionAsAString, actualCodeAbstractionAsAString);
    }

    /**
     * Requirement #: 7
     */
    @Test
    public void UnexpectedBranchUsedNoFilesGeneratedTest() {
        IAbstractionExtension ae = new MockAbstractionExtension();
        IBranch branch = new GitBranch("unexpectedBranch", new GitCommit[]{});
        ae.setBranch(branch);
        String generatedFileName = ae.generateCodeAbstraction();

        assertNull(generatedFileName);
    }
}

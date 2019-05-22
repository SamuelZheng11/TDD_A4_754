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
}

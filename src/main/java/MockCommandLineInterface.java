public class MockCommandLineInterface implements ICommandLineInterface {
    private boolean isRunning = false;
    private String branch = "";

    public void exec(String commands) {
        this.isRunning = true;
        if (commands.contains("noLintNeededBranch")) {
            branch = "noLintNeededBranch";
        }
    }

    public IBufferedReader getInputStream() {
        if (isRunning == true) {
            if (branch == "noLintNeededBranch") {
                return new MockBufferedReader(0);
            } else {
                return null;
            }
        } else {
            return null;
        }
    }
}

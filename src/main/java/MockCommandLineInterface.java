public class MockCommandLineInterface implements ICommandLineInterface {
    private boolean isRunning = false;
    private String branch = "";

    public void exec(String commands) {
        if (commands.contains("noLintNeededBranch")) {
            branch = "noLintNeededBranch";
            this.isRunning = true;
        }  else if (commands.contains("linterRequiresFurtherAssistanceBranch")) {
            branch = "linterRequiresFurtherAssistanceBranch";
            this.isRunning = true;
        }
    }

    public IBufferedReader getInputStream() {
        if (isRunning == true) {
            if (branch == "noLintNeededBranch") {
                return new MockBufferedReader(0);
            } else if (branch == "linterRequiresFurtherAssistanceBranch") {
                return new MockBufferedReader(1);
            } else {
                return null;
            }
        } else {
            return null;
        }
    }
}

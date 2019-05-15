public class GitBranch  implements IBranch {

    public String getName() {
        return _name;
    }

    public GitCommit[] getCommits() {
        return _commits;
    }

    private String _name;
    private GitCommit[] _commits;
    public GitBranch(String name, GitCommit[] commits){
        _name= name;
        _commits = commits;
    }


}

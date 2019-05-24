
public interface IAbstractionExtension {
    void setBranch(IBranch branch);

    String generateCodeAbstraction();
    String generateReviewerAbstraction();
    String generateDevCodeAbstraction();
}

import java.util.ArrayList;
import java.util.List;

public class MockDatabasePersistence {
    private List<User> allUsers = new ArrayList<User>();
    private static MockDatabasePersistence instance;
    private MockDatabasePersistence(){}

    public static MockDatabasePersistence getInstance(){
        if (instance==null){
            instance = new MockDatabasePersistence();
        }
        return instance;
    }
    public List<User> getAllCodeReviewers() {
        if (allUsers.size()==0) {
            allUsers = new ArrayList<User>();
            for (int i = 0; i < 10; i++) {
                allUsers.add(new User(String.format("%d", i), UserType.NonDeveloper));
            }
        }
        return allUsers;
    }
}

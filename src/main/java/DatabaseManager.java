import java.util.ArrayList;
import java.util.List;

public class DatabaseManager {
    private List<User> allUsers = new ArrayList<User>();
    private static DatabaseManager instance;
    private DatabaseManager(){}

    public static DatabaseManager getInstance(){
        if (instance==null){
            instance = new DatabaseManager();
        }
        return instance;
    }
    public List<User> getAllUsers() {
        if (allUsers.size()==0) {
            allUsers = new ArrayList<User>();
            for (int i = 0; i < 10; i++) {
                UserType ut = UserType.Developer;
                if (i % 2 == 0) {
                    ut = UserType.NonDeveloper;
                }
                allUsers.add(new User(String.format("%d", i), ut));
            }
        }
        return allUsers;
    }
}

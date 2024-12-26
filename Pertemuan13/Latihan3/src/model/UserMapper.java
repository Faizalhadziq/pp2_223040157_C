package Pertemuan13.Latihan3.src.model;
import java.util.ArrayList;
import java.util.List;

public class UserMapper {
    public List<User> getUsers(int count) {
        List<User> users = new ArrayList<>();
        for (int i = 1; i <= count; i++) {
            users.add(new User("User" + i, "user" + i + "@example.com"));
        }
        return users;
    }
}

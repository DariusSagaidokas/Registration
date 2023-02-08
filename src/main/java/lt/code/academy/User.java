package lt.code.academy;

import java.io.Serializable;

public record User(String name, String surname, String id) implements Serializable {
    @Override
    public String toString() {
        return "User{" +
                "name='" + name + '\'' +
                ", surname='" + surname + '\'' +
                ", id='" + id + '\'' +
                '}';
    }
}

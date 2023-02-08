package lt.code.academy;

import java.io.*;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

public class Registration {
    private static final String FILE_NAME = "users.txt";

    public static void main(String[] args) {
        Map<String, User> users = new HashMap<>();
        Registration registration = new Registration();
        Scanner scanner = new Scanner(System.in);

        String action;
        do {
            registration.menu();
            action = scanner.nextLine();
            registration.userSelection(scanner, action, users);
        } while (!action.equals("3"));
    }


    private void createUser(Scanner scanner, Map<String, User> users) {
        System.out.println("Enter your name: ");
        String name = scanner.nextLine();
        System.out.println("Enter your surname: ");
        String surname = scanner.nextLine();

        User user;
        String id;
        do {
            System.out.println("Enter your ID: ");
            id = scanner.nextLine();
            user = users.get(id);
            if (user != null) {
                System.out.println("User with such ID already exists");
            }
        }
        while (user != null);

        user = new User(name, surname, id);
        users.put(id, user);
    }

    private Map<String, User> getUsers(Map<String, User> users) {
        File file = new File(FILE_NAME);
        if (!file.exists()){
            return users;
        }
        try (ObjectInput oi = new ObjectInputStream(new FileInputStream(FILE_NAME))) {
            while (true) {
                User user = (User) oi.readObject();
                users.put(user.id(), user);
            }
        } catch (EOFException e) {

        } catch (Exception e) {
            System.out.println(e.getMessage());
        }

        return users;
    }

    private void saveUsers(Map<String, User> users) {
        try (ObjectOutput ob = new ObjectOutputStream(new FileOutputStream(FILE_NAME))) {
            for (User user : users.values()) {
                ob.writeObject(user);
            }
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
    }

    private void menu() {
        System.out.println("""
                1. Register
                2. Check users log
                3. Exit
                """);
    }

    private void userSelection(Scanner scanner, String action, Map<String, User> users) {
        switch (action) {
            case "1" -> createUser(scanner, getUsers(users));
            case "2" -> System.out.println(getUsers(users));
            case "3" -> saveUsers(users);
            default -> System.out.println("Such action does not exist");
        }
    }

}

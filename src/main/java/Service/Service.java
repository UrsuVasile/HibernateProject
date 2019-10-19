package Service;

import Database.UserDao;
import Entity.User;

import java.util.Scanner;

public class Service {

    private Scanner scanner;
    private boolean isRunning;
    private UserDao userDao;

    public Service() {
        scanner = new Scanner(System.in);
        isRunning = true;
        userDao = new UserDao();

    }

    public void action() {
        while (isRunning) {
            System.out.println("1.Login");
            System.out.println("1.Sign in");
            System.out.println("Enter command: ");
            Integer command = scanner.nextInt();
            executeCommand(command);
        }

    }

    public void executeCommand(Integer command) {

        switch (command) {
            case 1:
                System.out.println("Enter username:");
                String username = scanner.next();
                System.out.println("Enter pasword:");
                int password = scanner.nextInt();

                if (userDao.findUserAndPasswordFromDatabase(username, password)) {
                    System.out.println("Succes");
                } else {
                    System.out.println("Try again!");
                }
                break;
            case 2:
                System.out.println("Insert the Username: ");
                String user = scanner.nextLine();
                System.out.println("Insert the pasword: ");
                String pass = scanner.next();
                User user1 = new User();
                user1.setUsername(user);
                user1.setPassword(pass);

                userDao.insertUser(user1);
                break;
            default:
                isRunning = false;
                System.out.println("Goodbye!");
        }
    }
}


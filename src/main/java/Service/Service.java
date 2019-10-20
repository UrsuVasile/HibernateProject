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
            System.out.println("2.Sign up");
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
                String password = scanner.next();


                if (userDao.findUserAndPasswordFromDatabase(username, password)==false) {
                    System.out.println("Try again!");
                } else {
                    System.out.println("Succes!");
                }
                break;
            case 2:
                System.out.println("Insert the Username: ");
                String user = scanner.next();
                System.out.println("Insert the pasword: ");
                String pass1 = scanner.next();
                System.out.println("Insert the pasword again: ");
                String pass2 = scanner.next();
                if (pass1.equals(pass2)) {
                    User user1 = new User();
                    user1.setUsername(user);
                    user1.setPassword(pass1);
                    userDao.insertUser(user1);
                    System.out.println("You have successfully created your account. Now you can login!");
                }else{
                    System.out.println("You did not enter the same password");
                    System.out.println("Please try again");
                }
                break;
            default:
                isRunning = false;
                System.out.println("Goodbye!");
        }
    }
}


package Service;

import Database.ProductDAO;
import Database.UserDao;
import Entity.Product;
import Entity.User;

import java.util.Scanner;

public class Service {

    Scanner scanner;
    User user;
    Product product;
    UserDao userDao;
    ProductDAO productDAO;
    boolean isRunning;

    public Service() {
        scanner = new Scanner(System.in);
        user = new User();
        userDao = new UserDao();
        productDAO = new ProductDAO();
        product = new Product();
        isRunning = true;
    }

    public void action() {

        System.out.println("1.log in");
        System.out.println("2.sign up");
        int firstCommand = scanner.nextInt();
        executeCommand(firstCommand);
    }

    public void executeCommand(int command) {
        if (command == 1) {
            System.out.println("You about to log in.");
            System.out.print("Please enter your username:");
            String username = scanner.next();
            System.out.print("Please enter your password");
            String password = scanner.next();


                if (userDao.validateCredentials(username, password)) {
                    System.out.println("Log In successfully");
                    while (isRunning) {
                        System.out.println("Choose a command:");
                        System.out.println("1.FindProductById");
                        System.out.println("2.Insert a Product");
                        System.out.println("3.Update a Product");
                        int productCommand = scanner.nextInt();
                        executeProductCommand(productCommand);
                    }
        } else if (command == 2) {

            System.out.println("You are creating now an account: ");
            System.out.println();
            System.out.print("Please enter the username:");
            String username = scanner.next();
            user.setUsername(username);
            System.out.print("Please enter the password:");
            String password = scanner.next();
            user.setPassword(password);
            userDao.insertUser(user);
            System.out.println("You've registered successfully.");
            executeCommand(1);
        } else {
            System.out.println("Insert a valid command");
            action();
        }
    }

    public void executeProductCommand(int productCommand) {
        switch (productCommand) {
            case 1:
                System.out.println("This command finds a product by id.");
                System.out.print("Insert the id of the product you wish to find:");
                int productId = scanner.nextInt();
                System.out.println(productDAO.findProductById(productId));
                break;
            case 2:
                System.out.println("This command inserts a product.");
                System.out.println("Enter the name of the product");
                String productName = scanner.next();
                product.setName(productName);
                productDAO.insertProduct(product);
                break;
            default:
                System.out.println("bye");
                isRunning = false;
        }
    }

}

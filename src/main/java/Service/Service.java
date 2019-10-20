package Service;

import Database.ProductDAO;
import Database.UserDao;
import Entity.Description;
import Entity.Product;
import Entity.User;

import java.util.List;
import java.util.Optional;
import java.util.Scanner;

public class Service {

    private Scanner scanner;
    private User user;
    private Product product;
    private UserDao userDao;
    private ProductDAO productDAO;
    private boolean isRunning;
    private Description description;

    public Service() {
        scanner = new Scanner(System.in);
        user = new User();
        userDao = new UserDao();
        productDAO = new ProductDAO();
        product = new Product();
        isRunning = true;
        description = new Description();
    }

    public void action() {

        System.out.println("1.log in");
        System.out.println("2.sign up");
        int firstCommand = scanner.nextInt();
        executeCommand(firstCommand);
    }

    public void executeCommand(int command) {
        if (command == 1) {
            logIn();
        } else if (command == 2) {
            signUp();
        } else if (command == 3) {
            doCommands();
        } else {
            System.out.println("Insert a valid command");
            action();
        }
    }

    private void doCommands() {
        while (isRunning) {
            System.out.println("Choose a command:");
            System.out.println("1.FindProductById");
            System.out.println("2.Insert a Product");
            System.out.println("3.Update a Product");
            System.out.println("4.Delete a Product");
            System.out.println("5.Showw all Product");
            int productCommand = scanner.nextInt();
            executeProductCommand(productCommand);
        }
    }

    private void signUp() {
        System.out.println("You are creating now an account: ");
        System.out.println();
        System.out.print("Please enter the username:");
        String username = scanner.next();
        user.setUsername(username);
        System.out.print("Please enter the password:");
        String password = scanner.next();
        System.out.print("Please re-enter the password:");
        String password2 = scanner.next();
        if (password.equals(password2)) {
            user.setPassword(password);
            userDao.insertUser(user);
            System.out.println("You've registered successfully.");
            executeCommand(1);
        } else {
            System.out.println("Passwords do not match");
            System.out.println();
            executeCommand(2);
        }
    }

    private void logIn() {
        System.out.println("You about to log in.");
        System.out.print("Please enter your username:");
        String username = scanner.next();
        System.out.print("Please enter your password");
        String password = scanner.next();

        if (userDao.findUserAndPasswordFromDatabase(username, password)) {
            System.out.println("Log In successfully");
            doCommands();
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

                System.out.println("Please enter a description");
                scanner.nextLine();
                String descriptionName = scanner.nextLine();
                description.setDescription(descriptionName);

                System.out.print("Enter color: ");
                String descriptionColor = scanner.next();
                description.setColor(descriptionColor);

                System.out.print("Enter type: ");
                String descriptionType = scanner.next();
                description.setType(descriptionType);

                product.setDescription(description);
                description.setProduct(product);
                productDAO.insertProduct(product);
                break;
            case 3:
                System.out.println("This command updates a product.");

                System.out.print("Insert the id of the product you wish to update: ");
                int idProduct = scanner.nextInt();
                product.setId(idProduct);

                System.out.print("Insert the new name for the product:");
                String nameProdUpdate = scanner.next();
                product.setName(nameProdUpdate);

                System.out.print("Insert the new description for the product:");
                scanner.nextLine();
                String descriptionProductUpdate = scanner.nextLine();
                description.setDescription(descriptionProductUpdate);

                System.out.print("Insert the new color for the product:");
                String colorProductUpdate = scanner.next();
                description.setColor(colorProductUpdate);

                System.out.print("Insert the new type for the product:");
                String typeProductUpdate = scanner.next();
                description.setType(typeProductUpdate);

                Product newProduct = productDAO.findProductById(idProduct);
                int newId = newProduct.getDescription().getId();
//                int newId = Optional.ofNullable(newProduct)
//                            .map(Product::getDescription)
//                            .map(Description::getId)
//                            .orElse(0);
                description.setId(newId);

                product.setDescription(description);
                description.setProduct(product);

                productDAO.updateProduct(product);
                break;
            case 4:
                System.out.println("This command will delete a product.");
                System.out.print("Insert the id of the product you wish to delete: ");
                int idProductToDelete = scanner.nextInt();
                productDAO.deleteProductById(idProductToDelete);
            case 5:
                System.out.println("This command will show all products.");
                List<Product> products = productDAO.showAllProducts();
                for(Product p : products){
                    System.out.println(p);
                }
                break;
            default:
                System.out.println("bye");
                isRunning = false;
        }
    }

}

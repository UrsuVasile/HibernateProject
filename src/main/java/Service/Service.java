package Service;

import Database.ProductDAO;
import Database.UserDao;
import Entity.Description;
import Entity.Product;
import Entity.User;

import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.List;
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
            product = new Product();
            description = new Description();
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
            String saltedPassword = "bubulici" +  password;
            String hashedPassword = generateHash(saltedPassword);

            user.setPassword(hashedPassword);
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
                findProductByIdCommand();
                break;
            case 2:
                insertProductCommand();
                break;
            case 3:
                updateProductCommand();
                break;
            case 4:
                deleteProductCommand();
                break;
            case 5:
                showAllProductsCommand();
                break;
            default:
                System.out.println("Goodbye!");
                isRunning = false;
        }
    }

    private void showAllProductsCommand() {
        System.out.println("This command will show all products.");
        List<Product> products = productDAO.showAllProducts();
        for (Product p : products) {
            System.out.println(p);
        }
    }

    private void deleteProductCommand() {
        System.out.println("This command will delete a product.");
        System.out.print("Insert the id of the product you wish to delete: ");
        int idProductToDelete = scanner.nextInt();

        if (productDAO.findProductById(idProductToDelete) != null) {
            if (productDAO.findProductById(idProductToDelete).getId() != 0) {
                productDAO.deleteProductById(idProductToDelete);
                System.out.println("Product deleted.");
            }
        } else {
            System.out.println("The id entered is not valid.");
            executeProductCommand(4);
        }
    }

    private void updateProductCommand() {
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
    }

    private void insertProductCommand() {
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
    }

    private void findProductByIdCommand() {
        System.out.println("This command finds a product by id.");
        System.out.print("Insert the id of the product you wish to find:");
        int productId = scanner.nextInt();
        System.out.println(productDAO.findProductById(productId));
    }

    public String md5(String input) {

        String md5 = null;

        if (null == input) return null;

        try {

            //Create MessageDigest object for MD5
            MessageDigest digest = MessageDigest.getInstance("MD5");

            //Update input string in message digest
            digest.update(input.getBytes(), 0, input.length());

            //Converts message digest value in base 16 (hex)
            md5 = new BigInteger(1, digest.digest()).toString(16);

        } catch (NoSuchAlgorithmException e) {
            System.out.println("I'm sorry, but MD5 is not a valid message digest algorithm");
        }
        return md5;
    }

    public static String generateHash(String input) {
        StringBuilder hash = new StringBuilder();

        try {
            MessageDigest sha = MessageDigest.getInstance("SHA-1");
            byte[] hashedBytes = sha.digest(input.getBytes());
            char[] digits = {'0', '1', '2', '3', '4', '5', '6', '7', '8', '9',
                    'a', 'b', 'c', 'd', 'e', 'f'};
            for (int idx = 0; idx < hashedBytes.length; idx++) {
                byte b = hashedBytes[idx];
                hash.append(digits[(b & 0xf0) >> 4]);
                hash.append(digits[b & 0x0f]);
            }
        } catch (NoSuchAlgorithmException e) {
            System.out.println("I'm sorry, but SHA-1 is not a valid message digest algorithm");
        }
        return hash.toString();
    }

}



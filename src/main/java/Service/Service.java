package Service;

import Database.ProductDAO;
import Database.StockDao;
import Database.UserDao;
import Entity.*;

import javax.jws.soap.SOAPBinding;
import javax.persistence.NoResultException;
import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.LinkedList;
import java.util.List;
import java.util.Scanner;

public class Service {

    private Scanner scanner;
    private User user;
    private Product product;
    private UserDao userDao;
    private ProductDAO productDAO;
    private boolean isRunning;
    private boolean executeStock;
    private Description description;
    private Stock stock;
    private StockDao stockDao;
    private Result result;

    public Service() {
        scanner = new Scanner(System.in);
        user = new User();
        userDao = new UserDao();
        productDAO = new ProductDAO();
        isRunning = true;
        executeStock = true;
        result = new Result();
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
            stock = new Stock();

            System.out.println("Choose a command:");
            System.out.println("1.FindProductById");
            System.out.println("2.Insert a Product");
            System.out.println("3.Update a Product");
            System.out.println("4.Delete a Product");
            System.out.println("5.Show all Products");
            System.out.println("6.Back to Main Menu");
            System.out.println("7.Exit");
            int productCommand = scanner.nextInt();
            executeProductCommand(productCommand);
        }
    }

    private void doCommandsForGuests() {
        while (isRunning) {
            product = new Product();
            description = new Description();
            stock = new Stock();

            System.out.println("Choose a command:");
            System.out.println("1.FindProductById");
            System.out.println("2.Insert a Product");
            System.out.println("3.Update a Product");
            System.out.println("4.Delete a Product");
            System.out.println("5.Show all Products");
            System.out.println("6.Exit");
            int productCommand = scanner.nextInt();
            executeProductCommandForGuest(productCommand);
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
            String saltedPassword = "bubulici" + password;
            String hashedPassword = generateHash(saltedPassword);
            List<User> users = userDao.showAllUsers();

                if (users.size()==0){
                    user.setPassword(hashedPassword);
                    user.setRol("admin");
                    userDao.insertUser(user);
                    System.out.println("You've registered successfully.");
                    executeCommand(1);
                }else if (users.size()>0) {
                    user.setPassword(hashedPassword);
                    user.setRol("guest");
                    userDao.insertUser(user);
                    System.out.println("You've registered successfully.");
                    executeCommand(1);
                }
        } else {
            System.out.println("Passwords do not match");
            System.out.println();
            executeCommand(2);
        }
    }

    private void logIn() {
        System.out.println("You about to log in.");
        System.out.print("Please enter your username:");
        String userName = scanner.next();
        System.out.print("Please enter your password");
        String password = scanner.next();
        try {
            if (userDao.findUserRol(userName, generateHash("bubulici" + password)).equals("admin")) {
                System.out.println();
                System.out.println("Hi Boss,");
                System.out.println("Welcome in your virtual store manager!");
                System.out.println();
                selectCommands();
            } else if (userDao.findUserAndPasswordFromDatabase(userName, password)) {
                System.out.println("Log In successfully");
                doCommandsForGuests();
            }
        } catch (NoResultException e) {
            System.out.println("Wrong password or username!");
            System.out.println("Please, try again:");
            action();
        }
    }

    public void selectCommands() {
        System.out.println("Choose a option");
        System.out.println("1.Product's Menu");
        System.out.println("2.User's Menu");
        System.out.println("3.Stock's Menu");
        System.out.println("4.Exit");
        int command = scanner.nextInt();
        if (command == 1) {
            doCommands();
        } else if (command == 2) {
            doAdminCommands();
        } else if (command == 3) {
            executeStockCommand();
        } else if (command == 4) {
            System.out.println("Goodbye");
            isRunning = false;
        } else {
            System.out.println("Choose a number between 1 and 4");
            selectCommands();
        }

    }

    public void doAdminCommands() {
        System.out.println("1.Show all users");
        System.out.println("2.Find the user by ID");
        System.out.println("3.Update the rol of the member, found by ID");
        System.out.println("4.Back to Main Menu");
        System.out.println("5.Exit");
        System.out.println();
        int command = scanner.nextInt();
        if (command > 0 && command < 6) {
            executeAdminCommand(command);
        } else {
            System.out.println("Please, enter a number between 1 and 5");
            System.out.println();
            doAdminCommands();
        }
    }

    public void executeAdminCommand(int command) {
        switch (command) {
            case 1:
                System.out.println("This command will show all users.");
                List<User> users = userDao.showAllUsers();
                for (User u : users) {
                    System.out.println(u);
                }
                doAdminCommands();
                break;
            case 2:
                System.out.println("Enter user's ID:");
                int id = scanner.nextInt();
                System.out.println(userDao.findUserById(id));
                doAdminCommands();
                break;
            case 3:
                User newUser = new User();
                System.out.println("This command will update user's rol in store");
                System.out.println("Insert the id of the user you wish to update:");
                int idUser = scanner.nextInt();
                newUser.setId(idUser);

                System.out.print("Insert the new rol for the user:");
                String newUserRol = scanner.next();
                newUser.setRol(newUserRol);

                newUser.setPassword(userDao.findUserById(idUser).getPassword());
                newUser.setUsername(userDao.findUserById(idUser).getUsername());

                userDao.updateUserRol(newUser);
                doAdminCommands();

                break;
            case 4:
                selectCommands();
            case 5:
                isRunning = false;
                System.out.println("Goodbye!");
                break;
            default:
                System.out.println("Please enter a number between 1 and 5");
                doAdminCommands();
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
            case 6:
                selectCommands();
            case 7:
                System.out.println("Goodbye!");
                isRunning = false;
                break;
            default:
                isRunning = false;
        }
    }

    public void executeProductCommandForGuest(int productCommand) {
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
            case 6:
                System.out.println("Goodbye!");
                isRunning = false;
                break;
            default:
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

        System.out.print("Insert the new stock for the product:");
        int stockProductUpdate = scanner.nextInt();
        stock.setStockQuantity(stockProductUpdate);

        Product newProduct = productDAO.findProductById(idProduct);
        int newId = newProduct.getDescription().getId();
        int stockId = newProduct.getStock().getId();
//                int newId = Optional.ofNullable(newProduct)
//                            .map(Product::getDescription)
//                            .map(Description::getId)
//                            .orElse(0);
        description.setId(newId);
        stock.setId(stockId);

        product.setStock(stock);
        stock.setProduct(product);

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

        System.out.print("Enter stock: ");
        int stockQuantity = scanner.nextInt();
        stock.setStockQuantity(stockQuantity);

        stock.setProduct(product);
        product.setStock(stock);

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

    public String generateHash(String input) {
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

    public void executeStockCommand() {
        System.out.println("Here you can perform different operations related to stock");
        System.out.println("Choose a command: ");
        System.out.println();
        while (executeStock) {
            System.out.println("1.Display the total stock in the store");
            System.out.println("2.Display the stock of each product");
            System.out.println("3.Show products with stock = 0");
            System.out.println("4.Displays products with sufficient stock(stock>10)");
            System.out.println("5.Back to Main Menu");
            System.out.println("6.Exit");
            int command = scanner.nextInt();

            if (command <= 6) {
                doExecuteStock(command);
            } else {
                System.out.println("Enter a number between 1 and 6");
                System.out.println();
                executeStockCommand();
            }
        }
    }

    public void doExecuteStock(int command) {
        stockDao = new StockDao();
        switch (command) {
            case 1:
                System.out.println("Total stock of the store is: " + stockDao.totalStock());
                System.out.println();
                break;
            case 2:
                List<Result> list = stockDao.stockForEachProduct();
                for (Result p : list) {
                    System.out.println(p);
                }
                System.out.println();
                break;
            case 3:
                List<Result> results = stockDao.showProductsWithStockZero();
                for (Result p : results) {
                    System.out.println(p);
                }
                System.out.println();
                break;
            case 4:
                List<Result> resultsSufficient = stockDao.displayProductsWithSufficientStock();
                for (Result p : resultsSufficient) {
                    System.out.println(p);
                }
                System.out.println();
                break;
            case 5:
                selectCommands();
                break;
            case 6:
                System.out.println("Goodbye!");
                executeStock = false;
            default:
                executeStock = false;
        }
    }
}



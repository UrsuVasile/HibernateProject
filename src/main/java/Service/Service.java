package Service;

import java.util.Scanner;

public class Service {

    private Scanner scanner;
    private boolean isRunning;

    public Service(){
        scanner = new Scanner(System.in);
        isRunning = true;
    }

    public void action(){
        firstMessage();
        int initialValue = scanner.nextInt();
        executeCommand(initialValue);


    }

    public void firstMessage(){
        System.out.println("1.Log in.");
        System.out.println("2.Sign up");
    }

    public void validateCredetials(){

    }

    public void executeCommand(int command){
        switch (command){
            case 1:

        }
    }
}

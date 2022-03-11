package banking;

import java.util.Scanner;

public class Execution {

    public static void run(DataBase dataBase){
        Scanner scanner = new Scanner(System.in);
        Menu.mainMenu();
        while (true) {
            switch (scanner.nextInt()) {
                case 1:
                    Card card = new Card(dataBase);
                    Menu.mainMenu();
                    break;
                case 2:
                    Login.logInAccount(dataBase);
                    break;
                case 0:
                    Menu.exit();
                    dataBase.closeCon();
                    System.exit(0);
                    break;
            }
        }
    }
}

package banking;

import java.util.Scanner;

public class Login {
    public static void logInAccount(DataBase dataBase) {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Enter your card number:");
        String cardNumber = scanner.nextLine();
        System.out.println("Enter your PIN:");
        String PIN = scanner.nextLine();

        boolean contain = dataBase.checkCardAndPin(cardNumber, PIN);
        if(contain) {
            Menu.successfullyLogin();
            accountTransactions(cardNumber, dataBase);
        } else {
            Menu.unsuccessfulLogin();
            Menu.mainMenu();
        }
    }

    private static void accountTransactions(String card, DataBase dataBase) {

        boolean isTrue = true;

        while(isTrue) {
            Menu.loginMenu();
            Scanner scanner = new Scanner(System.in);
            switch (scanner.nextInt()) {
                case 1: getBalance(card, dataBase);
                        break;
                case 2: topUpBalance(card, dataBase);
                        break;
                case 3: transferMoney(card, dataBase);
                        break;
                case 4: deleteAccount(card, dataBase);
                        isTrue = false;
                        break;
                case 5: isTrue = false;
                        Menu.successfullyLoggedOut();
                        break;
                case 0: Menu.exit();
                        dataBase.closeCon();
                        System.exit(0);
                        break;
            }
        }
        Menu.mainMenu();
    }

    private static void getBalance(String card, DataBase dataBase) {
        System.out.println("Balance: " + dataBase.checkBalance(card));
    }
    private static void topUpBalance(String card, DataBase dataBase) {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Enter income:");
        dataBase.topUpBalance(card, scanner.nextInt());
        System.out.println("Income was added!\n");
    }

    private static void deleteAccount(String card, DataBase dataBase) {
        dataBase.deleteAccount(card);
        System.out.println("The account has been closed!\n");
    }

    private static boolean transferMoney(String senderCard, DataBase dataBase) {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Transfer\nEnter card number:");
        String recipientCard = scanner.nextLine();
        if(!checkLuhn(recipientCard)) {
            Menu.lunh();
            return false;
        }
        if(!dataBase.checkCard(recipientCard)) {
            Menu.checkCard();
            return false;
        }
        Menu.enterMoney();
        int value = scanner.nextInt();
        if(dataBase.checkBalance(senderCard) < value) {
            Menu.notMoney();
            return false;
        }

        dataBase.transferMoney(senderCard, recipientCard, value);
        System.out.println("Success!");
        return true;
    }

    private static boolean checkLuhn(String card) {
        int sum = 0;
        int digitNumber = 0;
        String[] number = card.split("");
        for(int i = 0; i < number.length - 1; i++) {
            if((i + 1) % 2 != 0) {
                number[i] = String.valueOf(Integer.parseInt(number[i]) * 2);
                if(Integer.parseInt(number[i]) > 9) {
                    number[i] = String.valueOf(Integer.parseInt(number[i]) - 9);
                }
            }
            sum += Integer.parseInt(number[i]);
        }
        while((sum + digitNumber) % 10 != 0) {
            digitNumber++;
        }
        return number[number.length - 1].equals(String.valueOf(digitNumber));
    }
}

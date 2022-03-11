package banking;

public class Menu {
    public static void mainMenu() {
        System.out.print(
                "1. Create an account\n" +
                "2. Log into account\n" +
                "0. Exit\n");
    }

    public static void loginMenu() {
        System.out.print(
                "1. Balance\n" +
                "2. Add income\n" +
                "3. Do transfer\n" +
                "4. Close account\n" +
                "5. Log out\n" +
                "0. Exit\n");
    }

    public static void createdCardSuccess(String card, String PIN){
        System.out.println(
                "Your card has been created\n" +
                "Your card number:\n" +
                card +
                "\nYour card PIN:\n" +
                PIN + "\n");
    }

    public static void unsuccessfulLogin() {
        System.out.println("\nWrong card number or PIN!\n");
    }

    public static void successfullyLogin() {
        System.out.println("\nYou have successfully logged in!\n");
    }

    public static void successfullyLoggedOut() {
        System.out.println("\nYou have successfully logged out!\n");
    }

    public static void lunh() {
        System.out.println("\nProbably you made a mistake in the card number. Please try again!\n");
    }
    public static void checkCard() {
        System.out.println("\nSuch a card does not exist.\n");
    }

    public static void enterMoney() {
        System.out.println("Enter how much money you want to transfer:");
    }

    public static void notMoney() {
        System.out.println("\nNot enough money!\n");
    }

    public static void exit() {
        System.out.println("Bye!");
    }
}

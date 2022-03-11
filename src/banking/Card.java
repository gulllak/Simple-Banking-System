package banking;

public class Card {
    DataBase dataBase;

    public Card(DataBase dataBase) {
        this.dataBase = dataBase;
        generateCard();
    }

    private void generateCard() {
        StringBuilder card = new StringBuilder("400000");

        do {
            card.append((int) (Math.random() * 10));
        } while (card.length() != 15);

        card = createCardLuhn(card.toString());

        if (dataBase.checkCard(card.toString())) {
            generateCard();
        }
        String pin = generatePin();
        dataBase.addCard(card.toString(), pin);
        Menu.createdCardSuccess(card.toString(), pin);
    }

    private String generatePin() {
        StringBuilder PIN = new StringBuilder();
        for(int i = 0; i < 4; i++) {
            PIN.append((int) (Math.random() * 10));
        }
        return PIN.toString();
    }

    private StringBuilder createCardLuhn(String card) {
        int sum = 0;
        int digitNumber = 0;
        StringBuilder numberCard = new StringBuilder();
        String[] number = card.split("");
        for(int i = 0; i < number.length; i++) {
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
        return numberCard.append(card).append(digitNumber);
    }
}

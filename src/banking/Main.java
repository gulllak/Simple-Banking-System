package banking;

import org.sqlite.SQLiteDataSource;

public class Main {
    public static void main(String[] args) {
        SQLiteDataSource dataSource = new SQLiteDataSource();
        dataSource.setUrl("jdbc:sqlite:Simple Banking System/task/" + args[1]);
        DataBase dataBase = new DataBase(dataSource);
        Execution.run(dataBase);
    }
}
package banking;

import javax.sql.DataSource;
import java.sql.*;

public class DataBase {
    private Connection con;
    private Statement statement;
    private final String INSERT_INFO = "INSERT INTO card (number, pin) VALUES (?, ?)";
    private final String CHECK_CARD = "SELECT number FROM card";
    private final String CHECK_CARD_PIN = "SELECT number, pin FROM card";
    private final String CHECK_BALANCE = "SELECT balance FROM card WHERE number = ?";
    private final String TOP_UP_BALANCE = "UPDATE card SET balance = balance + ? WHERE number = ?";
    private final String DELETE_ACCOUNT = "DELETE FROM card WHERE number = ?";
    private final String INCREASE_MONEY = "UPDATE card SET balance = balance + ? WHERE number = ?";
    private final String DECREASE_MONEY = "UPDATE card SET balance = balance - ? WHERE number = ?";

    public DataBase (DataSource dataSource) {
        try {
            this.con = dataSource.getConnection();
            this.statement = con.createStatement();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        createTable();
    }

    private void createTable() {
        try {
            statement.executeUpdate("CREATE TABLE IF NOT EXISTS card(" +
                    "id INTEGER PRIMARY KEY," +
                    "number TEXT NOT NULL," +
                    "pin TEXT NOT NULL," +
                    "balance INTEGER DEFAULT 0)");

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void addCard(String card, String password) {
        try {
            PreparedStatement pstmt = con.prepareStatement(INSERT_INFO);
            pstmt.setString(1, card);
            pstmt.setString(2, password);
            pstmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public boolean checkCard(String card) {
        try {
            Statement pstmt = con.createStatement();
            ResultSet resultSet = pstmt.executeQuery(CHECK_CARD);
            while (resultSet.next()) {
                if(resultSet.getString("number").equals(card)) {
                    return true;
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    public boolean checkCardAndPin(String card, String password) {
        try {
            Statement pstmt = con.createStatement();
            ResultSet resultSet = pstmt.executeQuery(CHECK_CARD_PIN);
            while (resultSet.next()) {
                String number = resultSet.getString("number");
                String pin = resultSet.getString("pin");
                if(number.equals(card) && pin.equals(password)) {
                    return true;
                }
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    public int checkBalance(String card) {
        try {
            PreparedStatement pstmt = con.prepareStatement(CHECK_BALANCE);
            pstmt.setString(1, card);
            ResultSet resultSet = pstmt.executeQuery();
            return resultSet.getInt("balance");
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return 0;
    }

    public void topUpBalance(String card, int income) {
        try {
            PreparedStatement pstmt = con.prepareStatement(TOP_UP_BALANCE);
            pstmt.setInt(1, income);
            pstmt.setString(2, card);
            pstmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void deleteAccount(String card) {
        try {
            PreparedStatement pstmt = con.prepareStatement(DELETE_ACCOUNT);
            pstmt.setString(1, card);
            pstmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void transferMoney(String senderCard, String recipientCard, int value) {
        try {
            con.setAutoCommit(false);

            try (PreparedStatement increase = con.prepareStatement(INCREASE_MONEY);
                 PreparedStatement decrease = con.prepareStatement(DECREASE_MONEY)) {
                increase.setInt(1, value);
                increase.setString(2, recipientCard);
                increase.executeUpdate();

                decrease.setInt(1, value);
                decrease.setString(2, senderCard);
                decrease.executeUpdate();

                con.commit();

            } catch (SQLException e) {
                e.printStackTrace();
            }
        } catch (SQLException exception) {
            if(con != null) {
                try {
                    con.rollback();
                } catch (SQLException exep) {
                    exep.printStackTrace();
                }
            }
        }
    }

    public void closeCon() {
        try {
            con.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}

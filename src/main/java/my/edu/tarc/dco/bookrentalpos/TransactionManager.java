package my.edu.tarc.dco.bookrentalpos;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;

/**
 * Used to load all transactions from the database
 *
 * @author Looz
 * @version 1.0
 */
public class TransactionManager {

    private Transaction[] transactionList;
    private final int ARRAY_SIZE = 100;
    private DBManager db;
    private BookManager bm;
    private int transactionCount;

    /**
     * Contains the rates for the deposit to pay for customer
     * HashMap keys:    rentalWeeks
     * HashMap values:  rates in %
     * If more than 4 weeks, 10% applies
     */
    public final HashMap<Integer, Integer> DEPOSIT_RATES = new HashMap<Integer, Integer>(){
        {
            put(1, 3);
            put(2, 5);
            put(3, 7);
            put(4, 10);
        }
    };

    public TransactionManager(DBManager db, BookManager bm) {
        this.db = db;
        this.bm = bm;
        transactionCount = 0;
        transactionList = new Transaction[ARRAY_SIZE];
        try {
            ResultSet rs = db.resultQuery("SELECT * FROM transactions");
            while (rs.next()) {
                Transaction t = new Transaction(
                        rs.getInt("id"),
                        rs.getString("date"),
                        TransactionType.valueOf(rs.getString("type")),
                        rs.getInt("staffHandled"),
                        rs.getInt("memberInvolved"),
                        rs.getInt("bookInvolved"),
                        rs.getInt("rentDurationInDays"),
                        rs.getDouble("cashFlow")
                );
                transactionList[transactionCount++] = t;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /***
     * this function accepts transaction object where there is no id and date`
     * @param trans accept Transaction object
     * @return Return true if the transaction was added into database successfully
     */
    public boolean addTransaction(Transaction trans) {
        String sql = String.format(
                "INSERT INTO transactions(type, staffHandled, memberInvolved, bookInvolved, rentDurationInDays, cashFlow) VALUES('%s',%d,%d,%d,%d,%f)",
                trans.getType(),
                trans.getStaffHandled(),
                trans.getMemberInvovled(),
                trans.getBookInvovled(),
                trans.getRentDurationInDays(),
                trans.getCashFlow());
        if (db.updateQuery(sql) == 1) {
            try {
                // id and date is generated by sqlite, i need to make a copy of it and store it in my preloaded database
                // this query basically get the latest Mmeber entry inserted into database
                ResultSet rs = db.resultQuery("SELECT id, date FROM member WHERE id = (SELECT seq FROM sqlite_sequence WHERE name='transactions')");
                trans.setID(rs.getInt("id"));
                trans.setDateCreated(rs.getString("date"));

                // store in my preloaded database
                transactionList[transactionCount++] = trans;

                // Update respective table based on the transaction type
                Book b = bm.getBookById(trans.getBookInvovled());
                switch (trans.getType()) {
                    case RENT:
                        b.setLastRentedBy(trans.getMemberInvovled());
                        b.setRented(true);
                        b.setReserved(false);
                        bm.updateBook(b);
                        break;
                    case RETURN:
                        b.setRented(false);
                        bm.updateBook(b);
                        break;
                    case RESERVE:
                        b.setLastReservedBy(trans.getMemberInvovled());
                        b.setReserved(true);
                        bm.updateBook(b);
                        break;
                }

            } catch (SQLException err) {
                System.out.println(err.getMessage());
            }
            return true;
        } else {
            return false;
        }
    }
}

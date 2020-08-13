package my.edu.tarc.dco.bookrentalpos;

import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Class used to load all the Book data from the database
 *
 * @author Looz
 * @version 1.0
 */
public class BookManager {

    private Book[] bookList;
    private int bookCount;
    private DBManager db;
    private final int ARRAY_SIZE = 100;

    public BookManager(DBManager db) {
        bookCount = 0;
        bookList = new Book[ARRAY_SIZE];
        this.db = db;
        String sql = "SELECT * FROM book;";
        try {
            java.sql.ResultSet rs = db.resultQuery(sql);
            while (rs.next()) {
                Book s = new Book(
                        rs.getInt("id"),
                        rs.getString("date"),
                        rs.getString("title"),
                        rs.getString("author"),
                        rs.getDouble("retailPrice"),
                        rs.getInt("lastRentedBy"),
                        rs.getInt("lastReservedBy"),
                        rs.getInt("isRented") == 0 ? false : true,
                        rs.getInt("isReserved") == 0 ? false : true
                );
                bookList[bookCount++] = s;
            }
        } catch (java.sql.SQLException err) {
            System.out.println(err.getMessage());
        }
    }

    /**
     * Get the reference to the book object with specified ID
     *
     * @return Reference to the book object in this class. Will return null if
     * book of specified ID was not found
     */
    public Book getBookById(int bookID) {
        for (int i = 0; i < bookCount; i++) {
            if (bookList[i].getId() == bookID) {
                return bookList[i];
            }
        }
        return null;
    }

    /**
     * Get a copy of the booklist array<br>
     * For now, you should use BookManager.getBookCount() to get the array size
     *
     * @return Book array with constant size of ARRAY_SIZE
     * @see BookManager#getBookCount()
     * @see BookManager#ARRAY_SIZE
     */
    public Book[] getBooKListCache() {
        return this.bookList.clone();
    }

    /**
     * Add new book entry to the database
     *
     * @param book Book object (without ID)
     * @return True if the book is added to database successfully
     * @see Book#Book(String, String, double)
     */
    public boolean addBook(Book book) {
        String sql = String.format("INSERT INTO book(title, retailPrice, author, lastRentedBy, lastReservedBy, isRented, isReserved) VALUES('%s', '%f', '%s', %s, %s, %d, %d)",
                book.getName(),
                book.getRetailPrice(),
                book.getAuthor(),
                book.getLastRentedBy() == 0 ? "null" : book.getLastRentedBy() + "",
                book.getLastReservedBy() == 0 ? "null" : book.getLastReservedBy() + "",
                book.isRented() ? 1 : 0,
                book.isReserved() ? 1 : 0
        );
        if (db.updateQuery(sql) == 1) {
            try {
                // id and date is generated by sqlite, i need to make a copy of it and store it in my preloaded database
                // this query basically get the latest Mmeber entry inserted into database
                ResultSet rs = db.resultQuery("SELECT id, date FROM book WHERE id = (SELECT seq FROM sqlite_sequence WHERE name='book')");
                book.setID(rs.getInt("id"));
                book.setDateCreated(rs.getString("date"));

                // store in my preloaded database
                bookList[bookCount++] = book;
            } catch (SQLException err) {
                System.out.println(err.getMessage());
            }
            return true;
        } else {
            return false;
        }
    }

    /**
     * Update the book entry into database
     *
     * @param bk Book Object to be updated (must contain ID)
     * @return True if the book is successfuly updated. Will return false if it
     * failed or the provided Book object dont contain ID
     */
    public boolean updateBook(Book bk) {
        if (bk.getId() == 0) {
            return false;
        }
        String sql = String.format("UPDATE book\n"
                        + "SET title='%s', author='%s', retailPrice=%f, lastRentedBy=%s, lastReservedBy=%s, isRented=%d, isReserved=%d\n"
                        + "WHERE id=%d;",
                bk.getName(),
                bk.getAuthor(),
                bk.getRetailPrice(),
                bk.getLastRentedBy() == 0 ? "null" : bk.getLastRentedBy() + "",
                bk.getLastReservedBy() == 0 ? "null" : bk.getLastReservedBy() + "",
                bk.isRented() ? 1 : 0,
                bk.isReserved() ? 1 : 0,
                bk.getId());
        if (db.updateQuery(sql) == 1) {
            return true;
        }
        return false;
    }

    /**
     * Remove the book from the database<br>
     * NOTE: All the related table will have this book removed as well
     *
     * @param bookID BookID to be removed
     * @return True if the book is removed successfully
     */
    public boolean removeBook(int bookID) {
        db.execQuery("UPDATE transactions\n"
                + "SET bookInvolved=NULL\n"
                + "WHERE bookInvolved=" + bookID);
        String sql = String.format("DELETE FROM book WHERE id=%d", bookID);
        Book[] tmpList = new Book[ARRAY_SIZE];
        if (db.updateQuery(sql) == 1) {
            int b = 0;
            for (int a = 0; a < bookCount; a++) {
                if (bookList[a].getId() != bookID) {
                    tmpList[b++] = bookList[a];
                }
            }
            bookList = tmpList.clone();
            bookCount--;
            return true;
        } else {
            return false;
        }
    }

    /**
     * @return Book count loaded into this instance from database
     */
    public int getBookCount() {
        return this.bookCount;
    }

}

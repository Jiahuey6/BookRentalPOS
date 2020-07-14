package my.edu.tarc.dco.bookrentalpos;

/**
 *
 * @author Looz
 */
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class StaffManager {

    private ArrayList<Staff> staffList;
    private DBManager db;

    public StaffManager(DBManager db) {
	this.db = db;
	staffList = new ArrayList<Staff>();
	String sql = "SELECT * FROM staff;";
	try {
	    java.sql.ResultSet rs = db.resultQuery(sql);
	    while (rs.next()) {
		Staff s = new Staff(rs.getInt("id") + "", rs.getString("date"), rs.getString("name"), rs.getString("password"));
		staffList.add(s);
	    }
	} catch (java.sql.SQLException err) {
	    System.out.println(err.getMessage());
	}

    }

    public boolean login(String usrName, String pw) {
	for (int i = 0; i < staffList.size(); i++) {
	    if (staffList.get(i).getName().equals(usrName) && staffList.get(i).getPW().equals(CustomUtil.md5Hash(pw))) {
		return true;
	    }
	}
	return false;
    }

    public Staff getStaff(String staffID) {
	for (int i = 0; i < staffList.size(); i++) {
	    if (staffList.get(i).getID().equals(staffID)) {
		return staffList.get(i);
	    }
	}
	return null;
    }

    // return true if registeration successful
    // return false if same name existed
    public boolean registerStaff(Staff stf) {

	String sql = String.format("INSERT INTO staff(name, password) VALUES(\"%s\", \"%s\")", stf.getName(), stf.getPW());
	// updateQuery() return rows affected from the sql query
	if (db.updateQuery(sql) == 1) {
	    try {
		// id and date is generated by sqlite, i need to make a copy of it and store it in my preloaded database
		// this query basically get the latest Staff entry inserted into database
		ResultSet rs = db.resultQuery("SELECT id, date FROM staff WHERE id = (SELECT seq FROM sqlite_sequence)");
		stf.setID(rs.getInt("id") + "");
		stf.setDateCreated(rs.getString("date"));
		
		// store in my preloaded database
		staffList.add(stf);
	    } catch (SQLException err) {
		System.out.println(err.getMessage());
	    }
	    return true;
	} else {
	    return false;
	}
    }

    public boolean removeStaff(String staffID) {
	String sql = String.format("DELETE FROM staff WHERE id=\"%s\"", staffID);
	if (db.updateQuery(sql) == 1) {
	    return true;
	} else {
	    return false;
	}
    }

}
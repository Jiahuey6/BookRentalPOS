package my.edu.tarc.dco.bookrentalpos;

/**
 * Class used to store Staff data
 *
 * @author Looz
 * @version 1.0
 */
public class Staff extends Entity {

    private boolean isAdmin = false;
    private String pwHash;

    /**
     * Constructor used to create new Staff
     *
     * @param usrname username, later used to login
     * @param password password in plain text, later used to login
     */
    public Staff(String usrname, String password) {
	setName(usrname);
	this.pwHash = CustomUtil.md5Hash(password);
    }

    /**
     * Constructor used to import data from database. DO NOT use to create new
     * Staff
     *
     * @param id StaffID generated by database
     * @param dateCreated staff creation date generated by database
     * @param usrname Staff username
     * @param hashedPW md5Hash of the password
     */
    public Staff(int id, String dateCreated, String usrname, String hashedPW) {
	setID(id);
	setName(usrname);
	setDateCreated(dateCreated);
	this.pwHash = hashedPW;
    }

    /**
     * This function does not work yet. (Not implemented)
     * @return false
     */
    public boolean hasAdminPrivillage() {
	return this.isAdmin;
    }

    /**
     * @param password password in plain text.
     */
    public void setPassword(String password) {
	this.pwHash = CustomUtil.md5Hash(password);
    }

    /**
     * 
     * @return Hash of the staff password
     */
    public String getPW() {
	return this.pwHash;
    }
}

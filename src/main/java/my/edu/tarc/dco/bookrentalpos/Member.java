package my.edu.tarc.dco.bookrentalpos;

/**
 *
 * @author Looz
 */
public class Member extends Entity {

    private String phoneNo;
    private String email;
    private String icNo;

    /**
     * Constructor to create a new member without contacts
     *
     * @param icNo NRIC number of the member
     * @param name Name of the member
     */
    public Member(String icNo, String name) {
	this.icNo = icNo;
	setName(name);
	this.phoneNo = "";
	this.email = "";
    }

    /**
     * Constructor to create a new member with contacts
     *
     * @see Member#Member(java.lang.String, java.lang.String)
     */
    public Member(String icNo, String name, String phoneNo, String email) {
	this.icNo = icNo;
	setName(name);
	this.phoneNo = phoneNo;
	this.email = email;
    }

    /**
     * Constructor used to import data from database, DO NOT use it to create
     * new member
     *
     * @param id memberID generated by database
     * @param date creation date of the member in database
     * @see Member#Member(java.lang.String, java.lang.String, java.lang.String,
     * java.lang.String)
     */
    public Member(int id, String date, String name, String phoneNo, String email, String icNo) {
	setID(id);
	setDateCreated(date);
	setName(name);
	this.phoneNo = phoneNo;
	this.email = email;
	this.icNo = icNo;
    }

    /**
     * @param type PHONE / EMAIL
     * @return contacts of the member
     */
    public String getContacts(ContactType type) {
	if (type == ContactType.EMAIL) {
	    return this.email;
	} else if (type == ContactType.PHONE) {
	    return this.phoneNo;
	}
	return null;
    }

    /**
     * 
     * @return NRIC of the member
     */
    public String getICNo() {
	return this.icNo;
    }

}

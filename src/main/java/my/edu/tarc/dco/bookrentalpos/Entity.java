package my.edu.tarc.dco.bookrentalpos;

/**
 * Parent class of all entity in POS system
 *
 * @author Looz
 * @version 1.0
 */
public class Entity {

    private int id;
    private String name;
    private String dateCreated;

    /**
     * @return ID of the entity, usually generated by database
     */
    public final int getId() {
	return this.id;
    }

    /**
     *
     * @return name of the entity
     */
    public final String getName() {
	return this.name;
    }

    /**
     *
     * @return date the entity was created in database, usually generated by
     * database
     */
    public final String getDateCreated() {
	return this.dateCreated;
    }

    /**
     * This function shouldn't be used except for initialization purpose
     *
     * @param id id of the entity
     */
    public final void setID(int id) {
	this.id = id;
    }

    /**
     * @param name name of the entity
     */
    public final void setName(String name) {
	this.name = name;
    }

    /**
     * This function shouldn't be used except for initialization purpose
     *
     * @param date creation date of the entity in database
     */
    public final void setDateCreated(String date) {
	this.dateCreated = date;
    }
}

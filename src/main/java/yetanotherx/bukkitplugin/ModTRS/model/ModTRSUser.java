package yetanotherx.bukkitplugin.ModTRS.model;

import com.avaje.ebean.validation.NotNull;
import java.io.Serializable;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import yetanotherx.bukkitplugin.ModTRS.ModTRS;


@Entity
@Table(name="modtrs_user")
/**
 * Model for a single row in the modtrs_user table
 */
public class ModTRSUser implements Serializable {
    private static final long serialVersionUID = 1L;

    @Id
    private int id;
    
    @NotNull
    private String name;


    public ModTRSUser() {
    }


    public int getId() {
	return id;
    }


    public void setId(int id) {
	this.id = id;
    }


    public String getName() {
	return name;
    }


    public void setName(String name) {
	this.name = name;
    }
    
    public void save(ModTRS parent) {
        parent.getDatabase().save(this);
    }

}

package yetanotherx.bukkitplugin.ModTRS.model;

import java.sql.SQLException;
import java.util.List;
import yetanotherx.bukkitplugin.ModTRS.ModTRS;

/**
 * Model for interacting with the modtrs_user table
 * @author yetanotherx
 */
public class ModTRSUserTable {

    /**
     * ModTRS plugin instance
     */
    private ModTRS parent;

    /**
     * Constructor for ModTRSUserTable
     * @param parent 
     */
    public ModTRSUserTable(ModTRS parent) {
        this.parent = parent;
    }

    /**
     * Returns a {@link ModTRSUser} for the given username. 
     * @param name Username to retrieve
     * @return ModTRSUser
     * @throws SQLException 
     */
    public ModTRSUser getUserFromName(String name) throws SQLException {
        return parent.getDatabase().find(ModTRSUser.class).where().eq("name", name).findUnique();
    }

    /**
     * Returns a {@link ModTRSUser} for the given user ID. 
     * @param id User ID to retrieve
     * @return ModTRSUser
     * @throws SQLException 
     */
    public ModTRSUser getUserFromId(int id) throws SQLException {
        return parent.getDatabase().find(ModTRSUser.class).where().eq("id", String.valueOf(id)).findUnique();
    }
    
    /**
     * Returns a List of all the {@link ModTRSUser}s in the database. 
     * @return ArrayList
     * @throws SQLException 
     */
    public List<ModTRSUser> getUserList() throws SQLException {
        return parent.getDatabase().find(ModTRSUser.class).findList();
    }
}

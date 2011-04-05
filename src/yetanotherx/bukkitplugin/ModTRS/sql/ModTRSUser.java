package yetanotherx.bukkitplugin.ModTRS.sql;

import java.sql.PreparedStatement;
import java.sql.SQLException;

import yetanotherx.bukkitplugin.ModTRS.ModTRSSettings;

public class ModTRSUser {

    private int id;
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



    public boolean insert() throws SQLException {
	PreparedStatement insertPrep = ModTRSSettings.sqlite.prepareStatement( ModTRSSQL.addUserInfo );
	insertPrep.setString(1, this.name);
	return insertPrep.execute();
    }
    
    public boolean update() throws SQLException {
	PreparedStatement updatePrep = ModTRSSettings.sqlite.prepareStatement( ModTRSSQL.setUserInfo );
	updatePrep.setString(1, this.name);
	return updatePrep.execute();
    }


}

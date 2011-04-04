package yetanotherx.bukkitplugin.ModTRS.sql;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import yetanotherx.bukkitplugin.ModTRS.ModTRS;

public class ModTRSUser {
    
    private int id;
    private String name;
    private int lastRequestId;
    
    
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


    public int getLastRequestId() {
        return lastRequestId;
    }


    public void setLastRequestId(int lastRequestId) {
        this.lastRequestId = lastRequestId;
    }
    
    
    
    public static ModTRSUser getUserFromName( String name, ModTRS parent ) throws SQLException {
	
	PreparedStatement prep = parent.sqlite.prepareStatement(ModTRSSQL.getUserInfo);
	prep.setString(1, name);
	ResultSet rs = prep.executeQuery();
	boolean next = rs.next();
	rs.close();
	
	if( !next ) {
	    PreparedStatement insertPrep = parent.sqlite.prepareStatement(ModTRSSQL.addUserInfo);
	    insertPrep.setString(1, name);
	    insertPrep.execute();
	    prep.executeQuery();
	}
	
	rs = prep.executeQuery();
	ModTRSUser user = new ModTRSUser();
	
	while(rs.next()) {
	    user.setId(rs.getInt("user_id"));
	    user.setName(name);
	}
	
	rs.close();
	
	return user;
	
    }


    public static ModTRSUser getUserFromId( int id , ModTRS parent ) throws SQLException {
	
	PreparedStatement prep = parent.sqlite.prepareStatement(ModTRSSQL.getUserInfoFromId);
	prep.setInt(1, id);
	ResultSet rs = prep.executeQuery();
	
	while(rs.next()) {
	    ModTRSUser user = new ModTRSUser();
	    user.setId(id);
	    user.setName(rs.getString("user_name"));
	    rs.close();
	    return user;
	}
	
	rs.close();
	
	return null;
	
    }
    

}

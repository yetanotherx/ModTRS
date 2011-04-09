package yetanotherx.bukkitplugin.ModTRS.sql;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import yetanotherx.bukkitplugin.ModTRS.ModTRSSettings;

public class ModTRSUserTable {

    public static ModTRSUser getUserFromName( String name ) throws SQLException {

	ModTRSSQL.checkDbExists();
	
	PreparedStatement prep = ModTRSSettings.sqlite.prepareStatement(ModTRSSQL.getUserInfoName);
	prep.setString(1, name);
	ResultSet rs = prep.executeQuery();

	while( rs.next() ) {
	    ModTRSUser user = new ModTRSUser();
	    user.setName(name);
	    user.setId(rs.getInt("user_id"));
	    rs.close();
	    return user;
	}

	rs.close();

	return null;

    }


    public static ModTRSUser getUserFromId( int id ) throws SQLException {

	ModTRSSQL.checkDbExists();
	
	PreparedStatement prep = ModTRSSettings.sqlite.prepareStatement(ModTRSSQL.getUserInfoId);
	prep.setInt(1, id);
	ResultSet rs = prep.executeQuery();

	while( rs.next() ) {
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

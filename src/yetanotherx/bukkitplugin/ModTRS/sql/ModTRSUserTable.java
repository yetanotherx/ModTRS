package yetanotherx.bukkitplugin.ModTRS.sql;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import yetanotherx.bukkitplugin.ModTRS.ModTRS;

public class ModTRSUserTable {

    public static ModTRSUser getUserFromName( ModTRS parent, String name ) throws SQLException {

	PreparedStatement prep = parent.databaseHandler.getDatabase().prep(parent.databaseHandler.getDatabase().getUserInfoName());
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


    public static ModTRSUser getUserFromId( ModTRS parent, int id ) throws SQLException {

	PreparedStatement prep = parent.databaseHandler.getDatabase().prep(parent.databaseHandler.getDatabase().getUserInfoId());
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

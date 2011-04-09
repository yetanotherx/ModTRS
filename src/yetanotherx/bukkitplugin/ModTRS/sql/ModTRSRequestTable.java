package yetanotherx.bukkitplugin.ModTRS.sql;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import yetanotherx.bukkitplugin.ModTRS.ModTRSSettings;

public class ModTRSRequestTable {

    public static ModTRSRequest getRequestFromId( int id ) throws SQLException {

	ModTRSSQL.checkDbExists();
	
	PreparedStatement prep = ModTRSSettings.sqlite.prepareStatement(ModTRSSQL.getRequestInfo);
	prep.setInt(1, id);
	ResultSet rs = prep.executeQuery();

	while( rs.next() ) {

	    ModTRSRequest request = new ModTRSRequest();

	    request.setId( rs.getInt("request_id") );
	    request.setUserId( rs.getInt("request_user_id") );
	    request.setModId( rs.getInt("request_mod_user_id") );
	    request.setTimestamp( rs.getLong("request_timestamp") );
	    request.setModTimestamp( rs.getInt("request_mod_timestamp") );
	    request.setWorld( rs.getString("request_world") );
	    request.setX( rs.getInt("request_x") );
	    request.setY( rs.getInt("request_y") );
	    request.setZ( rs.getInt("request_z") );
	    request.setText( rs.getString("request_text") );
	    request.setStatus( rs.getInt("request_status") );

	    rs.close();

	    return request;
	}

	rs.close();

	return null;

    }

    public static ArrayList<ModTRSRequest> getOpenRequests(String type) throws SQLException {

	ModTRSSQL.checkDbExists();
	
	PreparedStatement prep = ModTRSSettings.sqlite.prepareStatement(ModTRSSQL.getOpenRequests);
	ResultSet rs = prep.executeQuery();

	ArrayList<ModTRSRequest> requests = new ArrayList<ModTRSRequest>();

	while( rs.next() ) {

	    ModTRSRequest request = new ModTRSRequest();

	    request.setId( rs.getInt("request_id") );
	    request.setUserId( rs.getInt("request_user_id") );
	    request.setModId( rs.getInt("request_mod_user_id") );
	    request.setTimestamp( rs.getLong("request_timestamp") );
	    request.setModTimestamp( rs.getInt("request_mod_timestamp") );
	    request.setWorld( rs.getString("request_world") );
	    request.setX( rs.getInt("request_x") );
	    request.setY( rs.getInt("request_y") );
	    request.setZ( rs.getInt("request_z") );
	    request.setText( rs.getString("request_text") );
	    request.setStatus( rs.getInt("request_status") );

	    if( type.equals("open") ) {
		if( rs.getInt("request_status") == 0 || rs.getInt("request_status") == 1 ) {
		    requests.add(request);
		}
	    }
	    if( type.equals("all") ) {
		requests.add(request);
	    }
	    if( type.equals("closed") ) {
		if( rs.getInt("request_status") == 3 ) {
		    requests.add(request);
		}
	    }
	    if( type.equals("held") ) {
		if( rs.getInt("request_status") == 2 ) {
		    requests.add(request);
		}
	    }


	}

	rs.close();

	return requests;
    }

}

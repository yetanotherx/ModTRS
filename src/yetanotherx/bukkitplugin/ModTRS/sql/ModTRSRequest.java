package yetanotherx.bukkitplugin.ModTRS.sql;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import org.bukkit.ChatColor;

import yetanotherx.bukkitplugin.ModTRS.ModTRS;

public class ModTRSRequest {

    private int id;
    private int userId;
    private int modId = 0;
    private long modTimestamp = 0;
    private long timestamp;
    private String world;
    private int x, y, z;
    private String text;
    private int status;

    public int getModId() {
	return modId;
    }

    public void setModId(int modId) {
	this.modId = modId;
    }

    public long getModTimestamp() {
	return modTimestamp;
    }

    public void setModTimestamp(long modTimestamp) {
	this.modTimestamp = modTimestamp;
    }

    public int getStatus() {
	return status;
    }

    public void setStatus(int status) {
	this.status = status;
    }

    public ModTRSRequest() {
    }

    public int getId() {
	return id;
    }

    public void setId(int id) {
	this.id = id;
    }

    public int getUserId() {
	return userId;
    }

    public void setUserId(int userId) {
	this.userId = userId;
    }

    public long getTimestamp() {
	return timestamp;
    }

    public void setTimestamp(long timestamp) {
	this.timestamp = timestamp;
    }

    public String getWorld() {
	return world;
    }

    public void setWorld(String world) {
	this.world = world;
    }

    public int getX() {
	return x;
    }

    public void setX(int x) {
	this.x = x;
    }

    public int getY() {
	return y;
    }

    public void setY(int y) {
	this.y = y;
    }

    public int getZ() {
	return z;
    }

    public void setZ(int z) {
	this.z = z;
    }

    public String getText() {
	return text;
    }

    public void setText(String text) {
	this.text = text;
    }

    public boolean insert( ModTRS parent ) throws SQLException {
	PreparedStatement insertPrep = parent.sqlite.prepareStatement(ModTRSSQL.addRequestInfo);
	insertPrep.setInt(1, this.userId );
	insertPrep.setInt(2, this.modId);
	insertPrep.setLong(3, this.timestamp);
	insertPrep.setLong(4, this.modTimestamp);
	insertPrep.setString(5, this.world);
	insertPrep.setInt(6, this.x);
	insertPrep.setInt(7, this.y);
	insertPrep.setInt(8, this.z);
	insertPrep.setString(9, this.text);
	return insertPrep.execute();

    }

    public boolean requestExists( ModTRS parent ) throws SQLException {

	PreparedStatement prep = parent.sqlite.prepareStatement(ModTRSSQL.getRequestInfo);
	prep.setInt(1, this.id);
	ResultSet rs = prep.executeQuery();
	boolean next = rs.next();
	rs.close();

	return next;
    }

    public void getData(ModTRS parent) throws SQLException {

	PreparedStatement prep = parent.sqlite.prepareStatement(ModTRSSQL.getRequestInfo);
	prep.setInt(1, this.id);
	ResultSet rs = prep.executeQuery();

	while( rs.next() ) {
	    this.userId = rs.getInt("request_user_id");
	    this.modId = rs.getInt("request_mod_user_id");
	    this.timestamp = rs.getLong("request_timestamp");
	    this.modTimestamp = rs.getInt("request_mod_timestamp");
	    this.world = rs.getString("request_world");
	    this.x = rs.getInt("request_x");
	    this.y = rs.getInt("request_y");
	    this.z = rs.getInt("request_z");
	    this.text = rs.getString("request_text");
	    this.status = rs.getInt("request_status");
	}

	rs.close();



    }

    public String getStatusText( boolean color ) {
	
	if( color ) {
	    switch( this.status ) {

	    case 0:
		return ChatColor.YELLOW + "Open";
	    case 1:
		return ChatColor.RED + "Claimed";
	    case 2:
		return ChatColor.LIGHT_PURPLE + "On Hold";
	    case 3:
		return ChatColor.GREEN + "Closed";
	    }
	}
	else {
	    switch( this.status ) {

	    case 0:
		return "Open";
	    case 1:
		return "Claimed";
	    case 2:
		return "On Hold";
	    case 3:
		return "Closed";
	    }
	}
	return "";
    }



}

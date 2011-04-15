package yetanotherx.bukkitplugin.ModTRS.sql;

import java.io.File;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

import yetanotherx.bukkitplugin.ModTRS.ModTRSSettings;

public class ModTRSSQL {

    /**
     * Table creation
     */
    public static String createUser = "CREATE TABLE IF NOT EXISTS 'user' ( 'user_id' INTEGER NOT NULL PRIMARY KEY AUTOINCREMENT UNIQUE, 'user_name' TINYTEXT NOT NULL, 'user_last_request_id' MEDIUMINT )";
    public static String createRequest = "CREATE TABLE IF NOT EXISTS 'request' ( 'request_id' INTEGER NOT NULL PRIMARY KEY AUTOINCREMENT UNIQUE, 'request_user_id' INTEGER NOT NULL, 'request_mod_user_id' INTEGER NOT NULL DEFAULT 0, 'request_timestamp' BIGINT NOT NULL, 'request_mod_timestamp' BIGINT NOT NULL DEFAULT 0, 'request_world' TINYTEXT NOT NULL, 'request_x' TINYINT NOT NULL, 'request_y' TINYINT NOT NULL, 'request_z' TINYINT NOT NULL, 'request_text' TEXT NOT NULL, 'request_status' TINYINT DEFAULT 0 )";

    /**
     * User commands
     */
    public static String getUserInfoName = "SELECT * FROM user WHERE user_name = ? LIMIT 1";
    public static String getUserInfoId = "SELECT * FROM user WHERE user_id = ? LIMIT 1";
    public static String addUserInfo = "INSERT INTO user (user_name) VALUES (?)";
    public static String setUserInfo = "UPDATE user SET user_name=? WHERE user_id=?";

    /**
     * Request commands
     */
    public static String getRequestInfo = "SELECT * FROM request WHERE request_id = ? LIMIT 1";
    public static String addRequestInfo = "INSERT INTO 'request' ( 'request_user_id' , 'request_mod_user_id' , 'request_timestamp' , 'request_mod_timestamp' , 'request_world' , 'request_x' , 'request_y' , 'request_z' , 'request_text' ) VALUES ( ? , ? , ? , ? , ? , ? , ? , ? , ? )";
    public static String setRequestInfo = "UPDATE 'request' SET 'request_user_id' = ? , 'request_mod_user_id' = ? , 'request_timestamp' = ? , 'request_mod_timestamp' = ? , 'request_world' = ? , 'request_x' = ? , 'request_y' = ? , 'request_z' = ? , 'request_text' = ? , 'request_status' = ? WHERE request_id = ?";
    public static String getOpenRequests = "SELECT * FROM request";

    public static void checkDbExists() throws SQLException {
	File file = new File( ModTRSSettings.database.get("database") );
	if( !file.exists() ) {
	    String databaseUrl = "jdbc:sqlite:" + ModTRSSettings.database.get("database");
	    ModTRSSettings.sqlite = DriverManager.getConnection(databaseUrl);
	    Statement stat = ModTRSSettings.sqlite.createStatement();
	    stat.executeUpdate(ModTRSSQL.createUser);
	    stat.executeUpdate(ModTRSSQL.createRequest);
	}
    }

}

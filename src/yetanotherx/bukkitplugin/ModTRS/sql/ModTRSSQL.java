package yetanotherx.bukkitplugin.ModTRS.sql;

public class ModTRSSQL {

    /**
     * Table creation
     * TODO: Publish this online
     */
    public static String createUser = "CREATE TABLE IF NOT EXISTS 'user' ( 'user_id' INTEGER NOT NULL PRIMARY KEY AUTOINCREMENT UNIQUE, 'user_name' TINYTEXT NOT NULL, 'user_last_request_id' MEDIUMINT )";
    public static String createRequest = "CREATE TABLE IF NOT EXISTS 'request' ( 'request_id' INTEGER NOT NULL PRIMARY KEY AUTOINCREMENT UNIQUE, 'request_user_id' INTEGER NOT NULL, 'request_mod_user_id' INTEGER NOT NULL DEFAULT 0, 'request_timestamp' BIGINT NOT NULL, 'request_mod_timestamp' BIGINT NOT NULL DEFAULT 0, 'request_world' TINYTEXT NOT NULL, 'request_x' TINYINT NOT NULL, 'request_y' TINYINT NOT NULL, 'request_z' TINYINT NOT NULL, 'request_text' TEXT NOT NULL, 'request_status' TINYINT DEFAULT 0 )";

    /**
     * User commands
     */
    public static String getUserInfo = "SELECT * FROM user WHERE user_name = ? LIMIT 1";
    public static String addUserInfo = "INSERT INTO user (user_name) VALUES (?)";
    public static String setUserInfo = "UPDATE user SET user_name=?, user_last_request_id=? WHERE user_id=?";

}

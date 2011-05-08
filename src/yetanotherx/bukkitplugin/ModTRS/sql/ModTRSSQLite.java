package yetanotherx.bukkitplugin.ModTRS.sql;

import com.griefcraft.lwc.DriverStub;
import com.griefcraft.lwc.Updater;
import java.io.File;
import java.net.URL;
import java.net.URLClassLoader;
import java.sql.Connection;
import java.sql.Driver;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;
import yetanotherx.bukkitplugin.ModTRS.ModTRS;
import yetanotherx.bukkitplugin.ModTRS.ModTRSSettings;

public class ModTRSSQLite extends ModTRSMySQL /* because the syntax is the same */ implements IModTRSDatabase {

    private Connection conn;

    @Override
    public void init(ModTRS parent) throws SQLException {
        String databaseUrl = "jdbc:sqlite:" + ModTRSSettings.database.get("database");

	ModTRS.log.debug("Loading SQLite: " + databaseUrl );

	try {
	    URLClassLoader classLoader = new URLClassLoader(new URL[] { new URL("jar:file:" + new File(Updater.DEST_LIBRARY_FOLDER + "lib/sqlite.jar") + "!/" ) });
	    Driver driver = (Driver) classLoader.loadClass("org.sqlite.JDBC").newInstance();
	    DriverManager.registerDriver(new DriverStub(driver));
	}
	catch( ClassNotFoundException e) {
	    ModTRS.log.severe("Error: Cannot locate the SQLite JDBC. Please download from http://www.zentus.com/sqlitejdbc/ and place in the plugins/ModTRS/lib folder.");
	    parent.getServer().getPluginManager().disablePlugin(parent);
	}
        catch( Exception e) {
	    throw new SQLException(e.getMessage());
	}

	conn = DriverManager.getConnection(databaseUrl);

	ModTRS.log.debug("Creating tables if necessary" );

        Statement stat = conn.createStatement();
	stat.executeUpdate( this.createUser() );
	stat.executeUpdate( this.createRequest() );

	ModTRS.log.debug("Finished loading SQLite" );
    }

    @Override
    public void close() throws SQLException {
        conn.close();
    }

    @Override
    public PreparedStatement prep(String sql) throws SQLException {
        this.dbExists();

        return conn.prepareStatement(sql);
    }

    @Override
    public void dbExists() throws SQLException {

        File file = new File( ModTRSSettings.database.get("database") );
	if( !file.exists() ) {
	    String databaseUrl = "jdbc:sqlite:" + ModTRSSettings.database.get("database");
	    conn = DriverManager.getConnection(databaseUrl);
	    Statement stat = conn.createStatement();
	    stat.executeUpdate( this.createUser() );
	    stat.executeUpdate( this.createRequest() );
	}

    }

    @Override
    public String createUser() {
        return "CREATE TABLE IF NOT EXISTS 'user' ( 'user_id' INTEGER NOT NULL PRIMARY KEY AUTOINCREMENT UNIQUE, 'user_name' TINYTEXT NOT NULL, 'user_last_request_id' MEDIUMINT )";
    }

    @Override
    public String createRequest() {
        return "CREATE TABLE IF NOT EXISTS 'request' ( 'request_id' INTEGER NOT NULL PRIMARY KEY AUTOINCREMENT UNIQUE, 'request_user_id' INTEGER NOT NULL, 'request_mod_user_id' INTEGER NOT NULL DEFAULT 0, 'request_timestamp' BIGINT NOT NULL, 'request_mod_timestamp' BIGINT NOT NULL DEFAULT 0, 'request_world' TINYTEXT NOT NULL, 'request_x' TINYINT NOT NULL, 'request_y' TINYINT NOT NULL, 'request_z' TINYINT NOT NULL, 'request_text' TEXT NOT NULL, 'request_status' TINYINT DEFAULT 0 )";
    }

    @Override
    public String updateLocationFieldsToMediumint() {
        return "";
    }

    @Override
    public String addModCommentAndServerFields() {
        return "ALTER TABLE  `request` ADD  `request_server` TEXT NULL ,ADD  `request_mod_comment` TEXT NULL";
    }

}

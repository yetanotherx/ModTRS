package yetanotherx.bukkitplugin.ModTRS.sql;

import java.sql.SQLException;
import yetanotherx.bukkitplugin.ModTRS.ModTRS;
import yetanotherx.bukkitplugin.ModTRS.ModTRSSettings;
import yetanotherx.bukkitplugin.ModTRS.exception.ShutdownException;

public class ModTRSDatabase {

    private IModTRSDatabase database;

    public ModTRSDatabase(ModTRS parent) throws ShutdownException, SQLException {

        if (ModTRSSettings.database.get("type").equals("sqlite")) {
            database = new ModTRSSQLite();
        } else if (ModTRSSettings.database.get("type").equals("mysql")) {
            database = new ModTRSMySQL();
        } else if (ModTRSSettings.database.get("type").equals("postgresql")) {
            database = new ModTRSPostgreSQL();
        } else {
            throw new ShutdownException( "Database may only be one of the following: sqlite, mysql, postgresql" );
        }

        database.init(parent);

    }

    public IModTRSDatabase getDatabase() {
        return database;
    }

    public void setDatabase(IModTRSDatabase database) {
        this.database = database;
    }
}

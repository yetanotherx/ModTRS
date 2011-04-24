package yetanotherx.bukkitplugin.ModTRS.sql;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import yetanotherx.bukkitplugin.ModTRS.ModTRS;

public class ModTRSPostgreSQL implements IModTRSDatabase {

    private Connection conn;

    @Override
    public void init(ModTRS parent) {
        throw new UnsupportedOperationException("PostgreSQL is not supported yet. Please use MySQL or SQLite");
    }

    @Override
    public void close() throws SQLException {
        conn.close();
    }

    @Override
    public PreparedStatement prep(String sql) throws SQLException {
        this.dbExists();
        
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public void dbExists() throws SQLException {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public String createUser() {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public String createRequest() {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public String getUserInfoName() {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public String getUserInfoId() {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public String addUserInfo() {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public String setUserInfo() {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public String getRequestInfo() {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public String addRequestInfo() {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public String setRequestInfo() {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public String getOpenRequests() {
        throw new UnsupportedOperationException("Not supported yet.");
    }
}

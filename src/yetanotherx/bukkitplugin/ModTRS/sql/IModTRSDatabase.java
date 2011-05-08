package yetanotherx.bukkitplugin.ModTRS.sql;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;
import yetanotherx.bukkitplugin.ModTRS.ModTRS;

public interface IModTRSDatabase {

    public PreparedStatement prep( String sql ) throws SQLException;
    public Statement stat() throws SQLException;

    public void init( ModTRS parent ) throws SQLException;

    public void close() throws SQLException;

    public void dbExists() throws SQLException;

    public String createUser();
    public String createRequest();

    /**
     * User commands
     */
    public String getUserInfoName();
    public String getUserInfoId();
    public String addUserInfo();
    public String setUserInfo();

    /**
     * Request commands
     */
    public String getRequestInfo();
    public String getRequestInfoFromUserId();
    public String addRequestInfo();
    public String setRequestInfo();
    public String getOpenRequests();

    /**
     * Update commands
     */
    public String updateLocationFieldsToMediumint();
    public String addModCommentField();
    public String addServerField();

}

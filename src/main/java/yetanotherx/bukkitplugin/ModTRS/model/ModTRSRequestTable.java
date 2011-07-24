package yetanotherx.bukkitplugin.ModTRS.model;

import com.avaje.ebean.PagingList;
import com.avaje.ebean.Query;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import yetanotherx.bukkitplugin.ModTRS.ModTRS;

/**
 * Model for interacting with the modtrs_request table
 * @author yetanotherx
 */
public class ModTRSRequestTable {

    /**
     * ModTRS plugin instance
     */
    private ModTRS parent;

    /**
     * Constructor for ModTRSRequestTable
     * @param parent 
     */
    public ModTRSRequestTable(ModTRS parent) {
        this.parent = parent;
    }
    
    /**
     * Returns a {@link ModTRSRequest} for the given ID. 
     * @param id ID to retrieve
     * @return ModTRSRequest
     * @throws SQLException 
     */
    public ModTRSRequest getRequestFromId(int id) throws SQLException {
        return parent.getDatabase().find(ModTRSRequest.class).where().eq("id", String.valueOf(id)).findUnique();
    }
    
    /**
     * Returns a List of all the {@link ModTRSRequest}s that a user has created
     * @param username Username to lookup
     * @return ArrayList of {@link ModTRSRequest}
     * @throws SQLException 
     */
    public List<ModTRSRequest> getRequestsFromUser(String username) throws SQLException {
        ModTRSUser user = parent.getTableHandler().getUser().getUserFromName(username);
        if( user == null ) {
            return new ArrayList<ModTRSRequest>();
        }
        return this.getRequestsFromUserQuery(user.getId()).findList();
    }
    
    /**
     * Returns a List of all the completed {@link ModTRSRequest}s that a user has created, 
     * that they have not been notified of.
     * @param username Username to lookup
     * @return ArrayList of {@link ModTRSRequest}
     * @throws SQLException 
     */
    public List<ModTRSRequest> getUnnotifiedRequestsFromUser(String username) throws SQLException {
        ModTRSUser user = parent.getTableHandler().getUser().getUserFromName(username);
        if( user == null ) {
            return new ArrayList<ModTRSRequest>();
        }
        return this.getUnnotifiedRequestsFromUserQuery(user.getId()).findList();
    }
    
    /**
     * Returns a List of all the open {@link ModTRSRequest}s that a user has created
     * @param username Username to lookup
     * @return ArrayList of {@link ModTRSRequest}
     * @throws SQLException 
     */
    public List<ModTRSRequest> getOpenRequestsFromUser(String username) throws SQLException {
        ModTRSUser user = parent.getTableHandler().getUser().getUserFromName(username);
        if( user == null ) {
            return new ArrayList<ModTRSRequest>();
        }
        return this.getOpenRequestsFromUserQuery(user.getId()).findList();
    }
    
    /**
     * Returns a {@link PagingList} of all the open {@link ModTRSRequest}s that a user has created
     * @param username Username to lookup
     * @param max Results per page to show
     * @return PagingList of {@link ModTRSRequest}
     * @throws SQLException 
     */
    public PagingList<ModTRSRequest> getOpenRequestsFromUserPager(String username, int max) throws SQLException {
        ModTRSUser user = parent.getTableHandler().getUser().getUserFromName(username);
        if( user == null ) {
            return null;
        }
        return this.getOpenRequestsFromUserQuery(user.getId()).findPagingList(max);
    }
    
    /**
     * Returns the number of {@link ModTRSRequest}s that a user has created
     * @param username Username to lookup
     * @return int
     * @throws SQLException 
     */
    public int getNumberOfRequestsFromUser(String username) throws SQLException {
        ModTRSUser user = parent.getTableHandler().getUser().getUserFromName(username);
        if( user == null ) {
            return 0;
        }
        return this.getRequestsFromUserQuery(user.getId()).findRowCount();
    }
    
    /**
     * Returns a List of all the {@link ModTRSRequest}s that a user ID has created
     * @param id User ID to lookup
     * @return ArrayList of {@link ModTRSRequest}
     * @throws SQLException 
     */
    public List<ModTRSRequest> getRequestsFromUser(int id) throws SQLException {
        ModTRSUser user = parent.getTableHandler().getUser().getUserFromId(id);
        if( user == null ) {
            return new ArrayList<ModTRSRequest>();
        }
        return this.getRequestsFromUserQuery(id).findList();
    }
    
    /**
     * Returns a List of all the completed {@link ModTRSRequest}s that a user ID has created, 
     * that they have not been notified of.
     * @param id User ID to lookup
     * @return ArrayList of {@link ModTRSRequest}
     * @throws SQLException 
     */
    public List<ModTRSRequest> getUnnotifiedRequestsFromUser(int id) throws SQLException {
        ModTRSUser user = parent.getTableHandler().getUser().getUserFromId(id);
        if( user == null ) {
            return new ArrayList<ModTRSRequest>();
        }
        return getUnnotifiedRequestsFromUserQuery(id).findList();
    }
    
    /**
     * Returns a List of all the open {@link ModTRSRequest}s that a user ID has created
     * @param id User ID to lookup
     * @return ArrayList of {@link ModTRSRequest}
     * @throws SQLException 
     */
    public List<ModTRSRequest> getOpenRequestsFromUser(int id) throws SQLException {
        ModTRSUser user = parent.getTableHandler().getUser().getUserFromId(id);
        if( user == null ) {
            return new ArrayList<ModTRSRequest>();
        }
        return this.getOpenRequestsFromUserQuery(id).findList();
    }
    
    /**
     * Returns a {@link PagingList} of all the open {@link ModTRSRequest}s that a user ID has created
     * @param id User ID to lookup
     * @param max Results per page to show
     * @return PagingList of {@link ModTRSRequest}
     * @throws SQLException 
     */
    public PagingList<ModTRSRequest> getOpenRequestsFromUserPager(int id, int max) throws SQLException {
        ModTRSUser user = parent.getTableHandler().getUser().getUserFromId(id);
        if( user == null ) {
            return null;
        }
        return this.getOpenRequestsFromUserQuery(id).findPagingList(max);
    }
    
    /**
     * Returns the number of {@link ModTRSRequest}s that a user ID has created
     * @param id User ID to lookup
     * @return int
     * @throws SQLException 
     */
    public int getNumberOfRequestsFromUser(int id) throws SQLException {
        ModTRSUser user = parent.getTableHandler().getUser().getUserFromId(id);
        if( user == null ) {
            return 0;
        }
        return this.getRequestsFromUserQuery(id).findRowCount();
    }
    
    /**
     * Returns a {@link PagingList} of all the requests of a certain type
     * @param type Can be one of the following: open, closed, claimed, held
     * @param max Number of results to show per page
     * @return PagingList
     * @throws SQLException 
     */
    public PagingList<ModTRSRequest> getRequestsPager(String type, int max) throws SQLException {

        Query<ModTRSRequest> query = this.getRequestsQuery(type);
        
        if( query != null ) {
            return query.findPagingList(max);
        }
        return null;

    }
    
    /**
     * Returns a {@link ArrayList} of all the requests of a certain type
     * @param type Can be one of the following: open, closed, claimed, held
     * @return ArrayList
     * @throws SQLException 
     */
    public List<ModTRSRequest> getRequests(String type) throws SQLException {

        Query<ModTRSRequest> query = this.getRequestsQuery(type);
        
        if( query != null ) {
            return query.findList();
        }
        return new ArrayList<ModTRSRequest>();

    }
    
    private Query<ModTRSRequest> getRequestsFromUserQuery(int id) {
        return parent.getDatabase().find(ModTRSRequest.class).where().eq("userId", id).query();
    }
    
    private Query<ModTRSRequest> getUnnotifiedRequestsFromUserQuery(int id) {
        return parent.getDatabase().find(ModTRSRequest.class).where().eq("userId", id).eq("notifiedOfCompletion", 0).eq("status", 3).query();
    }
    
    private Query<ModTRSRequest> getOpenRequestsFromUserQuery(int id) {
        return parent.getDatabase().find(ModTRSRequest.class).where().eq("userId", id).or(parent.getDatabase().getExpressionFactory().eq("status", "0"), parent.getDatabase().getExpressionFactory().eq("status", "1")).query();
    }
    
    /**
     * Returns a {@link Query} that can be executed to get a 
     * list of requests of a certain type
     * @param type Can be one of the following: open, closed, claimed, held
     * @return Query
     * @throws SQLException 
     */
    private Query<ModTRSRequest> getRequestsQuery(String type) throws SQLException {

        if (type.equals("open")) {
            return parent.getDatabase().find(ModTRSRequest.class).where().or(parent.getDatabase().getExpressionFactory().eq("status", "0"), parent.getDatabase().getExpressionFactory().eq("status", "1")).query();
        }
        if (type.equals("all")) {
            return parent.getDatabase().find(ModTRSRequest.class);
        }
        if (type.equals("closed")) {
            return parent.getDatabase().find(ModTRSRequest.class).where().eq("status", "3").query();
        }
        if (type.equals("held")) {
            return parent.getDatabase().find(ModTRSRequest.class).where().eq("status", "2").query();
        }
        return null;

    }
    

}

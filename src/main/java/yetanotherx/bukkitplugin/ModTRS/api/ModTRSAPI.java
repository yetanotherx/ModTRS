package yetanotherx.bukkitplugin.ModTRS.api;

import java.sql.SQLException;
import yetanotherx.bukkitplugin.ModTRS.ModTRS;
import yetanotherx.bukkitplugin.ModTRS.command.CommandResult;
import yetanotherx.bukkitplugin.ModTRS.model.ModTRSRequest;
import yetanotherx.bukkitplugin.ModTRS.model.ModTRSRequestTable;
import yetanotherx.bukkitplugin.ModTRS.model.ModTRSUser;
import yetanotherx.bukkitplugin.ModTRS.model.ModTRSUserTable;
import yetanotherx.bukkitplugin.ModTRS.command.FakeCommandSender;
import yetanotherx.bukkitplugin.ModTRS.event.EventHandler;
import yetanotherx.bukkitplugin.ModTRS.event.SaveRowEvent;

/**
 * API to interact with the ModTRS plugin
 * 
 * @author yetanotherx
 */
public class ModTRSAPI {

    /**
     * ModTRS plugin instance
     */
    private ModTRS parent;

    /**
     * Create a new API instance
     * 
     * @param parent Instance of the ModTRS plugin, retrieved from Server.getPlugin()
     */
    private ModTRSAPI(ModTRS parent) {
        this.parent = parent;
    }

    /**
     * Get a new API instance
     * @param parent Instance of the ModTRS plugin, retrieved from Server.getPlugin()
     * @return ModTRSAPI API instance
     */
    public static ModTRSAPI load(ModTRS parent) {
        ModTRS.log.debug("Initalizing the API!");
        return new ModTRSAPI(parent);
    }
    
    /**
     * Insert/Update a {@link ModTRSRequest} or {@link ModTRSUser} model. 
     * If the model does not exist in the database, it will
     * insert automatically. Otherwise, it will simply update.
     * 
     * @param model {@link ModTRSRequest} or {@link ModTRSUser} object
     */
    public void saveRow(Object model) {
        if( !(model instanceof ModTRSRequest) || !(model instanceof ModTRSUser) ) return;
        
        SaveRowEvent event = new SaveRowEvent(model);
        EventHandler.getInstance().dispatch(event);
        if (event.isCancelled()) {
            return;
        }
        
        parent.getDatabase().save(event.getModel());
    }
    
    /**
     * Returns an instance of the {@link ModTRSRequestTable} class
     * 
     * @return ModTRSRequestTable
     */
    public ModTRSRequestTable getRequestTable() {
        return parent.getTableHandler().getRequest();
    }
    
    /**
     * Returns an instance of the {@link ModTRSUserTable} class
     * 
     * @return ModTRSRequestTable
     */
    public ModTRSUserTable getUserTable() {
        return parent.getTableHandler().getUser();
    }

    /**
     * Gets a {@link ModTRSUser} instance with the username given.
     * If there is not a row in the database with that username,
     * it will automatically create one.
     * 
     * @param name Username to get
     * @return {@link ModTRSUser} instance for the user
     * @throws SQLException 
     */
    public ModTRSUser getUserFromName(String name) throws SQLException {
        ModTRSUser user = parent.getTableHandler().getUser().getUserFromName(name);

        if (user == null) {
            user = new ModTRSUser();
            user.setName(name);
            this.saveRow(user);
            user = parent.getTableHandler().getUser().getUserFromName(name);
        }
        
        return user;
        
    }
    
    /**
     * Run a ModTRS command from the given username, bypassing all permissions. Returns a {@link CommandResult} instance
     * 
     * For example: sendCommandWithUsername( "Yetanotherx", "check", "p:2", "t:open" ) 
     * is equal to Yetanotherx typing this in chat: /check p:2 t:open
     * 
     * @param username Username to run the command from
     * @param command Command name to run
     * @param args Arguments to pass
     * @return CommandResult
     */
    public CommandResult sendCommandWithUsername(String username, String command, String ... args) {
        FakeCommandSender sender = new FakeCommandSender(username);
        boolean output = parent.onCommand(sender, parent.getCommand(command), command, args);
        return new CommandResult(sender.getResult(), output);
    }
    
    /**
     * Run a ModTRS command (bypassing all permissions). Returns a {@link CommandResult} instance.
     * 
     * For example: sendCommandWithUsername( "check", "p:2", "t:open" ) 
     * is equal to a user called "internal-code" typing this in chat: /check p:2 t:open
     * 
     * @param command Command name to run
     * @param args Arguments to pass
     * @return CommandResult
     */
    public CommandResult sendCommand(String command, String ... args) {
        FakeCommandSender sender = new FakeCommandSender();
        boolean output = parent.onCommand(sender, parent.getCommand(command), command, args);
        return new CommandResult(sender.getResult(), output);
    }

    /**
     * Returns the instance of the ModTRS plugin. 
     * @return ModTRS
     */
    public ModTRS getPlugin() {
        return parent;
    }
    
}

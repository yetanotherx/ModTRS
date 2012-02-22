package yetanotherx.bukkitplugin.ModTRS;

import yetanotherx.bukkitplugin.ModTRS.util.ModTRSPermissions;
import yetanotherx.bukkitplugin.ModTRS.util.ModTRSSettings;
import yetanotherx.bukkitplugin.ModTRS.util.ModTRSUpdate;
import yetanotherx.bukkitplugin.ModTRS.util.ModTRSLogger;
import yetanotherx.bukkitplugin.ModTRS.command.CommandHandler;
import yetanotherx.bukkitplugin.ModTRS.model.ModTRSRequest;
import yetanotherx.bukkitplugin.ModTRS.model.ModTRSTableHandler;
import yetanotherx.bukkitplugin.ModTRS.model.ModTRSUser;
import yetanotherx.bukkitplugin.ModTRS.util.MessageHandler;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.plugin.java.JavaPlugin;
import java.util.ArrayList;
import javax.persistence.PersistenceException;
import org.bukkit.event.Event;
import yetanotherx.bukkitplugin.ModTRS.api.ModTRSAPI;
import yetanotherx.bukkitplugin.ModTRS.listener.ModTRSListener;


/*
 * ModTRS Version 2.1 - Moderator Request Ticket System
 * Copyright (C) 2011 Yetanotherx <yetanotherx -a--t- gmail -dot- com>
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 2 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 * 
 * Unless otherwise specified, this license applies to all the files in 
 * the 'yetanothrx.bukkitplugin.ModTRS' package, and all its subpackages.
 */
public class ModTRS extends JavaPlugin {

    public static final ModTRSLogger log = new ModTRSLogger();
    private CommandHandler commandHandler;
    private ModTRSListener listener;
    private ModTRSTableHandler tableHandler;
    private ModTRSAPI api;

    /**
     * Outputs a message when disabled
     */
    @Override
    public void onDisable() {
        log.info("Plugin disabled. (version " + this.getDescription().getVersion() + ")");
    }

    /**
     * Perform some massive loading action
     * 
     * Step 1: Load and parse config file, creating it if it doesn't exist.
     * Step 2: Load and parse the messages files
     * Step 3: Check for updates in the code
     * Step 4: Load the Permissions plugin
     * Step 5: Initialize the Help page
     * Step 6: Set up the database code
     * Step 7: Register the two persistence table models
     * Step 8: Register events and listeners
     * Step 9: Register commands
     * Step 10: Load the API
     * 
     */
    @Override
    public void onEnable() {

        try {
            ModTRSSettings.load(this);
            ModTRSPermissions.load(this);
            MessageHandler.getInstance().load(this);
            this.setupDatabase();
            ModTRSUpdate.load(this);
            this.tableHandler = ModTRSTableHandler.load(this);
            this.listener = new ModTRSListener(this);
            this.commandHandler = CommandHandler.load(this);
            this.api = ModTRSAPI.load(this);
            
            this.getServer().getPluginManager().registerEvents(this.listener, this);

            log.info("Plugin enabled! (version " + this.getDescription().getVersion() + ")");
            log.debug("Debug mode enabled!");

        } catch (Exception e) {
            log.severe("Caught an exception, disabling plugin! " + e.getMessage());
            e.printStackTrace();
            this.getServer().getPluginManager().disablePlugin(this);
            return;
        }
    }

    public void setupDatabase() {

        try {
            getDatabase().find(ModTRSRequest.class).findRowCount();
            getDatabase().find(ModTRSUser.class).findRowCount();
        } catch (PersistenceException ex) {
            ModTRS.log.debug("Installing database due to first time usage");
            installDDL();
        }
    }

    @Override
    public ArrayList<Class<?>> getDatabaseClasses() {
        ArrayList<Class<?>> list = new ArrayList<Class<?>>();
        list.add(ModTRSRequest.class);
        list.add(ModTRSUser.class);
        return list;
    }

    public ModTRSTableHandler getTableHandler() {
        return this.tableHandler;
    }

    /**
     * Returns the API instance
     * @return ModTRSAPI
     */
    public ModTRSAPI getAPI() {
        return api;
    }

    public CommandHandler getCommandHandler() {
        return commandHandler;
    }

    /**
     * Calls the command handler, which performs some magic to perform the command
     */
    @Override
    public boolean onCommand(CommandSender sender, Command command, String commandLabel, String[] args) {
        return this.commandHandler.onCommand(sender, command, commandLabel, args);
    }

}

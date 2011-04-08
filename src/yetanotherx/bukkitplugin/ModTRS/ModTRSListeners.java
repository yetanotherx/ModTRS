package yetanotherx.bukkitplugin.ModTRS;

import java.util.HashMap;

import org.bukkit.event.Event;
import org.bukkit.event.Listener;

import yetanotherx.bukkitplugin.ModTRS.listener.ModTRSPlayerListener;

public class ModTRSListeners {

    /**
     * Main plugin class
     */
    private ModTRS parent;
    
    /**
     * List of all event listeners
     */
    private HashMap<String, Listener> listeners = new HashMap<String, Listener>();
    
    public ModTRSListeners(ModTRS parent) {
	this.parent = parent;
    }

    /**
     * Initialize the listeners and connect them to events
     */
    public static ModTRSListeners load(ModTRS parent) {
	ModTRSListeners listener = new ModTRSListeners(parent);
	
	listener.registerListener( "player", new ModTRSPlayerListener(parent) );
	
	listener.registerEvent( Event.Type.PLAYER_JOIN, "player", Event.Priority.Monitor );
	return listener;
    }
    
    /**
     * Add a new listener
     */
    public void registerListener( String name, Listener listener ) {
	this.listeners.put(name, listener);
    }
    
    /**
     * Add a new event
     */
    public void registerEvent( Event.Type type, String listener, Event.Priority priority ) {
	parent.getServer().getPluginManager().registerEvent(type, listeners.get(listener), priority, parent);
    }
    

}

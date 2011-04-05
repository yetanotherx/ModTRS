package yetanotherx.bukkitplugin.ModTRS;

import java.util.HashMap;

import org.bukkit.event.Event;
import org.bukkit.event.Listener;

import yetanotherx.bukkitplugin.ModTRS.listener.ModTRSPlayerListener;

public class ModTRSListeners {

    private ModTRS parent;
    private HashMap<String, Listener> listeners = new HashMap<String, Listener>();
    
    public ModTRSListeners(ModTRS parent) {
	this.parent = parent;
    }

    public static ModTRSListeners load(ModTRS parent) {
	ModTRSListeners listener = new ModTRSListeners(parent);
	
	listener.registerListener( "player", new ModTRSPlayerListener(parent) );
	
	listener.registerEvent( Event.Type.PLAYER_JOIN, "player", Event.Priority.Monitor );
	return listener;
    }
    
    public void registerListener( String name, Listener listener ) {
	this.listeners.put(name, listener);
    }
    
    public void registerEvent( Event.Type type, String listener, Event.Priority priority ) {
	parent.getServer().getPluginManager().registerEvent(type, listeners.get(listener), priority, parent);
    }
    

}

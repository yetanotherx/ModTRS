package yetanotherx.bukkitplugin.ModTRS;

import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Adds a debug method, and prefixes all log messages with [ModTRS]
 */
public class ModTRSLogger {

    public static final Logger logger = Logger.getLogger("Minecraft");
    
    public void info( String s ) {
	logger.log(Level.INFO, "[ModTRS] " + s);
    }
    
    public void debug( String s ) {
	if( ModTRSSettings.debugMode ) {
	    logger.log(Level.INFO, "[ModTRS DEBUG] " + s);
	}
    }
    
    public void severe( String s ) {
	logger.log(Level.SEVERE, "[ModTRS] " + s);
    }
    
    public void warning( String s ) {
	logger.log(Level.WARNING, "[ModTRS] " + s);
    }
    
}

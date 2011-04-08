package yetanotherx.bukkitplugin.ModTRS.validator;

import java.util.HashMap;

public class ModTRSValidatorHandler {
    
    /**
     * Singleton instance
     */
    private static ModTRSValidatorHandler instance;
    
    /**
     * List of all validators
     */
    private HashMap<String, ModTRSValidator> validators = new HashMap<String, ModTRSValidator>();
    
    /**
     * Singleton instance retrieval
     */
    public static ModTRSValidatorHandler getInstance() {
	if( instance == null ) {
	    instance = new ModTRSValidatorHandler();
	}
	return instance;
    }
    
    /**
     * Adds a validator
     */
    public void registerValidator( String command, ModTRSValidator validator ) {
	validators.put(command, validator);
    }
    
    /**
     * Returns true if a validator exists
     */
    public boolean hasValidator( String command ) {
	return validators.containsKey(command);
    }
    
    /**
     * Returns the validator
     */
    public ModTRSValidator getValidator( String command ) {
	return validators.get(command);
    }

}

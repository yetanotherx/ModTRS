package yetanotherx.bukkitplugin.ModTRS.validator;

import java.util.HashMap;

public class ValidatorHandler {
    
    /**
     * Singleton instance
     */
    private static ValidatorHandler instance;
    
    /**
     * List of all validators
     */
    private HashMap<String, Validator> validators = new HashMap<String, Validator>();
    
    /**
     * Singleton instance retrieval
     */
    public static ValidatorHandler getInstance() {
	if( instance == null ) {
	    instance = new ValidatorHandler();
	}
	return instance;
    }
    
    /**
     * Adds a validator
     */
    public void registerValidator( String command, Validator validator ) {
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
    public Validator getValidator( String command ) {
	return validators.get(command);
    }

}

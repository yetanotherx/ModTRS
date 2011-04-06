package yetanotherx.bukkitplugin.ModTRS.validator;

import java.util.HashMap;

public class ModTRSValidatorHandler {
    
    private static ModTRSValidatorHandler instance;
    
    private HashMap<String, ModTRSValidator> validators = new HashMap<String, ModTRSValidator>();
    
    public static ModTRSValidatorHandler getInstance() {
	if( instance == null ) {
	    instance = new ModTRSValidatorHandler();
	}
	return instance;
    }
    
    public void registerValidator( String command, ModTRSValidator validator ) {
	
	validators.put(command, validator);
	
    }
    
    public boolean hasValidator( String command ) {
	return validators.containsKey(command);
    }
    
    public ModTRSValidator getValidator( String command ) {
	return validators.get(command);
    }

}

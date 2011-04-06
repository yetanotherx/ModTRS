package yetanotherx.bukkitplugin.ModTRS.validator;

import org.bukkit.command.CommandExecutor;

import yetanotherx.bukkitplugin.ModTRS.ModTRS;

public class CheckValidator extends ModTRSValidatorBase implements ModTRSValidator {

    public CheckValidator(CommandExecutor command, ModTRS parent) {
    }
    
    @Override
    public boolean isValid(String[] args) {
	
	for( String arg : args ) {
	    if( arg.substring(0, 2).equals("p:") ) {
		if( !this.isInteger(arg.substring(2)) ) return false;
	    }
	    else if( arg.substring(0, 2).equals("t:") ) {
		if( !this.inArray(arg.substring(2), new String[] { "open", "closed", "held", "all"} ) ) return false;
	    }
	    else {
		return false;
	    }
	}
	
	if( this.isAtLeastArgs(args, 3) ) return false;
	
	return true;
    }


}

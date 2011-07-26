package yetanotherx.bukkitplugin.ModTRS.validator;

import org.bukkit.command.CommandExecutor;

import yetanotherx.bukkitplugin.ModTRS.ModTRS;

public class CheckValidator extends Validator {

    public CheckValidator(CommandExecutor command, ModTRS parent) {
    }
    
    @Override
    public boolean isValid(String[] args) {
	
	for( String arg : args ) {
	    
	    String arg2 = arg;
	    
	    if( arg.length() < 2 ) {
		arg2 += " ";
	    }
	    
	    if( arg2.substring(0, 2).equals("p:") ) {
		if( !this.isInteger(arg2.substring(2)) ) return false; //p:* must be an integer
	    }
	    else if( arg2.substring(0, 2).equals("t:") ) {
		//t:* must be one of these types
		if( !this.inArray(arg2.substring(2), new String[] { "open", "closed", "held", "all"} ) ) return false;
	    }
	    else {
		if( !this.isInteger(arg) ) return false; //Integers only
		if( this.isAtLeastArgs(args, 2) ) return false; //Only allow 1 arg if one of the args does not start with p: or t:
	    }
	}
	
	if( this.isAtLeastArgs(args, 3) ) return false; //Cannot be more than 2 arguments
	
	return true;
    }


}

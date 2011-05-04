package yetanotherx.bukkitplugin.ModTRS.validator;

import org.bukkit.command.CommandExecutor;

import yetanotherx.bukkitplugin.ModTRS.ModTRS;

public class CompleteValidator extends ModTRSValidatorBase implements ModTRSValidator {

    public CompleteValidator(CommandExecutor command, ModTRS parent) {
    }
    
    @Override
    public boolean isValid(String[] args) {
	if( !this.isAtLeastArgs(args, 1) ) return false; //Must have 1 argument
	if( this.isAtLeastArgs(args, 3) ) return false; //Can't have 3
        if( this.isAtLeastArgs(args, 2) ) {
            if( !args[1].equals("-silent") ) return false; //Must be -silent
        }
	if( !this.isInteger(args[0]) ) return false; //The argument must be a number
	return true;
    }

}

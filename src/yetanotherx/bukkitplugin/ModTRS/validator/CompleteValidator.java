package yetanotherx.bukkitplugin.ModTRS.validator;

import org.bukkit.command.CommandExecutor;

import yetanotherx.bukkitplugin.ModTRS.ModTRS;

public class CompleteValidator extends ModTRSValidatorBase implements ModTRSValidator {

    public CompleteValidator(CommandExecutor command, ModTRS parent) {
    }
    
    @Override
    public boolean isValid(String[] args) {
	
	if( !this.isAtLeastArgs(args, 1) ) return false;
	if( this.isAtLeastArgs(args, 2) ) return false;
	if( !this.isInteger(args[0]) ) return false;
	
	return true;
    }

}

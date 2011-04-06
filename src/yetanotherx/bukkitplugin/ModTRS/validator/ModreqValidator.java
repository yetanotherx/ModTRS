package yetanotherx.bukkitplugin.ModTRS.validator;

import org.bukkit.command.CommandExecutor;

import yetanotherx.bukkitplugin.ModTRS.ModTRS;

public class ModreqValidator extends ModTRSValidatorBase implements ModTRSValidator {

    public ModreqValidator(CommandExecutor command, ModTRS parent) {
    }
    
    @Override
    public boolean isValid(String[] args) {
	
	if( !this.isAtLeastArgs(args, 1) ) return false;
	
	return true;
    }

}
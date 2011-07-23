package yetanotherx.bukkitplugin.ModTRS.validator;

import org.bukkit.command.CommandExecutor;

import yetanotherx.bukkitplugin.ModTRS.ModTRS;

public class BroadcastValidator extends ValidatorBase implements Validator {

    public BroadcastValidator(CommandExecutor command, ModTRS parent) {
    }

    @Override
    public boolean isValid(String[] args) {
	
	if( !this.isAtLeastArgs(args, 1) ) return false; //Must be at least 1 argument
	
	return true;
    }

}

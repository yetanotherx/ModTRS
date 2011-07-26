package yetanotherx.bukkitplugin.ModTRS.validator;

import org.bukkit.command.CommandExecutor;

import yetanotherx.bukkitplugin.ModTRS.ModTRS;

public class OneArgumentIntegerValidator extends Validator {

    public OneArgumentIntegerValidator(CommandExecutor command, ModTRS parent) {
    }
    
    @Override
    public boolean isValid(String[] args) {
	if( !this.isAtLeastArgs(args, 1) ) return false; //Must have 1 argument
	if( !this.isInteger(args[0]) ) return false; //The argument must be a number
	return true;
    }

}

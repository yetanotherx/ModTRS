package yetanotherx.bukkitplugin.ModTRS.validator;

import org.bukkit.command.CommandExecutor;
import yetanotherx.bukkitplugin.ModTRS.ModTRS;

public class ReloadValidator extends ValidatorBase implements Validator {

    public ReloadValidator(CommandExecutor command, ModTRS parent) {
    }

    @Override
    public boolean isValid(String[] args) {

	if( this.isAtLeastArgs(args, 1) ) return false; //No arguments

	return true;
    }

}
package yetanotherx.bukkitplugin.ModTRS.validator;

import org.bukkit.command.CommandExecutor;
import yetanotherx.bukkitplugin.ModTRS.ModTRS;

public class BanValidator extends ValidatorBase implements Validator {

    public BanValidator(CommandExecutor command, ModTRS parent) {
    }
    
    @Override
    public boolean isValid(String[] args) {
	if( !this.isAtLeastArgs(args, 1) ) return false; //Must have 1 argument
	return true;
    }

}

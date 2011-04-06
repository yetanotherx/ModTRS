package yetanotherx.bukkitplugin.ModTRS.validator;

import org.bukkit.command.CommandExecutor;

import yetanotherx.bukkitplugin.ModTRS.ModTRS;

public class HelpValidator extends ModTRSValidatorBase implements ModTRSValidator {

    public HelpValidator(CommandExecutor command, ModTRS parent) {
    }

    @Override
    public boolean isValid(String[] args) {
	
	//No validation needed
	
	return true;
    }

}

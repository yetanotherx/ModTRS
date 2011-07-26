package yetanotherx.bukkitplugin.ModTRS.validator;

import org.bukkit.command.CommandExecutor;
import yetanotherx.bukkitplugin.ModTRS.ModTRS;

public class NoArgumentsValidator extends Validator {

    public NoArgumentsValidator(CommandExecutor command, ModTRS parent) {
    }

    @Override
    public boolean isValid(String[] args) {
        if (this.isAtLeastArgs(args, 1)) {
            return false; //No arguments
        }
        return true;
    }
}

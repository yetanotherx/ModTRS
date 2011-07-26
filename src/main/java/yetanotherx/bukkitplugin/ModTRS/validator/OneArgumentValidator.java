package yetanotherx.bukkitplugin.ModTRS.validator;

import org.bukkit.command.CommandExecutor;
import yetanotherx.bukkitplugin.ModTRS.ModTRS;

public class OneArgumentValidator extends Validator {

    public OneArgumentValidator(CommandExecutor command, ModTRS parent) {
    }

    @Override
    public boolean isValid(String[] args) {
        if (!this.isAtLeastArgs(args, 1)) {
            return false; //No arguments
        }
        return true;
    }
    
}

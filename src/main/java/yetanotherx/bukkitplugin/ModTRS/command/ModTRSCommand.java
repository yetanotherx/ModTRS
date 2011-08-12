package yetanotherx.bukkitplugin.ModTRS.command;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

import yetanotherx.bukkitplugin.ModTRS.validator.ValidatorHandler;
import yetanotherx.bukkitplugin.ModTRS.ModTRS;
import yetanotherx.bukkitplugin.ModTRS.validator.NoArgumentsValidator;

public class ModTRSCommand implements CommandExecutor {

    private ModTRS parent;
    
    public ModTRSCommand(ModTRS parent) {
        this.parent = parent;
        ValidatorHandler.getInstance().registerValidator("modtrs", new NoArgumentsValidator(this, parent));
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String commandLabel, String[] args) {

        HelpCommand help = new HelpCommand(parent);

        if (ValidatorHandler.getInstance().hasValidator("help")) {
            if (!ValidatorHandler.getInstance().getValidator("help").isValid(args)) {
                return false;
            }
        }
        return help.onCommand(sender, command, commandLabel, args);

    }
}

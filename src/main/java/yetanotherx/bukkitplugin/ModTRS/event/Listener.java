package yetanotherx.bukkitplugin.ModTRS.event;

public abstract class Listener {
    
    public abstract void onCommandModBroadcast(CommandModBroadcastEvent event);
    public abstract void onCommandCheck(CommandCheckEvent event);
    public abstract void onCommandCheckId(CommandCheckIdEvent event);
    public abstract void onCommandClaim(CommandClaimEvent event);
    public abstract void onCommandComplete(CommandCompleteEvent event);
    public abstract void onCommandHelp(CommandHelpEvent event);
    public abstract void onCommandHold(CommandHoldEvent event);
    public abstract void onCommandModlist(CommandModlistEvent event);
    public abstract void onCommandModreq(CommandModreqEvent event);
    public abstract void onCommandReload(CommandReloadEvent event);
    public abstract void onCommandReopen(CommandReopenEvent event);
    public abstract void onCommandTeleport(CommandTeleportEvent event);
    public abstract void onCommandUnclaim(CommandUnclaimEvent event);
    
    
}

package yetanotherx.bukkitplugin.ModTRS.event;

import java.util.ArrayList;

/**
 * Event manager class (singleton)
 * 
 * @author yetanotherx
 */
public class EventHandler {

    /**
     * List of all the listeners that are connected to the plugin
     */
    private ArrayList<Listener> listeners = new ArrayList<Listener>();

    /**
     * Private constructor to ensure a singleton
     */
    private EventHandler() {
    }

    /**
     * Register a listener to the plugin. 
     * When an event is called, each listener gets called
     * @param listener Listener to register
     */
    public void register(Listener listener) {
        if (!listeners.contains(listener)) {
            listeners.add(listener);
        }
    }

    /**
     * Call an event, sending it to all the listeners.
     * The listeners that were registered latest have the highest priority. 
     * @param event Event to send
     */
    public void dispatch(Event event) {
        for (Listener listener : this.listeners) {
            switch (event.getType()) {
                case COMMAND_BAN:
                    listener.onCommandBan((CommandBanEvent) event);
                    break;
                case COMMAND_UNBAN:
                    listener.onCommandUnban((CommandUnbanEvent) event);
                    break;
                case COMMAND_MOD_BROADCAST:
                    listener.onCommandModBroadcast((CommandModBroadcastEvent) event);
                    break;
                case COMMAND_CHECK:
                    listener.onCommandCheck((CommandCheckEvent) event);
                    break;
                case COMMAND_CHECK_ID:
                    listener.onCommandCheckId((CommandCheckIdEvent) event);
                    break;
                case COMMAND_CLAIM:
                    listener.onCommandClaim((CommandClaimEvent) event);
                    break;
                case COMMAND_COMPLETE:
                    listener.onCommandComplete((CommandCompleteEvent) event);
                    break;
                case COMMAND_HELP:
                    listener.onCommandHelp((CommandHelpEvent) event);
                    break;
                case COMMAND_HOLD:
                    listener.onCommandHold((CommandHoldEvent) event);
                    break;
                case COMMAND_MODLIST:
                    listener.onCommandModlist((CommandModlistEvent) event);
                    break;
                case COMMAND_MODREQ:
                    listener.onCommandModreq((CommandModreqEvent) event);
                    break;
                case COMMAND_RELOAD:
                    listener.onCommandReload((CommandReloadEvent) event);
                    break;
                case COMMAND_REOPEN:
                    listener.onCommandReopen((CommandReopenEvent) event);
                    break;
                case COMMAND_TELEPORT:
                    listener.onCommandTeleport((CommandTeleportEvent) event);
                    break;
                case COMMAND_UNCLAIM:
                    listener.onCommandUnclaim((CommandUnclaimEvent) event);
                    break;
            }
        }
        
    }

    /**
     * Singleton caller for EventHandler
     * @return EventHandler instance
     */
    public static EventHandler getInstance() {
        return EventHandlerHolder.INSTANCE;
    }

    /**
     * Singleton holder
     */
    private static class EventHandlerHolder {

        /**
         * Singleton instance
         */
        private static final EventHandler INSTANCE = new EventHandler();
    }
}

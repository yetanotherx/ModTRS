package yetanotherx.bukkitplugin.ModTRS;

import yetanotherx.bukkitplugin.ModTRS.message.BroadcastMessages;
import yetanotherx.bukkitplugin.ModTRS.message.CheckIdMessages;
import yetanotherx.bukkitplugin.ModTRS.message.CheckMessages;
import yetanotherx.bukkitplugin.ModTRS.message.ClaimMessages;
import yetanotherx.bukkitplugin.ModTRS.message.ClosedMessages;
import yetanotherx.bukkitplugin.ModTRS.message.GeneralMessages;
import yetanotherx.bukkitplugin.ModTRS.message.HoldMessages;
import yetanotherx.bukkitplugin.ModTRS.message.ModlistMessages;
import yetanotherx.bukkitplugin.ModTRS.message.ModreqMessages;
import yetanotherx.bukkitplugin.ModTRS.message.ReopenMessages;
import yetanotherx.bukkitplugin.ModTRS.message.UnclaimMessages;

public class ModTRSMessage {

    public static GeneralMessages general = new GeneralMessages();
    public static BroadcastMessages broadcast = new BroadcastMessages();
    public static CheckIdMessages checkid = new CheckIdMessages();
    public static CheckMessages check = new CheckMessages();
    public static ClaimMessages claim = new ClaimMessages();
    public static ClosedMessages closed = new ClosedMessages();
    public static HoldMessages hold = new HoldMessages();
    public static ModlistMessages modlist = new ModlistMessages();
    public static ModreqMessages modreq = new ModreqMessages();
    public static ReopenMessages reopen = new ReopenMessages();
    public static UnclaimMessages unclaim = new UnclaimMessages();

}

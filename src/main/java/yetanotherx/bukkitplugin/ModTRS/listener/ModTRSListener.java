package yetanotherx.bukkitplugin.ModTRS.listener;

import java.util.List;
import org.bukkit.ChatColor;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerListener;

import yetanotherx.bukkitplugin.ModTRS.ModTRS;
import yetanotherx.bukkitplugin.ModTRS.model.ModTRSRequest;
import yetanotherx.bukkitplugin.ModTRS.util.Message;
import yetanotherx.bukkitplugin.ModTRS.command.ModTRSCommandSender;
import yetanotherx.bukkitplugin.ModTRS.util.ModTRSSettings;

public class ModTRSListener extends PlayerListener {

    private ModTRS parent;

    public ModTRSListener(ModTRS parent) {
        this.parent = parent;
    }

    /**
     * Simply notifies a user that there are open requests when they join
     */
    @Override
    public void onPlayerJoin(PlayerJoinEvent event) {

        ModTRSCommandSender player = new ModTRSCommandSender(event.getPlayer());

        if (!ModTRSSettings.notifyMods) {
            return;
        }

        if (player.hasPerm("modtrs.mod")) {

            try {
                int count = parent.getTableHandler().getRequest().getRequestsPager("open", 1).getTotalRowCount();
                if (count != 0) {
                    player.sendMessage(Message.parse("general.open_reqs", count));
                }

                List<ModTRSRequest> reqs = parent.getTableHandler().getRequest().getUnnotifiedRequestsFromUser(player.getName());
                if (reqs.size() > 0) {
                    player.sendMessage(Message.parse("closed.user.offline"));
                }

                int iter = 0;
                for (ModTRSRequest req : reqs) {
                    if (iter > 0) {
                        player.sendMessage(ChatColor.GOLD + "----------------");
                    }
                    player.sendMessage(Message.parse("closed.text", req.getText()));
                    
                    if( !req.getModComment().isEmpty() ) {
                        player.sendMessage(Message.parse("closed.mod_comment", req.getModComment()));
                    }
                    
                    iter++;
                }

            } catch (Exception e) {
                e.printStackTrace();
            }

        }
    }
}

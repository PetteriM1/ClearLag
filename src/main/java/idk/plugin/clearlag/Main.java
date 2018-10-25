package idk.plugin.clearlag;

import cn.nukkit.Server;
import cn.nukkit.command.Command;
import cn.nukkit.command.CommandSender;
import cn.nukkit.entity.Entity;
import cn.nukkit.entity.EntityHuman;
import cn.nukkit.event.Listener;
import cn.nukkit.level.Level;
import cn.nukkit.plugin.PluginBase;

public class Main extends PluginBase implements Listener {

    @Override
    public void onEnable() {
        getServer().getPluginManager().registerEvents(this, this);
    }

    public boolean onCommand(CommandSender sender, Command cmd, String commandLabel, String[] args) {
        if (cmd.getName().equalsIgnoreCase("clearlag")) {
            if (!sender.hasPermission("clearlag.run")) {
                sender.sendMessage("\u00A7cYou don't have permission to use this command");
                return true;
            }

            for (Level level : Server.getInstance().getLevels().values()) {
                for (Entity entity : level.getEntities()) {
                    if (!(entity instanceof EntityHuman)) {
                        entity.close();
                    }
                }
            }

            for (Level level : getServer().getLevels().values()) {
                level.doChunkGarbageCollection();
                level.unloadChunks(true);
            }
            System.gc();

            sender.sendMessage("\u00A73[ClearLag] \u00A7aLag cleared!");

            return true;
        }
        return false;
    }
}

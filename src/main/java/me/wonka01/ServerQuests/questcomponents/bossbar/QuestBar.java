package me.wonka01.ServerQuests.questcomponents.bossbar;

import me.knighthat.apis.utils.Colorization;
import me.wonka01.ServerQuests.ServerQuests;
import org.bukkit.Bukkit;
import org.bukkit.boss.BarColor;
import org.bukkit.boss.BarStyle;
import org.bukkit.boss.BossBar;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;

public class QuestBar implements Colorization {

    public static String barColor;
    private final BossBar bossBar;

    public QuestBar(String displayName) {
        BarColor color = BarColor.valueOf(barColor);
        bossBar = Bukkit.getServer().createBossBar(color(displayName), color, BarStyle.SEGMENTED_12);
        bossBar.setProgress(0.0);
        bossBar.setVisible(true);
    }

    public void updateBarProgress(double barRatio) {
        if (barRatio < 0.0 || barRatio > 1.0) {
            JavaPlugin.getPlugin(ServerQuests.class).getLogger().info("Invalid bar ratio provided");
            return;
        }
        bossBar.setProgress(barRatio);
    }

    public void removeBossBar() {
        bossBar.setVisible(false);
        bossBar.removeAll();
    }

    public void hideBossBar(Player player) {
        bossBar.removePlayer(player);
    }

    public void toggleBossBar(Player player) {
        boolean showPlayerBar = true;
        for (Player p : bossBar.getPlayers()) {
            if (p.getUniqueId().equals(player.getUniqueId())) {
                showPlayerBar = false;
                break;
            }
        }

        if (showPlayerBar) {
            bossBar.addPlayer(player);
        } else {
            bossBar.removePlayer(player);
        }
    }

    public void showBossBar(Player player) {
        bossBar.addPlayer(player);
    }

}

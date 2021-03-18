package me.wonka01.ServerQuests.questcomponents;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.boss.BarColor;
import org.bukkit.boss.BarStyle;
import org.bukkit.boss.BossBar;
import org.bukkit.entity.Player;

public class QuestBar {

    private BossBar bossBar;

    public QuestBar(String displayName) {
        bossBar = Bukkit.getServer().createBossBar(ChatColor.YELLOW + "" + ChatColor.BOLD + displayName, BarColor.GREEN, BarStyle.SEGMENTED_12);
        bossBar.setProgress(1.0);
        bossBar.setVisible(true);
    }

    public void updateBarProgress(double barRatio) {
        bossBar.setProgress(barRatio);
    }

    public void removeBossBar() {
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

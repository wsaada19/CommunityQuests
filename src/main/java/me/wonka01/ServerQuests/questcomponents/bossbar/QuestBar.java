package me.wonka01.ServerQuests.questcomponents.bossbar;

import lombok.NonNull;
import lombok.Setter;
import me.knighthat.apis.utils.Colorization;
import org.bukkit.Bukkit;
import org.bukkit.boss.BarColor;
import org.bukkit.boss.BarStyle;
import org.bukkit.boss.BossBar;
import org.bukkit.entity.Player;

public class QuestBar implements Colorization {
    private final @NonNull BarColor barColor;
    private final BossBar bar;

    public QuestBar(@NonNull String name, @NonNull String color, @NonNull BarStyle style) {
        Bukkit.broadcastMessage("Adding with color " + color);
        this.barColor = getBarColor(color);
        Bukkit.broadcastMessage("Actually adding with color " + this.barColor.toString());
        bar = Bukkit.createBossBar(color(name), this.barColor, style);
        bar.setVisible(true);
        bar.setProgress(0.0);
    }

    private @NonNull BarColor getBarColor(@NonNull String color) {
        for (BarColor c : BarColor.values())
            if (c.name().equalsIgnoreCase(color))
                return c;

        return BarColor.WHITE;
    }

    public void updateBarProgress(double barRatio) {
        if (barRatio < 0.0 || barRatio > 1.0) {
            return;
        }
        bar.setProgress(barRatio);
    }

    public void removeBossBar() {
        bar.setVisible(false);
        bar.removeAll();
    }

    public void hideBossBar(Player player) {
        bar.removePlayer(player);
    }

    public void toggleBossBar(Player player) {
        boolean showPlayerBar = true;
        for (Player p : bar.getPlayers()) {
            if (p.getUniqueId().equals(player.getUniqueId())) {
                showPlayerBar = false;
                break;
            }
        }

        if (showPlayerBar) {
            bar.addPlayer(player);
        } else {
            bar.removePlayer(player);
        }
    }

    public void showBossBar(Player player) {
        bar.addPlayer(player);
    }

}

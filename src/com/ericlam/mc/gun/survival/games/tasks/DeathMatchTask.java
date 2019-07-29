package com.ericlam.mc.gun.survival.games.tasks;

import com.ericlam.mc.gun.survival.games.main.GunSG;
import com.ericlam.mc.minigames.core.main.MinigamesCore;
import com.ericlam.mc.minigames.core.manager.PlayerManager;
import org.bukkit.Bukkit;

public class DeathMatchTask extends GunSGTask {

    @Override
    public void initRun(PlayerManager playerManager) {

    }

    @Override
    public void onCancel() {
        this.onFinish();
    }

    @Override
    public void onFinish() {
        MinigamesCore.getApi().getGameManager().endGame(playerManager.getGamePlayer(), null, true);
    }

    @Override
    public void run(long l) {
        if (l % 60 == 0 || l < 6 || l == 10){
            String time = MinigamesCore.getApi().getGameUtils().getTimeWithUnit(l);
            Bukkit.broadcastMessage(configManager.getMessage("deathmatch-count").replace("<time>", time));
            Bukkit.getOnlinePlayers().forEach(GunSG::playCountSound);
            if (l == 60){
                Bukkit.broadcastMessage(configManager.getMessage("glowing-mode"));
                playerManager.getGamePlayer().forEach(g->g.getPlayer().setGlowing(true));
            }
        }
        int level = (int)l;
        Bukkit.getOnlinePlayers().forEach(p->p.setLevel(level));
    }

    @Override
    public long getTotalTime() {
        return configManager.getData("deathMatchTime", Long.class).orElse(300L);
    }

    @Override
    public boolean shouldCancel() {
        return playerManager.getGamePlayer().size() <= 1;
    }
}

package com.sucy.active.enchants;

import org.bukkit.entity.Entity;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;
import org.bukkit.scheduler.BukkitRunnable;

/**
 * Damages nearby enemies over time when a player is dashing
 */
public class DashTask extends BukkitRunnable {

    /**
     * Plugin reference
     */
    Plugin plugin;

    /**
     * Player reference
     */
    Player player;

    /**
     * Enchantment reference
     */
    Dash enchant;

    /**
     * Damage to deal per run
     */
    int damage;

    /**
     * Remaining runs to do
     */
    int remaining;

    /**
     * Constructor
     *
     * @param plugin    plugin reference
     * @param player    player reference
     * @param damage    damage to deal
     * @param remaining remaining runs
     */
    public DashTask(Plugin plugin, Dash enchant, Player player, int damage, int remaining) {
        this.plugin = plugin;
        this.player = player;
        this.enchant = enchant;
        this.damage = damage;
        this.remaining = remaining;
    }

    /**
     * Damages all nearby enemies and queues another task if necessary
     */
    public void run() {
        for (Entity entity : player.getNearbyEntities(1, 1, 1)) {
            if (enchant.works(entity, player)) ((LivingEntity) entity).damage(damage, player);
        }
        if (remaining > 0) new DashTask(plugin, enchant, player, damage, remaining - 1).runTaskLater(plugin, 4);
    }
}

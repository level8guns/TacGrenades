package me.JayzaSapphire;

import java.util.List;

import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.entity.Projectile;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.ProjectileHitEvent;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

public class TacGrenades extends JavaPlugin implements Listener {

	public void onEnable() {
		getServer().getPluginManager().registerEvents(this, this);
	}
	
	@EventHandler
	public void onProjectileHit(ProjectileHitEvent event) {
		Entity ent = event.getEntity();
		Projectile p = (Projectile)ent;
		Entity shooter = p.getShooter();
		
		if (ent.getType() == EntityType.SNOWBALL) {
			if (shooter.getType() == EntityType.PLAYER) {
				Player user = (Player)shooter;
				
				if (user.hasPermission("tac.flashbang") || user.hasPermission("tac.*")) {
					List<Entity> elist = ent.getNearbyEntities(3.0, 3.0, 3.0);
					
					for (int i = 0; i < elist.size(); i++) {
						Entity testEnt = elist.get(i);
						
						if (testEnt instanceof LivingEntity) {
							LivingEntity e = (LivingEntity)testEnt;
							e.addPotionEffect(new PotionEffect(PotionEffectType.BLINDNESS, 10 * 20, 2));
						}
					}
				}
			}
		}
		
		if (ent.getType() == EntityType.THROWN_EXP_BOTTLE) {
			if (shooter.getType() == EntityType.PLAYER) {
				Player user = (Player)shooter;
				
				if (user.hasPermission("tac.stun") || user.hasPermission("tac.*")) {
					List<Entity> elist = ent.getNearbyEntities(3.0, 3.0, 3.0);
					
					for (int i = 0; i < elist.size(); i++) {
						Entity testEnt = elist.get(i);
						
						if (testEnt instanceof LivingEntity) {
							LivingEntity e = (LivingEntity)testEnt;
							e.addPotionEffect(new PotionEffect(PotionEffectType.CONFUSION, 10 * 20, 2));
						}
					}
				}
			}
		}
	}
	
	public void onDisable() {
		
	}
}

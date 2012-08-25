package me.JayzaSapphire;

import java.util.HashMap;
import java.util.List;

import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.entity.Projectile;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.entity.ProjectileHitEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerRespawnEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

public class TacGrenades extends JavaPlugin implements Listener {
	
	private HashMap<String, Location> insertions = new HashMap<String, Location>();

	public void onEnable() {
		getServer().getPluginManager().registerEvents(this, this);
	}
	
	@EventHandler
	public void onPlayerRespawn(PlayerRespawnEvent event) {
		Player player = event.getPlayer();
		
		if (insertions.containsKey(player.getName())) {
			event.setRespawnLocation(insertions.get(player.getName()));
			insertions.remove(player.getName());
		}
	}
	
	@EventHandler
	public void onPlayerInteract(PlayerInteractEvent event) {
		Player player = event.getPlayer();
		
		if (event.getAction() == Action.RIGHT_CLICK_BLOCK) {
			if (player.hasPermission("tac.insertion") || player.hasPermission("tac.*")) {
				ItemStack iih = player.getItemInHand();
				
				World world = event.getClickedBlock().getLocation().getWorld();
				int x = event.getClickedBlock().getLocation().getBlockX();
				int y = event.getClickedBlock().getLocation().getBlockY();
				int z = event.getClickedBlock().getLocation().getBlockZ();
				
				if (iih.getType() == Material.SLIME_BALL) {
					Location loc = new Location(world, x, y, z);
					
					insertions.put(player.getName(), loc);
					player.sendMessage(ChatColor.DARK_AQUA + "Tactical insertion placed.");
				}
			}
		}
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
					List<Entity> elist = ent.getNearbyEntities(2.5, 2.5, 2.5);
					
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
		
		if (ent.getType() == EntityType.EGG) {
			if (shooter.getType() == EntityType.PLAYER) {
				Player user = (Player)shooter;
				
				if (user.hasPermission("tac.smoke") || user.hasPermission("tac.*")) {
					List<Entity> elist = ent.getNearbyEntities(2.5, 2.5, 2.5);
					
					for (int i = 0; i < elist.size(); i++) {
						Entity testEnt = elist.get(i);
						
						if (testEnt instanceof LivingEntity) {
							LivingEntity e = (LivingEntity)testEnt;
							e.addPotionEffect(new PotionEffect(PotionEffectType.POISON, 20 * 20, 2));
						}
					}
				}
			}
		}
	}
	
	public void onDisable() {
		
	}
}

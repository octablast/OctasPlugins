package io.github.octablast.OctasPlugins;

import org.bukkit.event.Event;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.util.Vector;
import org.bukkit.*;
import org.bukkit.entity.Player;

public class PlayerActions implements Listener {
	private final Plugin1 plugin;
	public PlayerActions(Plugin1 instance) {
        plugin = instance;
    }
	
	boolean fallDamageImmune = false;
	@EventHandler
    public void onLeftClick(PlayerInteractEvent event) {
        Player player = event.getPlayer();
        if(player.getInventory().getItemInMainHand().getType().equals(Material.CHISELED_POLISHED_BLACKSTONE)) {
            if(event.getAction().equals(Action.LEFT_CLICK_AIR)|| event.getAction().equals(Action.LEFT_CLICK_BLOCK)) {
               	new PlayerCommands(plugin).towerCommand(player);
            }
        }
        if(player.getInventory().getItemInMainHand().getType().equals(Material.WOODEN_PICKAXE)) {
            if(event.getAction().equals(Action.LEFT_CLICK_AIR)|| event.getAction().equals(Action.LEFT_CLICK_BLOCK)) {
               	new PlayerCommands(plugin).cubeCommand(player,new String[]{"2"});
            }
        }
        if(player.getInventory().getItemInMainHand().getType().equals(Material.STICK)) {
			player.setVelocity(player.getVelocity().add(new Vector(0,0,1).rotateAroundX(player.getLocation().getPitch()*Math.PI/180).rotateAroundY(player.getLocation().getYaw()*Math.PI/-180)));
			fallDamageImmune = true;
			player.setFallDistance(-500);
		}
	}
	public void onPlayerMove(PlayerMoveEvent event) {
		if(event.getPlayer().getWorld().getBlockAt(event.getPlayer().getLocation().add(new Vector(0,-2,0))).getType()!=Material.AIR) {
			if(fallDamageImmune) {
				event.getPlayer().setFallDistance(-500);
				fallDamageImmune = false;
				event.getPlayer().sendMessage("Should have prot");
			}
		}
	}
}

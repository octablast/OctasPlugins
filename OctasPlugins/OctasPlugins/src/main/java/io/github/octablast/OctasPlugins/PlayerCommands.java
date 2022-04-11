package io.github.octablast.OctasPlugins;

import org.bukkit.*;
import org.bukkit.block.Block;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.event.Listener;
import org.bukkit.event.player.*;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.util.Vector;

public class PlayerCommands implements CommandExecutor{
	private final Plugin1 plugin;
	public PlayerCommands(Plugin1 instance) {
        plugin = instance;
    }
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
		if (cmd.getName().equalsIgnoreCase("cube") && sender instanceof Player) { // If the player typed /basic then do the following, note: If you only registered this executor for one command, you don't need this
			cubeCommand(sender, args);
			return true;
		}
		if(cmd.getName().equalsIgnoreCase("tower") && sender instanceof Player){
			towerCommand(sender);
		}
	        
		return false; 
	}
	public void cubeCommand(CommandSender sender,String[] args){
		Player player = (Player)sender;
		player.sendMessage("Your X Coordinates : " + player.getLocation().getX());
		Location location = player.getLocation();
		World world = location.getWorld();
		Server server = plugin.getServer();
		int s = Integer.parseInt(args[0]);
		int i=0;
		for(int y=location.getBlockY()+s;y>=location.getBlockY()-s;y--) {
			for(int x=location.getBlockX()+s;x>=location.getBlockX()-s;x--) {
				i++;
				for(int z=location.getBlockZ()-s;z<=location.getBlockZ()+s;z++) {
					if(world.getBlockAt(x,y,z).getType()!=Material.AIR) {
						
						final int xx=x,yy=y,zz=z;
						final Player p = player;
						final World w = world;
						server.getScheduler().scheduleSyncDelayedTask(plugin, new Runnable() {
							public void run() {
								Block currentBlock = w.getBlockAt(xx,yy,zz);
								p.getInventory().addItem(currentBlock.getDrops().toArray(new ItemStack[currentBlock.getDrops().size()]));
								currentBlock.setType(Material.AIR);
							}
						},(long)i/2);
					}
				}
			}
		}
	}
	public void towerCommand(CommandSender sender) {
		Player player = (Player)sender;
		Location location = player.getLocation();
		World world = location.getWorld();
		Server server = plugin.getServer();
		//player.setVelocity(new Vector(0,2,0));  //super simple, better below
		final World w = world;
		final Location l = location;
		final Player p = player;
		server.getScheduler().scheduleSyncDelayedTask(plugin, new Runnable() {
			public void run() {
				p.setVelocity(p.getVelocity().add(new Vector(0,2,0).rotateAroundX(p.getLocation().getPitch()*Math.PI/180).rotateAroundY(p.getLocation().getYaw()*Math.PI/-180)));
				for(int x = -3;x<=3;x++)
					for(int z = -3;z<=3;z++)
						w.getBlockAt(l.clone().add(new Vector(x,-2,z))).setType(Material.CHISELED_RED_SANDSTONE);
				p.sendMessage("why");
			}
		}, 0);
		server.getScheduler().scheduleSyncDelayedTask(plugin, new Runnable() {
			public void run() {
				for(int x = -4;x<=4;x++)
					for(int z = -4;z<=4;z++)
						w.getBlockAt(l.clone().add(new Vector(x,-1,z))).setType(Material.CHISELED_NETHER_BRICKS);
				p.sendMessage("why");
			}
		}, 2);
		server.getScheduler().scheduleSyncDelayedTask(plugin, new Runnable() {
			public void run() {
				for(int x = -3;x<=3;x++)
					for(int z = -3;z<=3;z++)
						w.getBlockAt(l.clone().add(new Vector(x,-1,z))).setType(Material.WATER);
				p.sendMessage("why");
			}
		}, 25);
		for(int i=0;i<50;i++)
			server.getScheduler().scheduleSyncDelayedTask(plugin, new Runnable() {
				public void run() {
					w.getBlockAt(p.getLocation().subtract(new Vector(0,2,0))).setType(Material.CHISELED_POLISHED_BLACKSTONE);
				}
			}, (long)i/2);
		server.getScheduler().scheduleSyncDelayedTask(plugin, new Runnable() {
			public void run() {
				for(int x = -1;x<=1;x++)
					for(int z = -1;z<=1;z++)
						w.getBlockAt(p.getLocation().add(new Vector(x,-1,z))).setType(Material.CHISELED_QUARTZ_BLOCK);
			}
		}, 25);
	}
}
		
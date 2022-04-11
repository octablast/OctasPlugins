package io.github.octablast.OctasPlugins;

import org.bukkit.event.Listener;
import org.bukkit.plugin.java.JavaPlugin;

public class Plugin1 extends JavaPlugin {
	private final PlayerCommands playerCommands = new PlayerCommands(this);
	private final Listener playerActions = new PlayerActions(this);
	public void onEnable() {
		getLogger().info("onEnable has been invokled!");
		getCommand("cube").setExecutor(playerCommands);
		getCommand("tower").setExecutor(playerCommands);
		getServer().getPluginManager().registerEvents(playerActions, this);
	}
	
	public void onDisable() {
		getLogger().info("onDisable has been invokled!");
	}
}
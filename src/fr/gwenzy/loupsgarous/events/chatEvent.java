package fr.gwenzy.loupsgarous.events;

import java.io.File;

import org.bukkit.ChatColor;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;
import org.bukkit.metadata.FixedMetadataValue;

import fr.gwenzy.loupsgarous.main.Main;

public class chatEvent implements Listener {

	private Main plugin;
	public chatEvent(Main plugin)
	{
		this.plugin = plugin;
	}
	@EventHandler
	public void playerChatEvent(AsyncPlayerChatEvent event)
	{
		Player p = event.getPlayer();
		try
		{
			if(p.getMetadata("waitForTime").get(0).asBoolean())
			{
				int newTime = Integer.valueOf(event.getMessage());
				String path = "game.times."+p.getMetadata("timeChangeRole").get(0).asString();
				File partyFile = null;
				boolean ok = false;
				for(File f : new File("plugins/LoupsGarous/games/").listFiles())
				{
				
					if(f.getName().split("!!")[1].replace(".ini", "").equalsIgnoreCase(event.getPlayer().getName()))
					{

						partyFile = f;
						ok = true;
						break;
					}
				}
				FileConfiguration config = YamlConfiguration.loadConfiguration(partyFile);
				
				config.set(path, newTime);
				p.sendMessage(ChatColor.GREEN+Main.pn+"LE temps est désormais mis à jour !");
				p.setMetadata("waitForTime", new FixedMetadataValue(plugin, false));
				event.setCancelled(true);
				config.save(partyFile);
			}
		}
		catch(Exception e)
		{
			
		}
	}
}

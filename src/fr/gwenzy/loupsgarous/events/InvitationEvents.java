package fr.gwenzy.loupsgarous.events;

import java.io.File;
import java.io.IOException;
import java.util.List;

import org.bukkit.ChatColor;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.metadata.FixedMetadataValue;

import fr.gwenzy.loupsgarous.main.Main;

public class InvitationEvents implements Listener {
	
	private Main plugin;
	public InvitationEvents(Main plugin)
	{
		this.plugin = plugin;
	}
	@EventHandler
	public void onInteractInMenu(InventoryClickEvent event) throws IOException
	{
		try
		{
		if(event.getInventory().getName().contains("vous invite"))
		{
			String inviter = event.getInventory().getName().replace(" vous invite", "");
			File partyFile = null;
			boolean ok = false;
			for(File f : new File("plugins/LoupsGarous/games/").listFiles())
			{
				if(f.getName().split("!!")[1].replace(".ini", "").equalsIgnoreCase(inviter))
				{

					partyFile = f;
					ok = true;
					break;
				}
			}
			if(ok)
			{
				if(event.getCurrentItem()!=null)
				{
					FileConfiguration config = YamlConfiguration.loadConfiguration(partyFile);
					if(event.getCurrentItem().getItemMeta().getDisplayName().equalsIgnoreCase(ChatColor.RED+""+ChatColor.BOLD+"Refuser l'invitation"))
					{

						Player p = Main.s.getPlayer(inviter);
						p.sendMessage(ChatColor.AQUA+event.getWhoClicked().getName()+ChatColor.GOLD+" a refusé votre invitation.");
						event.getWhoClicked().setMetadata("invited", new FixedMetadataValue(plugin, false));
						event.setCancelled(true);
						event.getWhoClicked().closeInventory();
					}
					else if(event.getCurrentItem().getItemMeta().getDisplayName().equalsIgnoreCase(ChatColor.GREEN+""+ChatColor.BOLD+"Accepter l'invitation"))
					{
						Player p = Main.s.getPlayer(inviter);
						p.sendMessage(ChatColor.AQUA+event.getWhoClicked().getName()+ChatColor.GOLD+" a accepté votre invitation.");
						event.getWhoClicked().setMetadata("invited", new FixedMetadataValue(plugin, false));
						event.setCancelled(true);
						event.getWhoClicked().closeInventory();
						List<String> players = config.getStringList("game.players");
						players.add(event.getWhoClicked().getName());
						config.set("game.players", players);
						config.save(partyFile);
					}
				}
			}
		}
	}catch(Exception e){}
	}
}

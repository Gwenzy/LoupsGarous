package fr.gwenzy.loupsgarous.events;

import java.io.File;

import org.bukkit.ChatColor;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import fr.gwenzy.loupsgarous.methods.AdminMethods;

public class ManagePartyEvents implements Listener
{
	@EventHandler
	public void onInteractInMenu(InventoryClickEvent event)
	{
		boolean ok = false;
		if(event.getCurrentItem()!=null)
		{
			if(event.getInventory().getName().contains("Administration"))
			{

				try
				{
					String partyName = event.getInventory().getName().replace("Administration de ", "").replace(".", "");
					File partyFile = null;

					for(File f : new File("plugins/LoupsGarous/games/").listFiles())
					{

						if(f.getName().split("!!")[0].equalsIgnoreCase(partyName))
						{

							partyFile = f;
							ok = true;
							break;
						}
					}
					FileConfiguration config = YamlConfiguration.loadConfiguration(partyFile);

					if(ok)
					{
						event.setCancelled(true);
						ItemStack choice = event.getCurrentItem();
						ItemMeta choiceMeta = choice.getItemMeta();
						String itemName = choiceMeta.getDisplayName();
						//On va gérer les différents choix de l'utilisateur
						if(itemName.equalsIgnoreCase(ChatColor.GREEN+"Démarrer la partie"))
						{
							//On verra plus tard hein ?
							//TODO

						}
						else if(itemName.equalsIgnoreCase(ChatColor.GREEN+"Type de partie"))
						{

							config.set("game.privateGame", !config.getBoolean("game.privateGame"));
							config.save(partyFile);
							event.getWhoClicked().closeInventory();
							event.getWhoClicked().openInventory(AdminMethods.getAdminInventory(partyName, event.getWhoClicked().getName()));
							
						}
						else if(itemName.equalsIgnoreCase(ChatColor.GREEN+"Narration de la partie automatique"))
						{

							config.set("game.directedByMaster", !config.getBoolean("game.directedByMaster"));
							config.save(partyFile);
							event.getWhoClicked().closeInventory();
							event.getWhoClicked().openInventory(AdminMethods.getAdminInventory(partyName, event.getWhoClicked().getName()));
							
						}
						else if(itemName.equalsIgnoreCase(ChatColor.GREEN+"Définition des rôles automatiques"))
						{

							config.set("game.rolesAuto", !config.getBoolean("game.rolesAuto"));
							config.save(partyFile);
							event.getWhoClicked().closeInventory();
							event.getWhoClicked().openInventory(AdminMethods.getAdminInventory(partyName, event.getWhoClicked().getName()));
							
						}
						
						
					}
				}catch(Exception e){e.printStackTrace();}
			}
		}
	}
}

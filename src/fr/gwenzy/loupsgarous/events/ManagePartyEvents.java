package fr.gwenzy.loupsgarous.events;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.inventory.meta.PotionMeta;

import fr.gwenzy.loupsgarous.main.Main;
import fr.gwenzy.loupsgarous.methods.AdminMethods;

public class ManagePartyEvents implements Listener
{
	@SuppressWarnings("unused")
	@EventHandler
	public void onInteractInMenu(InventoryClickEvent event) throws IOException
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
							if(event.getWhoClicked().hasPermission("LoupsGarous.private")||event.getWhoClicked().hasPermission("LoupsGarous.*"))
							{
								config.set("game.privateGame", !config.getBoolean("game.privateGame"));
								config.save(partyFile);
								event.getWhoClicked().closeInventory();
								event.getWhoClicked().openInventory(AdminMethods.getAdminInventory(partyName, event.getWhoClicked().getName()));
							}
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
						else if(itemName.equalsIgnoreCase(ChatColor.GREEN+"Modifier les rôles"))
						{
							
							Inventory i = Main.s.createInventory(null, 9, "Rôles : "+partyName);
							
							List<String> list = config.getStringList("game.roles");
							
							ItemStack is = new ItemStack(Material.ITEM_FRAME);
							ItemMeta isMeta = is.getItemMeta();
							
							int cup=0;
							ArrayList<String> cupP = new ArrayList<>();
							int voy=0;
							ArrayList<String> voyP = new ArrayList<>();
							int lg=0;
							ArrayList<String> lgP = new ArrayList<>();
							int vil=0;
							ArrayList<String> vilP = new ArrayList<>();
							int sor=0;
							ArrayList<String> sorP = new ArrayList<>();
							
							for(int in=0; in<list.size(); in++)
							{try{
								if(list.get(in).split("!")[0].equalsIgnoreCase("Cupidon"))
								{
									cup++;
									cupP.add(list.get(in).split("!")[1]);
								}
								else if(list.get(in).split("!")[0].equalsIgnoreCase("Voyante"))
								{
									voy++;
									voyP.add(list.get(in).split("!")[1]);
								}
								else if(list.get(in).split("!")[0].equalsIgnoreCase("Loup Garou"))
								{
									lg++;
									lgP.add(list.get(in).split("!")[1]);
								}
								else if(list.get(in).split("!")[0].equalsIgnoreCase("Villageois"))
								{
									vil++;
									vilP.add(list.get(in).split("!")[1]);
								}
								else if(list.get(in).split("!")[0].equalsIgnoreCase("Sorcière"))
								{
									sor++;
									sorP.add(list.get(in).split("!")[1]);
								}
							}catch(Exception e){}
							}
							ArrayList<String> lore = new ArrayList<>();

														
							is = new ItemStack(Material.ARROW);
							isMeta = is.getItemMeta();
							lore.clear();

							if(cup!=0)
							{
								isMeta.setDisplayName(ChatColor.RED+"Cupidon");
								lore.add("Cupidons disponibles : 0");
								lore.add("");
								lore.add("Joueurs ayant ce rôle");
								lore.addAll(cupP);
								isMeta.setLore(lore);
							}
							else
							{
								
								isMeta.setDisplayName(ChatColor.AQUA+"Cupidon");
								lore.add("Cupidons disponibles : 1");
								isMeta.setLore(lore);
							}
							is.setItemMeta(isMeta);
							i.setItem(3, is);
							lore.clear();

							is = new ItemStack(Material.ENDER_PEARL);
							isMeta = is.getItemMeta();
							if(voy!=0)
							{
								isMeta.setDisplayName(ChatColor.RED+"Voyante");
								lore.add("Voyantes disponibles : 0");
								lore.add("");
								lore.add("Joueurs ayant ce rôle");
								lore.addAll(voyP);
								isMeta.setLore(lore);
							}
							else
							{
								
								isMeta.setDisplayName(ChatColor.AQUA+"Voyante");
								lore.add("Voyantes disponibles : 1");
								isMeta.setLore(lore);
							}
							is.setItemMeta(isMeta);
							i.setItem(2, is);
							lore.clear();

							is = new ItemStack(Material.COOKED_BEEF);
							isMeta = is.getItemMeta();
							if(lg==0)
							{
								isMeta.setDisplayName(ChatColor.RED+"Loup Garou");
								lore.add("Loups Garous restants min : 2");
								isMeta.setLore(lore);
							}
							else if(lg==1)
							{
								isMeta.setDisplayName(ChatColor.RED+"Loup Garou");
								lore.add("Loups Garous restants min : 1");
								lore.add("");
								lore.add("Joueurs ayant ce rôle");
								lore.addAll(lgP);
								isMeta.setLore(lore);
							}
							else
							{
								
								isMeta.setDisplayName(ChatColor.AQUA+"Loup Garou");
								lore.add("Loups Garous restants min : 0");
								lore.add("");
								lore.add("Joueurs ayant ce rôle");
								lore.addAll(lgP);
								isMeta.setLore(lore);
							}
							is.setItemMeta(isMeta);
							i.setItem(4, is);
							lore.clear();

							is = new ItemStack(Material.WHEAT);
							isMeta = is.getItemMeta();
							if(vil!=0)
							{
								isMeta.setDisplayName(ChatColor.RED+"Villageois");
								lore.add("Villageois disponibles : 0");
								lore.add("");
								lore.add("Joueurs ayant ce rôle");
								lore.addAll(vilP);
								isMeta.setLore(lore);
							}
							else
							{
								
								isMeta.setDisplayName(ChatColor.AQUA+"Villageois");
								lore.add("Villageois disponibles : 1");
								isMeta.setLore(lore);
							}
							is.setItemMeta(isMeta);
							i.setItem(5, is);
							lore.clear();

							is = new ItemStack(Material.POTION, 1, (byte) 8257);
							PotionMeta pm = (PotionMeta) is.getItemMeta();
							if(sor!=0)
							{
								pm.setDisplayName(ChatColor.RED+"Sorcière");
								lore.add("Sorcières disponibles : 0");
								lore.add("");
								lore.add("Joueurs ayant ce rôle");
								lore.addAll(sorP);
								pm.setLore(lore);
							}
							else
							{
								
								pm.setDisplayName(ChatColor.AQUA+"Sorcière");
								lore.add("Sorcières disponibles : 1");
								pm.setLore(lore);
							}
							pm.clearCustomEffects();
							is.setItemMeta(pm);
							i.setItem(6, is);
							
							event.getWhoClicked().closeInventory();
							event.getWhoClicked().openInventory(i);
							
						}
						else if(itemName.equalsIgnoreCase(ChatColor.GREEN+"Inviter des joueurs dans la partie"))
						{
							List<String> players = config.getStringList("game.players");
							int nbP=0;
							for(Player p : Main.s.getOnlinePlayers())
							{
								nbP++;
							}
							int slots = 0;
							if(slots%9!=0)
							{
								slots = nbP;
							}
							else
							{
								slots = ((nbP/9)+1)*9;
							}
							Inventory i = Main.s.createInventory(null, slots, "Inviter un joueur "+partyName);
							for(Player p : Main.s.getOnlinePlayers())
							{
								if(!players.contains(p.getName()))
								{
									ItemStack is = new ItemStack(Material.SKULL_ITEM, 1, (byte) 3);
									ItemMeta isMeta = is.getItemMeta();
									isMeta.setDisplayName(ChatColor.GREEN+p.getName());
									is.setItemMeta(isMeta);
									i.addItem(is);
								}
							}
							event.getWhoClicked().closeInventory();
							event.getWhoClicked().openInventory(i);
							
						}
						
						
					}
				}catch(Exception e){e.printStackTrace();}
			}
			else if(event.getInventory().getName().contains("Inviter un joueur"))
			{
				try
				{
					String partyName = event.getInventory().getName().replace("Inviter un joueur ", "");
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
						String pseudo = ChatColor.stripColor(event.getCurrentItem().getItemMeta().getDisplayName());
						List<String> players = config.getStringList("game.players");
						players.add(pseudo); 
						config.set("game.players", players);
						config.save(partyFile);
						event.setCancelled(true);
						event.getWhoClicked().closeInventory();
						event.getWhoClicked().openInventory(AdminMethods.getAdminInventory(partyName, event.getWhoClicked().getName()));
					}
				}
				catch(Exception e){}
			}
			else if(event.getInventory().getName().contains("Rôles : "))
			{
				String partyName = event.getInventory().getName().replace("Rôles : ", "");
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
					if(event.getCurrentItem()!=null)
					{
						int nbP=0;
						List<String> roles = config.getStringList("game.roles");
						ArrayList<String> playersNo = new ArrayList<>();
						ArrayList<String> playersYes = new ArrayList<>();
						for(int i=0; i<roles.size(); i++)
						{
							playersNo.add(roles.get(i).split("!")[1]);
						}
						for(Player p : Main.s.getOnlinePlayers())
						{
							if(!playersNo.contains(p.getName()))
							{
								nbP++;
								playersYes.add(p.getName());
							}
						}
						int slots = 0;
						if(slots%9!=0)
						{
							slots = nbP;
						}
						else
						{
							slots = ((nbP/9)+1)*9;
						}
						try
						{
						if(event.getCurrentItem().getItemMeta().getDisplayName().contains(ChatColor.RED+""))
						{
							if(event.getCurrentItem().getItemMeta().getDisplayName().equalsIgnoreCase(ChatColor.RED+"Loup Garou"))
							{
								Inventory i = Main.s.createInventory(null, slots, "Rôles de "+partyName+" : "+ChatColor.stripColor(event.getCurrentItem().getItemMeta().getDisplayName()));
								for(int in = 0; in<playersYes.size(); in++)
								{
									ItemStack is = new ItemStack(Material.SKULL_ITEM, 1, (byte) 3);
									ItemMeta isMeta = is.getItemMeta();
									isMeta.setDisplayName(ChatColor.GREEN+playersYes.get(in));
									is.setItemMeta(isMeta);
									i.addItem(is);
								}
								event.getWhoClicked().openInventory(i);
							}
						}
						else
						{
							Inventory i = Main.s.createInventory(null, slots, "Rôles de "+partyName+" : "+ChatColor.stripColor(event.getCurrentItem().getItemMeta().getDisplayName()));
							for(int in = 0; in<playersYes.size(); in++)
							{
								ItemStack is = new ItemStack(Material.SKULL_ITEM, 1, (byte) 3);
								ItemMeta isMeta = is.getItemMeta();
								isMeta.setDisplayName(ChatColor.GREEN+playersYes.get(in));
								is.setItemMeta(isMeta);
								i.addItem(is);
							}
							event.getWhoClicked().openInventory(i);
						}
						event.setCancelled(true);
						}catch(Exception e){};
					}
				}
			}
			else if(event.getInventory().getName().contains("Rôles de "))
			{
				String partyName = event.getInventory().getName().split(" : ")[0].replace("Rôles de ", "");
				String role = event.getInventory().getName().split(" : ")[1];
				
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
					try
					{
						event.setCancelled(true);
						List<String> roles = config.getStringList("game.roles");
						roles.add(role+"!"+ChatColor.stripColor(event.getCurrentItem().getItemMeta().getDisplayName()));
					
						config.set("game.roles", roles);
						config.save(partyFile);
						event.getWhoClicked().closeInventory();
						event.getWhoClicked().openInventory(AdminMethods.getAdminInventory(partyName, event.getWhoClicked().getName()));
					}
					catch(Exception e){}
				}
			}
		}
	}
}

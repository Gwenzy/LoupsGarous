package fr.gwenzy.loupsgarous.commands;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;

import fr.gwenzy.loupsgarous.main.Main;
import fr.gwenzy.loupsgarous.methods.AdminMethods;

public class lgCommand implements CommandExecutor {

	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String CommandLabel,
			String[] args) {
		if(sender instanceof Player)
		{
		boolean allPerms = sender.hasPermission("LoupsGarous.*");
		Player p = (Player) sender;
		
		if(CommandLabel.equalsIgnoreCase("lg"))
		{
			if(args.length > 0)
			{
				if(args[0].equalsIgnoreCase("create"))
				{
					if(allPerms||sender.hasPermission("LoupsGarous.create"))
					{
						if(args.length>1)
						{
							boolean othergameName = false;
							boolean othergamePlayer = false;
							String name = args[1];
							File gamesFolder = new File("plugins/LoupsGarous/games/");
							File gameFileConfig = new File("plugins/LoupsGarous/games/"+name+"!!"+p.getName()+".ini");
							//On vérifie si une partie existe déjà
							if(gamesFolder.listFiles()!=null)
							{
							for(File f : gamesFolder.listFiles())
							{
								String fileName = f.getName();
								String gameName = fileName.split("!!")[0];
								String pseudoName = fileName.split("!!")[1].replaceAll(".ini", "");
								if(name.equals(gameName))
								{
									othergameName=true;
								}
								if(p.getName().equalsIgnoreCase(pseudoName))
								{
									othergamePlayer=true;
								}
							}
						}
							
							
							if(!othergameName)
							{
								if(!othergamePlayer)
								{
									if(!(args[1].length()>13))
									{
										try {
											gameFileConfig.createNewFile();
										} catch (IOException e) {
											// TODO Auto-generated catch block
											e.printStackTrace();
										}
										FileConfiguration fileConfig = YamlConfiguration.loadConfiguration(gameFileConfig);
										fileConfig.set("game.master", p.getName());
										fileConfig.set("game.privateGame", false);
										fileConfig.set("game.directedByMaster", false);										
										fileConfig.set("game.rolesAuto", false);										
										fileConfig.set("game.times.action", 30);										
										fileConfig.set("game.times.lg", 90);										
										fileConfig.set("game.times.vil", 600);										
										fileConfig.set("game.allConfigured", true);
										fileConfig.set("game.players", new ArrayList<String>());
										
										try {
											fileConfig.save(gameFileConfig);
										} catch (IOException e) {
											// TODO Auto-generated catch block
											e.printStackTrace();
										}
										p.sendMessage(ChatColor.GREEN+Main.pn+"La partie est créée, vous pouvez la configurer avec /lg configure");
									
									}
									else
									{
										p.sendMessage(ChatColor.RED+Main.pn+"Le nom de la partie doit avoir un nom avec un maximum de 13 caractères.");
									}
								}
								else
								{
									p.sendMessage(ChatColor.RED+Main.pn+"Vous avez déjà créé une partie, vous pouvez l'administrer avec la commande /lg configure");
									
								}
							}
							else
							{
								p.sendMessage(ChatColor.RED+Main.pn+"Une partie ayant ce nom existe déjà, merci d'en choisir un autre.");
							}
						}
						else
						{
							p.sendMessage(ChatColor.RED+Main.pn+"Merci de renseigner le nom de la partie.");
						}
					}
					else
					{
						p.sendMessage(ChatColor.RED+Main.pn+"Il vous est impossible de créer une partie. Raison : Permissions insuffisantes");
					}
				}
				else if(args[0].equalsIgnoreCase("configure"))
				{
					File gamesFolder = new File("plugins/LoupsGarous/games/");
					//On vérifie si des parties existent
					String gameName = null;
					String playerName = null;
					if(gamesFolder.listFiles()!=null)
					{
						
						boolean game = false;
						//Une boucle pour récupérer la partie du joueur concerné
						for(File f : gamesFolder.listFiles())
						{
							String nameFile = f.getName();
							String name = nameFile.split("!!")[0];
							String pseudo = nameFile.split("!!")[1].replaceAll(".ini", "");							
							if(pseudo.equalsIgnoreCase(p.getName()))
							{
								gameName = name;
								playerName  = pseudo;
								game = true;
							}
						}
						if(game)
						{
							Inventory i = AdminMethods.getAdminInventory(gameName, playerName);
							p.openInventory(i);
						}
						else
						{
							p.sendMessage(ChatColor.RED+Main.pn+"Vous n'avez pas créé de partie, vous devez d'abord en créer avec /lg create <Nom> si vous voulez l'administrer.");
						}
						
					}
					else
					{
						p.sendMessage(ChatColor.RED+Main.pn+"Vous n'avez pas créé de partie, vous devez d'abord en créer avec /lg create <Nom> si vous voulez l'administrer.");
						
					}
				}
				//Prochain argument ici
			}
			else
			{
				sender.sendMessage(ChatColor.RED+Main.pn+"Merci de respecter la syntaxe suivante : /lg <Argument>");
				
			}
		}
		}
		return true;
	}

}

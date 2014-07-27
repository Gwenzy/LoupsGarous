package fr.gwenzy.loupsgarous.methods;

import java.io.File;
import java.util.ArrayList;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import fr.gwenzy.loupsgarous.main.Main;

public class AdminMethods {
	public static Inventory getAdminInventory(String gameName, String playerName)
	{
		File gameConfigFile = new File("plugins/LoupsGarous/games/"+gameName+"!!"+playerName+".ini");
		FileConfiguration config = YamlConfiguration.loadConfiguration(gameConfigFile);
		
		
		Inventory i = Main.s.createInventory(null, 36, "Administration de "+gameName+".");
		
		//On déclare les items de choix
		ItemStack startGame = new ItemStack(Material.EMERALD);
		ItemStack togglePrivateOn = new ItemStack(Material.ENDER_PEARL);
		ItemStack togglePrivateOff = new ItemStack(Material.EYE_OF_ENDER);
		ItemStack directedAutoOn = new ItemStack(Material.REDSTONE_BLOCK);
		ItemStack directedAutoOff = new ItemStack(Material.REDSTONE);
		ItemStack rolesAutoOff = new ItemStack(Material.REDSTONE_LAMP_OFF);
		ItemStack rolesAutoOn = new ItemStack(Material.REDSTONE_TORCH_ON);
		ItemStack timeManage = new ItemStack(Material.WATCH);
		ItemStack deleteGame = new ItemStack(Material.FIRE);
		
		ItemMeta startGameMeta = startGame.getItemMeta();
		ItemMeta toggleOnMeta = togglePrivateOn.getItemMeta();
		ItemMeta toggleOffMeta = togglePrivateOff.getItemMeta();
		ItemMeta directedAutoMetaOn = directedAutoOn.getItemMeta();
		ItemMeta directedAutoMetaOff = directedAutoOff.getItemMeta();
		ItemMeta rolesAutoMetaOn = rolesAutoOn.getItemMeta();
		ItemMeta rolesAutoMetaOff = rolesAutoOff.getItemMeta();
		ItemMeta timeManageMeta = timeManage.getItemMeta();
		ItemMeta deleteGameMeta = deleteGame.getItemMeta();
		
		ArrayList<String> list = new ArrayList<>();
		list.add("État : "+ChatColor.GREEN+"Privée");
		ArrayList<String> liste = new ArrayList<>();
		liste.add("État : "+ChatColor.GREEN+"Publique");
		ArrayList<String> liste1 = new ArrayList<>();
		liste1.add("État : "+ChatColor.GREEN+"Actif");
		ArrayList<String> liste2 = new ArrayList<>();
		liste2.add("État : "+ChatColor.RED+"Inactif");
		startGameMeta.setDisplayName(ChatColor.GREEN+"Démarrer la partie");
		toggleOnMeta.setDisplayName(ChatColor.GREEN+"Type de partie");
		
		toggleOnMeta.setLore(list);
		toggleOffMeta.setDisplayName(ChatColor.GREEN+"Type de partie");
		
		toggleOffMeta.setLore(liste);
		directedAutoMetaOn.setDisplayName(ChatColor.GREEN+"Narration de la partie automatique");
		directedAutoMetaOn.setLore(liste1);
		directedAutoMetaOff.setLore(liste2);
		directedAutoMetaOff.setDisplayName(ChatColor.GREEN+"Narration de la partie automatique");
		rolesAutoMetaOn.setLore(liste1);
		rolesAutoMetaOff.setLore(liste2);
		rolesAutoMetaOn.setDisplayName(ChatColor.GREEN+"Définition des rôles automatiques");
		rolesAutoMetaOff.setDisplayName(ChatColor.GREEN+"Définition des rôles automatiques");
		timeManageMeta.setDisplayName(ChatColor.GREEN+"Gérer les temps de parole/d'action");
		ArrayList<String> actionTimeLore = new ArrayList<>();
		actionTimeLore.add("Temps d'action pour les personnages secondaires : "+config.getInt("game.times.action")+"s");
		actionTimeLore.add("Temps de chat et de vote des Loups Garous : "+config.getInt("game.times.lg")+"s");
		timeManageMeta.setLore(actionTimeLore);
		deleteGameMeta.setDisplayName(ChatColor.RED+"Supprimer la partie");
		
		startGame.setItemMeta(startGameMeta);
		togglePrivateOn.setItemMeta(toggleOnMeta);
		togglePrivateOff.setItemMeta(toggleOffMeta);
		directedAutoOn.setItemMeta(directedAutoMetaOn);
		directedAutoOff.setItemMeta(directedAutoMetaOff);
		rolesAutoOff.setItemMeta(rolesAutoMetaOff);
		rolesAutoOn.setItemMeta(rolesAutoMetaOn);
		timeManage.setItemMeta(timeManageMeta);
		deleteGame.setItemMeta(deleteGameMeta);
		
		
		
		
		
		
		i.setItem(4, startGame);
		if(config.getBoolean("game.privateGame"))
		{
			i.setItem(20, togglePrivateOn);
		}
		else
		{
			i.setItem(20, togglePrivateOff);
		}
		if(!config.getBoolean("game.directedByMaster"))
		{
			i.setItem(21, directedAutoOff);
		}
		else
		{
			i.setItem(21, directedAutoOn);
		}
		if(config.getBoolean("game.rolesAuto"))
		{
			i.setItem(22, rolesAutoOn);
		}
		else
		{
			i.setItem(22, rolesAutoOff);
		}
		i.setItem(23, timeManage);
		i.setItem(24, deleteGame);
		
		
		
		
		
		return i;
	}
}

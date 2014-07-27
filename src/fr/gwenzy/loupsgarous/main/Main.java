package fr.gwenzy.loupsgarous.main;

import java.io.File;
import java.util.logging.Logger;

import org.bukkit.Bukkit;
import org.bukkit.Server;
import org.bukkit.plugin.java.JavaPlugin;

import fr.gwenzy.loupsgarous.commands.lgCommand;
import fr.gwenzy.loupsgarous.events.ManagePartyEvents;

public class Main extends JavaPlugin
{
	public static Server s = Bukkit.getServer();
	public static Logger log = s.getLogger();
	public static String pn = "[Loups-Garous] ";
	@Override
	public void onEnable()
	{
		log.info(pn+"Les loups garous de Thiercelieux, développé par Gwenzy est en cours d'activation...");
		new File("plugins/LoupsGarous/games/").mkdirs();
		
		getCommand("lg").setExecutor(new lgCommand());
		
		Main.s.getPluginManager().registerEvents(new ManagePartyEvents(), this);
		
		
		log.info(pn+"Plugin activé");
		
	}
	
	@Override
	public void onDisable()
	{
		log.info(pn+"Plugin désactivé");
	}
}

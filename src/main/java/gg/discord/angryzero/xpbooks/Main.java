package gg.discord.angryzero.xpbooks;

import gg.discord.angryzero.xpbooks.events.onXPBookCrafting;
import gg.discord.angryzero.xpbooks.events.onXPBookUsage;
import gg.discord.angryzero.xpbooks.recipe.BookRecipe;
import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;

public final class Main extends JavaPlugin {
    public static Main Instance;


    @Override
    public void onEnable() {
        // Plugin startup logic
        saveDefaultConfig();
        Instance = this;
        BookRecipe bookRecipe = new BookRecipe();

        Bukkit.getServer().getPluginManager().registerEvents(new onXPBookCrafting(), this);
        Bukkit.getServer().getPluginManager().registerEvents(new onXPBookUsage(), this);
    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
    }
}

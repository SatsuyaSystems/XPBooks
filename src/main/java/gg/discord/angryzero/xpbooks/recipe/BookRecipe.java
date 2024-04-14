package gg.discord.angryzero.xpbooks.recipe;

import gg.discord.angryzero.xpbooks.Main;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.ShapedRecipe;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.persistence.PersistentDataType;

public class BookRecipe {
    public static ShapedRecipe recipe;
    public static final NamespacedKey key = new NamespacedKey(Main.Instance, "xpbook");
    public static final NamespacedKey xpKey = new NamespacedKey(Main.Instance, "xpbook-xp");

    public BookRecipe() {
        registerRecipes();
    }

    public void registerRecipes() {

        ItemStack book = new ItemStack(Material.BOOK);
        ItemMeta meta = book.getItemMeta();
        if( meta == null ){ return; }

        meta.setDisplayName("§5XP Book");
        meta.getPersistentDataContainer().set(xpKey, PersistentDataType.INTEGER, 0);
        meta.addEnchant(Enchantment.BINDING_CURSE, 1, true);
        meta.addItemFlags(ItemFlag.HIDE_ENCHANTS, ItemFlag.HIDE_ATTRIBUTES);

        book.setItemMeta(meta);

        recipe = new ShapedRecipe(key, book);
        recipe.shape("AEA","GBG","AEA");

        recipe.setIngredient('A', Material.AMETHYST_SHARD);
        recipe.setIngredient('E', Material.EMERALD);
        recipe.setIngredient('G', Material.GOLD_INGOT);
        recipe.setIngredient('B', Material.BOOK);

        Bukkit.getServer().addRecipe(recipe);
    }
}

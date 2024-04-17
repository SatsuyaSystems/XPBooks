package gg.discord.angryzero.xpbooks.events;

import gg.discord.angryzero.xpbooks.recipe.BookRecipe;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.persistence.PersistentDataContainer;
import org.bukkit.persistence.PersistentDataType;

import java.util.ArrayList;
import java.util.List;

public class onXPBookUsage implements Listener {
    private final double XP_STORED_PERCENTAGE = 0.9;


    @EventHandler
    public void onXPBookUse(PlayerInteractEvent event) {
        Player player = event.getPlayer();
        ItemStack item = event.getItem();

        if (item == null || item.getType() != Material.BOOK || !item.hasItemMeta()) return;

        ItemMeta meta = item.getItemMeta();
        if (meta == null || !meta.hasDisplayName() || !meta.getDisplayName().equals("§5XP Book")) return;

        PersistentDataContainer data = meta.getPersistentDataContainer();
        int xpBookXP = data.getOrDefault(BookRecipe.xpKey, PersistentDataType.INTEGER, 0);

        if (player.isSneaking()) {
            // Withdraw XP from the book and give it to the player
            if (xpBookXP > 0) {
                int xpToWithdraw = Math.min(xpBookXP, getTotalExperience(player.getLevel() + 1) - getTotalExperience(player.getLevel()));
                player.giveExp((int) (xpToWithdraw * XP_STORED_PERCENTAGE));
                xpBookXP -= xpToWithdraw;
                updateBookMeta(meta, item, xpBookXP);
            }
        } else {
            if(player.getLevel() == 0) return;
            if(player.getExp() == 0) return;

            // Store XP in the book
            int xpToStore = getTotalExperience(player.getLevel()) - getTotalExperience(player.getLevel() - 1);
            xpToStore = (int) (xpToStore * XP_STORED_PERCENTAGE);

            player.giveExp(-xpToStore);
            xpBookXP += xpToStore;
            updateBookMeta(meta, item, xpBookXP);
        }
    }

    private void updateBookMeta(ItemMeta meta, ItemStack item, int xpBookXP) {
        meta.getPersistentDataContainer().set(BookRecipe.xpKey, PersistentDataType.INTEGER, xpBookXP);
        List<String> lore = meta.getLore() == null ? new ArrayList<String>() : meta.getLore();
        if (lore.isEmpty())
            lore.add("§7Stored XP: §6" + xpBookXP);
        else
            lore.set(0, "§7Stored XP: §6" + xpBookXP);

        meta.setLore(lore);
        item.setItemMeta(meta);
    }

    // This method calculates the total experience required to reach a given level
    public int getTotalExperience(int level) {
        if (level <= 16) {
            return (int) (Math.pow(level, 2) + 6 * level);
        } else if (level <= 31) {
            return (int) (2.5 * Math.pow(level, 2) - 40.5 * level + 360);
        } else {
            return (int) (4.5 * Math.pow(level, 2) - 162.5 * level + 2220);
        }
    }
}


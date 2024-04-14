package gg.discord.angryzero.xpbooks.events;

import gg.discord.angryzero.xpbooks.recipe.BookRecipe;
import org.bukkit.Material;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.CraftItemEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.Random;

public class onXPBookCrafting implements Listener {

    @EventHandler
    public void onCraftItem(CraftItemEvent event) {
        ItemStack item = event.getCurrentItem();
        if (item != null && item.getType() == Material.BOOK && item.isSimilar(BookRecipe.recipe.getResult())) {

            ItemMeta meta = item.getItemMeta();
            if (meta == null) {
                event.setResult(CraftItemEvent.Result.DENY);
                event.setCancelled(true);
                return;
            }

            // Incorporating player-specific data and a random component
            long uniquePart = event.getWhoClicked().getUniqueId().getLeastSignificantBits() & 0xFFFF; // Use some bits from UUID
            int randomPart = new Random().nextInt(1001);
            long currentTime = System.currentTimeMillis();

            meta.setCustomModelData( (int) ((currentTime + uniquePart + randomPart) % Integer.MAX_VALUE) );

            item.setItemMeta(meta);
        }
    }
}

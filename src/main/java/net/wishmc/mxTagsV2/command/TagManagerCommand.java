package net.wishmc.mxTagsV2.command;

import net.wishmc.mxTagsV2.MxTagsV2;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabExecutor;
import org.bukkit.entity.Player;

import java.util.List;
import java.util.stream.IntStream;

import static net.wishmc.mxTagsV2.MxTagsV2.formatColors;

public class TagManagerCommand implements TabExecutor {

    private MxTagsV2 mxTags = MxTagsV2.getInstance();

    public int chatPageAmount = (int) Math.ceil((double) mxTags.tagManager().getTagAmount() / mxTags.TAGS_PER_PAGE_CHAT);

    private String invalidUsage = formatColors("&cInvalid Usage! Use &6/tagmanager &7<&6list &7| &6unselect &7| &6reload&7>");

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (args.length < 1) {
            sender.sendMessage(invalidUsage);
        }
        switch (args[0]) {
            case "list":
                if (args.length == 1) listTags(sender, 1);
                else listTags(sender, Integer.parseInt(args[1]));
                break;
            case "unselect":
                Player player = Bukkit.getPlayer(args[1]);
                if (player == null) {
                    sender.sendMessage(formatColors("&cPlayer not found."));
                    return true;
                }
                mxTags.tagManager().removeTag(player);
                sender.sendMessage(formatColors("&aRemoved tag of player " + player.getName()));
                break;
            case "reload":
                mxTags.reload();
                sender.sendMessage(formatColors("&aReloaded Tags"));
                break;
            default:
                sender.sendMessage(invalidUsage);
                break;
        }
        return true;
    }

    @Override
    public List<String> onTabComplete(CommandSender commandSender, Command command, String label, String[] args) {
        if (args.length == 1) return List.of("list", "unselect", "reload");

        if (args[0].equals("list")) return IntStream.rangeClosed(1, chatPageAmount).mapToObj(String::valueOf).toList();
        if (args[0].equals("unselect")) return Bukkit.getOnlinePlayers().stream().map(Player::getName).toList();

        return List.of();
    }

    private void listTags(CommandSender viewer, int page) {
        if (page > chatPageAmount || page < 1) {
            viewer.sendMessage(formatColors("&cThis page does not exist."));
            return;
        }
        viewer.sendMessage(formatColors("&e> &6&lMxTags List &r&e(&6Page " + page + "&e/&6" + chatPageAmount + "&e)"));
        mxTags.tagManager().getChatTagList(page).forEach(tag -> viewer.sendMessage("- " + tag));
    }
}
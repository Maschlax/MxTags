package net.wishmc.mxTagsV2.manager;

import net.wishmc.mxTagsV2.MxTagsV2;
import org.bukkit.ChatColor;
import org.bukkit.NamespacedKey;
import org.bukkit.entity.Player;
import org.bukkit.persistence.PersistentDataContainer;
import org.bukkit.persistence.PersistentDataType;

import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;

public class TagManager {

    private MxTagsV2 mxTags = MxTagsV2.getInstance();

    private List<Tag> everyTags = new LinkedList<>();
    private NamespacedKey selectedTag = new NamespacedKey(mxTags, "selectedtag");

    public record Tag(String tagText) {}

    public void registerTags() {
        mxTags.getConfig().getStringList("tags").forEach(this::addTag);
    }

    public void clearTags() {
        everyTags.clear();
    }

    public void addTag(String tag) {
        everyTags.add(new Tag(ChatColor.translateAlternateColorCodes('&', tag)));
    }

    public boolean tagNotExists(String tagText) {
        return everyTags.stream().noneMatch(tag -> tag.tagText().equals(tagText));
    }

    public void selectTag(Player player, int index) {
        Tag tag = everyTags.get(index);

        PersistentDataContainer persistentData = player.getPersistentDataContainer();
        persistentData.set(selectedTag, PersistentDataType.STRING, tag.tagText());

        setTag(player);
        player.sendActionBar("Selected tag: " + tag.tagText());
    }

    public void setTag(Player player) {
        String name = player.getName();
        String tagText = getPlayerTag(player);
        if (!tagText.isEmpty()) name += " " + tagText;
        player.setDisplayName(name);
        player.setPlayerListName(name);
    }

    public String getPlayerTag(Player player) {
        return player.getPersistentDataContainer().get(selectedTag, PersistentDataType.STRING);
    }

    public void removeTag(Player player) {
        player.getPersistentDataContainer().set(selectedTag, PersistentDataType.STRING, "");
        setTag(player);
    }

    public List<String> getGUITagList(int page) {
        int index = (page - 1) * mxTags.TAGS_PER_PAGE_GUI;
        return everyTags.subList(index, Math.min(index + mxTags.TAGS_PER_PAGE_GUI, everyTags.size())).stream().map(Tag::tagText).collect(Collectors.toList());
    }

    public List<String> getChatTagList(int page) {
        int index = (page - 1) * mxTags.TAGS_PER_PAGE_CHAT;
        return everyTags.subList(index, Math.min(index + mxTags.TAGS_PER_PAGE_CHAT, everyTags.size())).stream().map(Tag::tagText).collect(Collectors.toList());
    }

    public int getTagAmount() {
        return everyTags.size();
    }
}
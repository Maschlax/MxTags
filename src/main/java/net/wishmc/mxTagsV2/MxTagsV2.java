package net.wishmc.mxTagsV2;

import net.wishmc.mxTagsV2.command.TagCommand;
import net.wishmc.mxTagsV2.command.TagManagerCommand;
import net.wishmc.mxTagsV2.command.TagRemoveCommand;
import net.wishmc.mxTagsV2.listener.InventoryListener;
import net.wishmc.mxTagsV2.listener.JoinListener;
import net.wishmc.mxTagsV2.manager.TagManager;
import net.wishmc.mxTagsV2.manager.TagMenu;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.Arrays;

public final class MxTagsV2 extends JavaPlugin {

    private TagManager tagManager;
    private TagMenu tagMenu;

    public int TAGS_PER_PAGE_GUI;
    public int TAGS_PER_PAGE_CHAT = 10;

    @Override
    public void onEnable() {
        saveDefaultConfig();
        getConfig().addDefault("tags_per_gui_page", 45);
        getConfig().addDefault("tags", Arrays.asList("&ePlaceholder"));
        getConfig().options().copyDefaults(true);
        saveConfig();

        this.TAGS_PER_PAGE_GUI = getConfig().getInt("tags_per_gui_page");

        this.tagManager = new TagManager();
        tagManager.registerTags();
        this.tagMenu = new TagMenu();

        Bukkit.getPluginManager().registerEvents(new JoinListener(), this);
        Bukkit.getPluginManager().registerEvents(new InventoryListener(), this);

        getCommand("tags").setExecutor(new TagCommand());
        getCommand("tagmanager").setExecutor(new TagManagerCommand());
        getCommand("tagremove").setExecutor(new TagRemoveCommand());

        getLogger().info("Enabled MxTags");
    }

    public void reload() {
        tagManager.clearTags();
        reloadConfig();
        this.TAGS_PER_PAGE_GUI = getConfig().getInt("tags_per_gui_page");
        tagManager.registerTags();
        getLogger().info("Reloaded MxTags plugin");
    }

    public static String formatColors(String text) {
        return ChatColor.translateAlternateColorCodes('&', text);
    }

    public TagManager tagManager() {
        return this.tagManager;
    }

    public TagMenu tagMenu() {
        return this.tagMenu;
    }

    public static MxTagsV2 getInstance() {
        return JavaPlugin.getPlugin(MxTagsV2.class);
    }
}
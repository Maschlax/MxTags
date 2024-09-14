package dev.mxlx.mxTags;

import dev.mxlx.mxTags.commands.TagCommand;
import dev.mxlx.mxTags.commands.TagManageCommand;
import dev.mxlx.mxTags.listeners.JoinLeaveListener;
import dev.mxlx.mxTags.utils.ColorFormatter;
import dev.mxlx.mxTags.utils.DataBase;
import dev.mxlx.mxTags.utils.TagManager;
import me.maschlax.mxapi.MxAPI;
import org.bukkit.plugin.java.JavaPlugin;

import java.sql.SQLException;

public final class MxTags extends JavaPlugin {

    private ColorFormatter colorFormatter;
    private DataBase database;

    @Override
    public void onEnable() {
        registerCommands();
        registerListeners();
        registerUtils();
        registerConfig();
        registerDatabase();
        getLogger().info("MxTags is enabled!");
    }

    @Override
    public void onDisable() {
        this.database.closeConnection();
    }

    private void registerCommands() {
        getCommand("tags").setExecutor(new TagCommand());
        getCommand("tagmanager").setExecutor(new TagManageCommand());
    }

    private void registerListeners() {
        getServer().getPluginManager().registerEvents(new JoinLeaveListener(), this);
    }

    private void registerUtils() {
        this.colorFormatter = new ColorFormatter();
    }

    private void registerConfig() {
        saveDefaultConfig();
        getConfig().addDefault("debugmode", false);
        getConfig().addDefault("database.type", "mysql");
        getConfig().addDefault("database.host", "localhost");
        getConfig().addDefault("database.port", "port");
        getConfig().addDefault("database.user", "user");
        getConfig().addDefault("database.password", "password");
        getConfig().addDefault("database.database_name", "databaseName");
        getConfig().options().copyDefaults(true);
        saveConfig();
    }

    private void registerDatabase() {
        try {
            this.database = new DataBase(
                    getConfig().getString("database.type"),
                    getConfig().getString("database.host"),
                    getConfig().getString("database.port"),
                    getConfig().getString("database.user"),
                    getConfig().getString("database.password"),
                    getConfig().getString("database.database_name"));
            database.initializeDatabase();

        } catch (SQLException exception) {
            getLogger().severe("Could not initialize database!");
            getLogger().severe("Are the settings in the config correct?");
            if (getConfig().getBoolean("debugmode")) {
                exception.printStackTrace();
            }
        }
    }

    public ColorFormatter colorFormatter() {
        return this.colorFormatter;
    }

    public DataBase getDatabase() {
        return this.database;
    }

    public MxAPI mxAPI() {
        return JavaPlugin.getPlugin(MxAPI.class);
    }

    public static MxTags getInstance() {
        return JavaPlugin.getPlugin(MxTags.class);
    }
}

package dev.mxlx.mxTags;

import dev.mxlx.mxTags.commands.TagCommand;
import dev.mxlx.mxTags.commands.TagManageCommand;
import dev.mxlx.mxTags.listeners.JoinListener;
import dev.mxlx.mxTags.utils.ColorFormatter;
import dev.mxlx.mxTags.utils.DataBase;
import dev.mxlx.mxTags.utils.TagManager;
import me.maschlax.mxapi.MxAPI;
import org.bukkit.plugin.java.JavaPlugin;

import java.sql.SQLException;

public final class MxTags extends JavaPlugin {

    private ColorFormatter colorFormatter;
    private DataBase database;
    private TagManager tagManager;

    private boolean debugMode = false;

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
        getServer().getPluginManager().registerEvents(new JoinListener(), this);
    }

    private void registerUtils() {
        this.colorFormatter = new ColorFormatter();
        this.tagManager = new TagManager();
    }

    private void registerConfig() {
        saveDefaultConfig();
        getConfig().options().header("Not sure if any other database works, I think I've used MariaDB syntax");
        getConfig().addDefault("debugmode", false);
        getConfig().addDefault("database.type", "mariadb");
        getConfig().addDefault("database.host", "localhost");
        getConfig().addDefault("database.port", "port");
        getConfig().addDefault("database.user", "user");
        getConfig().addDefault("database.password", "password");
        getConfig().addDefault("database.database_name", "databaseName");
        getConfig().options().copyDefaults(true);
        saveConfig();

        if (getConfig().getBoolean("debugmode")) {
            getLogger().info("Enabled Debug mode.");
            debugMode = true;
        }
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
            if (debugMode) {
                exception.printStackTrace();
            }
        }
    }

    public ColorFormatter colorFormatter() {
        return this.colorFormatter;
    }

    public TagManager tagManager() {
        return this.tagManager;
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

    public boolean debugMode() { return debugMode; }
}

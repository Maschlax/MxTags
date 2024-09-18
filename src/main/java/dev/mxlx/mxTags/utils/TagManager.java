package dev.mxlx.mxTags.utils;

import dev.mxlx.mxTags.MxTags;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class TagManager {

    private MxTags mxTags = MxTags.getInstance();

    public void createTag(String tag, Integer slot) {
        tag = mxTags.colorFormatter().formatHexColors(tag);
        try {
            PreparedStatement statement = mxTags.getDatabase().getConnection().prepareStatement("UPDATE tags SET slot = slot + 1 WHERE slot >= ?");
            statement.setInt(1, slot);
            statement.executeUpdate();

            statement = mxTags.getDatabase().getConnection().prepareStatement("INSERT INTO tags(tag, slot) VALUES (?, ?)");
            statement.setString(1, tag);
            statement.setInt(2, slot);

            statement.executeUpdate();
            statement.close();

        } catch (SQLException e) {
            mxTags.getLogger().severe("Unable to create tag: " + tag + " in slot " + slot);
            if (mxTags.debugMode()) e.printStackTrace();
        }
    }

    public void deleteTag(int tagID) {
        try {
            PreparedStatement statement = mxTags.getDatabase().getConnection().prepareStatement("DELETE FROM tags WHERE id = ?");
            statement.setInt(1, tagID);

            statement.executeUpdate();
            statement.close();

        } catch (SQLException exception) {
            mxTags.getLogger().severe("Unable to remove tag: " + tagID);
            if (mxTags.debugMode()) exception.printStackTrace();
        }
    }

    public String getTag(int tagID) {
        String tag = null;
        try {
            PreparedStatement statement = mxTags.getDatabase().getConnection().prepareStatement("SELECT * FROM tags WHERE id = ?");
            statement.setInt(1, tagID);
            ResultSet results = statement.executeQuery();

            if (results.next()) {
                tag = results.getString("tag");
            } else {
                mxTags.getLogger().severe("Unable to find tag: " + tagID);
            }
            statement.close();

        } catch (SQLException exception) {
            mxTags.getLogger().severe("Error finding tag with id: " + tagID);
            if (mxTags.debugMode()) exception.printStackTrace();
        }

        return tag;
    }

    public int getPlayerTagID(Player player) {
        String uuid = player.getUniqueId().toString();
        int tagID = 0;
        try {
            PreparedStatement statement = mxTags.getDatabase().getConnection().prepareStatement("SELECT tag FROM players WHERE uuid = ?");
            statement.setString(1, uuid);
            ResultSet results = statement.executeQuery();

            if (results.next()) {
                tagID = results.getInt("tag");
            }
            statement.close();

        } catch (SQLException exception) {
            mxTags.getLogger().severe("Error finding tag with id: " + tagID);
            if (mxTags.debugMode()) exception.printStackTrace();
        }
        return tagID;
    }

    public void selectTag(Player player, int tagID) {
        String uuid = player.getUniqueId().toString();
        try {
            PreparedStatement statement = mxTags.getDatabase().getConnection().prepareStatement("SELECT tag FROM players WHERE uuid = ?");
            statement.setString(1, uuid);
            ResultSet results = statement.executeQuery();

            if (results.next()) {
                statement = mxTags.getDatabase().getConnection().prepareStatement("UPDATE players SET tag = ? WHERE uuid = ?");
                statement.setInt(1, tagID);
                statement.setString(2, uuid);
            } else {
                statement = mxTags.getDatabase().getConnection().prepareStatement("INSERT INTO players(uuid, tag) VALUES (?, ?)");
                statement.setString(1, uuid);
                statement.setInt(2, tagID);
            }

            statement.executeUpdate();
            statement.close();

            setTag(player, tagID, true);

        } catch (SQLException e) {
            mxTags.getLogger().severe("Unable to select tagID: " + tagID);
            if (mxTags.debugMode()) e.printStackTrace();
        }
    }

    public void setTag(Player player, int tagID, boolean message) {
        String tag = mxTags.tagManager().getTag(tagID);

        player.setDisplayName(player.getName() + " " + tag);
        player.setPlayerListName(player.getName() + " " + tag);

        if (message) player.sendMessage(ChatColor.GREEN + "Successfully selected tag: " + tag);
    }

    private final int ROW_AMOUNT_PER_CHAT_LIST_PAGE = 10;

    public List<String> listTags(int page) {
        ArrayList<String> tags = new ArrayList<>();
        int offset = (page * ROW_AMOUNT_PER_CHAT_LIST_PAGE) - ROW_AMOUNT_PER_CHAT_LIST_PAGE;
        try {
            PreparedStatement statement = mxTags.getDatabase().getConnection().prepareStatement("SELECT * FROM tags ORDER BY id LIMIT ? OFFSET ?");
            statement.setInt(1, ROW_AMOUNT_PER_CHAT_LIST_PAGE);
            statement.setInt(2, offset);
            ResultSet results = statement.executeQuery();

            while (results.next()) {
                String tagID = "" + results.getInt("id");
                String tag = results.getString(2);
                String tagSlot = "" + results.getInt(3);
                tags.add(tagID + "¢" + tag + "¢" + tagSlot);
            }
            statement.close();

        } catch (SQLException exception) {
            mxTags.getLogger().severe("Error listing tags");
            if (mxTags.debugMode()) exception.printStackTrace();
        }

        return tags;
    }

    public List<String> listTagsAsGUIpage(int page) {
        ArrayList<String> tags = new ArrayList<>();
        int offset = (page * 45) - 45;

        try {
            PreparedStatement statement = mxTags.getDatabase().getConnection().prepareStatement("SELECT tag FROM tags ORDER BY id LIMIT 45 OFFSET ?");
            statement.setInt(1, offset);
            ResultSet results = statement.executeQuery();

            while (results.next()) {
                String tag = results.getString("tag");
                tags.add(tag);
            }

        } catch (SQLException exception) {
            mxTags.getLogger().severe("Error listing tags");
            if (mxTags.debugMode()) exception.printStackTrace();
        }

        return tags;
    }

    public enum listEntryType {
        TAG_AMOUNT, CHAT_PAGE_AMOUNT, GUI_PAGE_AMOUNT
    }

    public int getEntryAmount(listEntryType type) {
        int tagAmount = 0;
        int returnAmount = 0;
        try {
            PreparedStatement statement = mxTags.getDatabase().getConnection().prepareStatement("SELECT COUNT(*) AS totalAmount FROM tags");
            ResultSet results = statement.executeQuery();

            if (results.next()) {
                tagAmount = results.getInt("totalAmount");
            }
            if (type == listEntryType.CHAT_PAGE_AMOUNT) {
                returnAmount = (tagAmount + ROW_AMOUNT_PER_CHAT_LIST_PAGE - 1) / ROW_AMOUNT_PER_CHAT_LIST_PAGE;

            } else if (type == listEntryType.GUI_PAGE_AMOUNT) {
                returnAmount = (tagAmount + 45 - 1) / 45;

            } else returnAmount = tagAmount;

            statement.close();

        } catch (SQLException exception) {
            mxTags.getLogger().severe("Error getting tag listing page amount");
            if (mxTags.debugMode()) exception.printStackTrace();
        }
        return returnAmount;
    }

    public boolean nextGUIpageExists(int currentPage) {
        if (currentPage < getEntryAmount(listEntryType.GUI_PAGE_AMOUNT)) return true;
        return false;
    }
}
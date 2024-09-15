package dev.mxlx.mxTags.utils;

import dev.mxlx.mxTags.MxTags;
import org.bukkit.entity.Player;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class TagManager {

    private MxTags mxTags = MxTags.getInstance();

    public void createTag(String tag, Integer slot) {
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
            PreparedStatement statement = mxTags.getDatabase().getConnection().prepareStatement("INSERT INTO players(uuid, tag) VALUES (?, ?)");
            statement.setString(1, uuid);
            statement.setInt(2, tagID);

            statement.executeUpdate();
            statement.close();

        } catch (SQLException e) {
            mxTags.getLogger().severe("Unable to select tagID: " + tagID);
            if (mxTags.debugMode()) e.printStackTrace();
        }
    }
}
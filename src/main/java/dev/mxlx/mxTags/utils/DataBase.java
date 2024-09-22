package dev.mxlx.mxTags.utils;

import dev.mxlx.mxTags.MxTags;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

public class DataBase {

    private MxTags mxTags = MxTags.getInstance();

    private final String TYPE;
    private final String HOST;
    private final String PORT;
    private final String USER;
    private final String PASSWORD;
    private final String DATABASE_NAME;

    public DataBase(String TYPE, String HOST, String PORT, String USER, String PASSWORD, String DATABASE_NAME) {
        this.TYPE = TYPE;
        this.HOST = HOST;
        this.PORT = PORT;
        this.USER = USER;
        this.PASSWORD = PASSWORD;
        this.DATABASE_NAME = DATABASE_NAME;
    }

    private Connection connection;
    public Connection getConnection() throws SQLException {
        if (connection != null) {
            return connection;
        }

        String url = "jdbc:" + TYPE + "://" + HOST + ":" + PORT + "/" + DATABASE_NAME;

        try {
            Class.forName("org.mariadb.jdbc.Driver");
            this.connection = DriverManager.getConnection(url, this.USER, this.PASSWORD);

            mxTags.getLogger().info("Successfully connected to database");

        } catch (ClassNotFoundException exception) {
            mxTags.getLogger().severe("Failed to load JDBC driver or get connection");
            if (mxTags.debugMode()) exception.printStackTrace();
        }

        return this.connection;
    }

    public void initializeDatabase() throws SQLException {
        Statement statement = getConnection().createStatement();
        String sql = "CREATE TABLE IF NOT EXISTS tags(id int PRIMARY KEY AUTO_INCREMENT, tag text, priority int)";
        statement.execute(sql);
        sql = "CREATE TABLE IF NOT EXISTS players(uuid varchar(36), tag int, FOREIGN KEY (tag) REFERENCES tags(id))";
        statement.execute(sql);
        statement.close();
    }

    public void closeConnection() {
        try {
            if (this.connection != null) {
                this.connection.close();
            }
        } catch (SQLException exception) {
            mxTags.getLogger().severe("Error closing connection");
            if (mxTags.debugMode()) exception.printStackTrace();
        }
    }
}
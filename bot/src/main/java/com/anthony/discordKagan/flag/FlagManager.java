package com.anthony.discordKagan.flag;

import com.anthony.discordKagan.Main;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.HashMap;
import java.util.Map;

public class FlagManager {

    private FlagManager() {}

    private static final Map<String, Boolean> flags = new HashMap<>();

    public static void loadFlags() {

        try (Statement stmt = Main.sql.createStatement()) {

            String sql = "SELECT * FROM flags";
            ResultSet resultSet = stmt.executeQuery(sql);
            if (resultSet.next()) {
                do {
                    flags.put(resultSet.getString("flag"), resultSet.getBoolean("state"));
                } while (resultSet.next());
            }

        } catch (SQLException _) {}
    }

    private static void addFlag(String flag) {
        String sql = "INSERT INTO flags (flag, state) VALUES ('" + flag + "', false)";

        try (Statement stmt = Main.sql.createStatement()) {
            stmt.execute(sql);
            flags.put(flag, false);
        } catch (SQLException _) {}
    }

    public static boolean getFlag(String flag) {
        Boolean state = flags.get(flag);

        if (state == null) {
            addFlag(flag);
            return false;
        }
        return state;
    }
}

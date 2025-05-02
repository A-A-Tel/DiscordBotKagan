package com.anthony.discordKagan.message;


import com.anthony.discordKagan.Main;
import net.dv8tion.jda.api.entities.Message;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class Filter {

    private static final Map<Integer, String> leet = Map.<Integer, String>ofEntries(

            // Digits
            Map.entry(0x0030, "o"),
            Map.entry(0x0031, "l"),
            Map.entry(0x0032, "z"),
            Map.entry(0x0033, "e"),
            Map.entry(0x0034, "a"),
            Map.entry(0x0035, "s"),
            Map.entry(0x0036, "g"),
            Map.entry(0x0037, "t"),
            Map.entry(0x0038, "b"),
            Map.entry(0x0039, "g"),

            // Symbols
            Map.entry(0x0021, "i"), // !
            Map.entry(0x0040, "a"), // @
            Map.entry(0x0024, "s"), // $
            Map.entry(0x0026, "and"), // &

            // Regional Indicators 🇦 to 🇿
            Map.entry(0x1F1E6, "a"),
            Map.entry(0x1F1E7, "b"),
            Map.entry(0x1F1E8, "c"),
            Map.entry(0x1F1E9, "d"),
            Map.entry(0x1F1EA, "e"),
            Map.entry(0x1F1EB, "f"),
            Map.entry(0x1F1EC, "g"),
            Map.entry(0x1F1ED, "h"),
            Map.entry(0x1F1EE, "i"),
            Map.entry(0x1F1EF, "j"),
            Map.entry(0x1F1F0, "k"),
            Map.entry(0x1F1F1, "l"),
            Map.entry(0x1F1F2, "m"),
            Map.entry(0x1F1F3, "n"),
            Map.entry(0x1F1F4, "o"),
            Map.entry(0x1F1F5, "p"),
            Map.entry(0x1F1F6, "q"),
            Map.entry(0x1F1F7, "r"),
            Map.entry(0x1F1F8, "s"),
            Map.entry(0x1F1F9, "t"),
            Map.entry(0x1F1FA, "u"),
            Map.entry(0x1F1FB, "v"),
            Map.entry(0x1F1FC, "w"),
            Map.entry(0x1F1FD, "x"),
            Map.entry(0x1F1FE, "y"),
            Map.entry(0x1F1FF, "z"),

            Map.entry(0x1F170, "a"),
            Map.entry(0x1F171, "b"),
            Map.entry(0x1F172, "c"),
            Map.entry(0x1F173, "d"),
            Map.entry(0x1F174, "e"),
            Map.entry(0x1F175, "f"),
            Map.entry(0x1F176, "g"),
            Map.entry(0x1F177, "h"),
            Map.entry(0x1F178, "i"),
            Map.entry(0x1F179, "j"),
            Map.entry(0x1F17A, "k"),
            Map.entry(0x1F17B, "l"),
            Map.entry(0x1F17C, "m"),
            Map.entry(0x1F17D, "n"),
            Map.entry(0x1F17E, "o"),
            Map.entry(0x1F17F, "p"),
            Map.entry(0x1F180, "q"),
            Map.entry(0x1F181, "r"),
            Map.entry(0x1F182, "s"),
            Map.entry(0x1F183, "t"),
            Map.entry(0x1F184, "u"),
            Map.entry(0x1F185, "v"),
            Map.entry(0x1F186, "w"),
            Map.entry(0x1F187, "x"),
            Map.entry(0x1F188, "y"),
            Map.entry(0x1F189, "z")
    );

    private static List<String> bannedWords;

    public static void filterMessage(Message message) {

        String messageText = message.getContentStripped();

        if (!messageText.isEmpty()) {
            for (String bannedWord : bannedWords) {
                if (messageText.contains(bannedWord)) {
                    message.delete().queue();
                    break;
                }
            }
        }
    }

    private static String removeLeet(String text) {

        StringBuilder builder = new StringBuilder();
        int[] textCp = text.toLowerCase().codePoints().toArray();

        for (int cp : textCp) {
            if (leet.containsKey(cp)) {
                builder.append(leet.get(cp));
            } else if (Character.isLetter(cp)) {
                builder.appendCodePoint(cp);
            }
        }
        return builder.toString();
    }

    public static void loadBannedWords() {
        String sql = "SELECT * FROM banned_words";

        try (Statement stmt = Main.sql.createStatement()) {

            ResultSet rs = stmt.executeQuery(sql);

            List<String> words = new ArrayList<>();


            while (rs.next()) {
                words.add(rs.getString("word"));
            }
            bannedWords = words;

        } catch (SQLException e) {
            bannedWords = List.of();
        }
    }
}

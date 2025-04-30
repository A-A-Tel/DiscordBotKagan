package com.anthony.discordKagan.command.commands.bannedWord;

import com.anthony.discordKagan.Main;
import com.anthony.discordKagan.command.ICommand;
import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;
import net.dv8tion.jda.api.interactions.commands.DefaultMemberPermissions;
import net.dv8tion.jda.api.interactions.commands.build.OptionData;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;

public class GetBannedWords implements ICommand {
    @Override
    public String getName() {
        return "get-banned-words";
    }

    @Override
    public String getDescription() {
        return "Shows the list of bad words that have been found in the guild.";
    }

    @Override
    public List<OptionData> getOptions() {
        return List.of();
    }

    @Override
    public DefaultMemberPermissions getPermission() {
        return DefaultMemberPermissions.ENABLED;
    }

    @Override
    public void execute(SlashCommandInteractionEvent event) {

        String reply;

        try (Statement statement = Main.sql.createStatement()) {

            ResultSet resultSet = statement.executeQuery("SELECT * FROM banned_words");

            if (!resultSet.next()) {
                reply = "No banned words found.";
            } else {

                StringBuilder builder = new StringBuilder("Table:\nid     word");

                do {
                    builder.append("\n")
                            .append(resultSet.getInt("id"))
                            .append("     ")
                            .append(resultSet.getString("word"));

                } while (resultSet.next());
                reply = builder.toString();
            }

        } catch (SQLException e) {
            reply = "Fatal error: " + e.getMessage();
        }
        event.reply(reply).queue();
    }
}

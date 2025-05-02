package com.anthony.discordKagan.command.commands.flag;

import com.anthony.discordKagan.Main;
import com.anthony.discordKagan.command.ICommand;
import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;
import net.dv8tion.jda.api.interactions.commands.DefaultMemberPermissions;
import net.dv8tion.jda.api.interactions.commands.OptionMapping;
import net.dv8tion.jda.api.interactions.commands.build.OptionData;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;

public class GetFlags implements ICommand {

    @Override
    public String getName() {
        return "get-flags";
    }

    @Override
    public String getDescription() {
        return "Gets the list of flags for the bot";
    }

    @Override
    public List<OptionData> getOptions() {
        return List.of();
    }

    @Override
    public DefaultMemberPermissions getPermission() {
        return DefaultMemberPermissions.DISABLED;
    }

    @Override
    public void execute(SlashCommandInteractionEvent event) {

        String reply;

        try (Statement statement = Main.sql.createStatement()) {

            ResultSet resultSet = statement.executeQuery("SELECT * FROM flags");

            if (!resultSet.next()) {
                reply = "No flags found.";
            } else {

                StringBuilder builder = new StringBuilder("Table:\nflag     state");

                do {
                    builder.append("\n")
                            .append(resultSet.getString("flag"))
                            .append("     ")
                            .append(resultSet.getBoolean("state"));

                } while (resultSet.next());
                reply = builder.toString();
            }

        } catch (SQLException e) {
            reply = "Fatal error: " + e.getMessage();
        }
        event.reply(reply).queue();
    }
}

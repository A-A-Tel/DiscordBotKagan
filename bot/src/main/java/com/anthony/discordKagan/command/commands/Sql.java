package com.anthony.discordKagan.command.commands;

import com.anthony.discordKagan.Main;
import com.anthony.discordKagan.command.ICommand;
import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;
import net.dv8tion.jda.api.interactions.commands.DefaultMemberPermissions;
import net.dv8tion.jda.api.interactions.commands.OptionMapping;
import net.dv8tion.jda.api.interactions.commands.OptionType;
import net.dv8tion.jda.api.interactions.commands.build.OptionData;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;

public class Sql implements ICommand {


    @Override
    public String getName() {
        return "query";
    }

    @Override
    public String getDescription() {
        return "directly query the database";
    }

    @Override
    public List<OptionData> getOptions() {
        return List.of(
                new OptionData(OptionType.STRING, "sql", "query", true, false)
        );
    }

    @Override
    public DefaultMemberPermissions getPermission() {
        return DefaultMemberPermissions.DISABLED;
    }

    @Override
    public void execute(SlashCommandInteractionEvent event) {

        String reply = "Failure";
        OptionMapping option = event.getOption("sql");

        if (option != null) {
            String query = option.getAsString();
            if (!query.isBlank()) {
                try (Statement stmt = Main.sql.createStatement()) {

                    ResultSet rs = stmt.executeQuery(query);

                    reply = rs.toString();

                } catch (SQLException e) {
                    reply = "Fatal Error: " + e.getMessage();
                }
            }
        }
        event.reply(reply).queue();
    }
}

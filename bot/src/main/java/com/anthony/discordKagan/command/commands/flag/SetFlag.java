package com.anthony.discordKagan.command.commands.flag;

import com.anthony.discordKagan.Main;
import com.anthony.discordKagan.command.ICommand;
import com.anthony.discordKagan.flag.FlagManager;
import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;
import net.dv8tion.jda.api.interactions.commands.DefaultMemberPermissions;
import net.dv8tion.jda.api.interactions.commands.OptionMapping;
import net.dv8tion.jda.api.interactions.commands.OptionType;
import net.dv8tion.jda.api.interactions.commands.build.OptionData;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;

public class SetFlag implements ICommand {
    @Override
    public String getName() {
        return "set-flag";
    }

    @Override
    public String getDescription() {
        return "Set a server setting/flag. Use get-flags to view all server settings.";
    }

    @Override
    public List<OptionData> getOptions() {
        return List.of(
                new OptionData(OptionType.STRING, "flag", "Name of the flag", true, false),
                new OptionData(OptionType.BOOLEAN, "state", "State of the flag", true, false)
        );
    }

    @Override
    public DefaultMemberPermissions getPermission() {
        return DefaultMemberPermissions.DISABLED;
    }

    @Override
    public void execute(SlashCommandInteractionEvent event) {

        String reply;
        String sql;

        OptionMapping flagMap = event.getOption("flag");
        OptionMapping stateMap = event.getOption("state");

        if (flagMap == null || stateMap == null) {

            reply = "Critical error: options loaded incorrectly.";
        } else {

            String flag = flagMap.getAsString();
            boolean state = stateMap.getAsBoolean();

            sql = "UPDATE flags SET state=? WHERE flag=?";

            try (PreparedStatement stmt = Main.sql.prepareStatement(sql)) {

                stmt.setBoolean(1, state);
                stmt.setString(2, flag);
                stmt.execute();

                reply = "Flag set successfully.";


            } catch (SQLException e) {
                reply = "Fatal error: " + e.getMessage();
            }
        }
        event.reply(reply).queue();
    }
}

package com.anthony.discordKagan.command.commands.bannedWord;

import com.anthony.discordKagan.Main;
import com.anthony.discordKagan.command.ICommand;
import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;
import net.dv8tion.jda.api.interactions.commands.DefaultMemberPermissions;
import net.dv8tion.jda.api.interactions.commands.OptionMapping;
import net.dv8tion.jda.api.interactions.commands.OptionType;
import net.dv8tion.jda.api.interactions.commands.build.OptionData;

import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;

public class RemoveBannedWord implements ICommand {
    @Override
    public String getName() {
        return "remove-banned-word";
    }

    @Override
    public String getDescription() {
        return "Use /get-banned-words to get banned words and their IDs";
    }

    @Override
    public List<OptionData> getOptions() {
        return List.of(
                new OptionData(OptionType.INTEGER, "id", "The id of the banned word to remove", true, true)
        );
    }

    @Override
    public DefaultMemberPermissions getPermission() {
        return DefaultMemberPermissions.DISABLED;
    }

    @Override
    public void execute(SlashCommandInteractionEvent event) {

        String reply;
        OptionMapping idOption = event.getOption("id");

        if (idOption == null) {
            reply = "Critical error: id=NULL";

        } else {
            int id = idOption.getAsInt();

            try (Statement stmt = Main.sql.createStatement()) {

                String sql = "DELETE FROM banned_words WHERE id = " + id;
                stmt.executeUpdate(sql);

                reply = "Banned word at " + id + " removed.";

            } catch (SQLException e) {
                reply = "Fatal error: " + e.getMessage();
            }
        }

        event.reply(reply).queue();
    }
}

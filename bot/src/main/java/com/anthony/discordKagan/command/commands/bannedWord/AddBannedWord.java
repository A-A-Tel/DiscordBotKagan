package com.anthony.discordKagan.command.commands.bannedWord;

import com.anthony.discordKagan.Main;
import com.anthony.discordKagan.command.ICommand;
import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;
import net.dv8tion.jda.api.interactions.commands.DefaultMemberPermissions;
import net.dv8tion.jda.api.interactions.commands.OptionMapping;
import net.dv8tion.jda.api.interactions.commands.OptionType;
import net.dv8tion.jda.api.interactions.commands.build.OptionData;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;

public class AddBannedWord implements ICommand {
    @Override
    public String getName() {
        return "add-banned-word";
    }

    @Override
    public String getDescription() {
        return "Adds a banned word if you have permissions";
    }

    @Override
    public List<OptionData> getOptions() {
        return List.of(
                new OptionData(OptionType.STRING, "word", "the banned word what else", true, false)
        );
    }

    @Override
    public DefaultMemberPermissions getPermission() {
        return DefaultMemberPermissions.DISABLED;
    }

    @Override
    public void execute(SlashCommandInteractionEvent event) {

        String reply;

        OptionMapping option = event.getOption("word");
        if (option == null) {
            reply = "No banned word found.";
        } else {
            String word = option.getAsString();

            String sql = "INSERT INTO banned_words (word) VALUES (?)";

            try (PreparedStatement stmt = Main.sql.prepareStatement(sql)) {

                stmt.setString(1, word);

                stmt.execute();
                reply = "Banned word added.";

            } catch (SQLException e) {
                reply = "Fatal error: " + e.getMessage();
            }
        }
        event.reply(reply).queue();
    }
}

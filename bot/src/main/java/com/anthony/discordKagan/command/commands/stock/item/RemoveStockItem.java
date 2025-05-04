package com.anthony.discordKagan.command.commands.stock.item;

import com.anthony.discordKagan.Main;
import com.anthony.discordKagan.command.ICommand;
import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;
import net.dv8tion.jda.api.interactions.commands.DefaultMemberPermissions;
import net.dv8tion.jda.api.interactions.commands.OptionMapping;
import net.dv8tion.jda.api.interactions.commands.OptionType;
import net.dv8tion.jda.api.interactions.commands.build.OptionData;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;

public class RemoveStockItem implements ICommand {
    @Override
    public String getName() {
        return "remove-stock-item";
    }

    @Override
    public String getDescription() {
        return "Removes a stock item by name";
    }

    @Override
    public List<OptionData> getOptions() {
        return List.of(
                new OptionData(OptionType.STRING, "name", "The stock item name", true, false)
        );
    }

    @Override
    public DefaultMemberPermissions getPermission() {
        return DefaultMemberPermissions.DISABLED;
    }

    @Override
    public void execute(SlashCommandInteractionEvent event) {

        String reply;
        OptionMapping nameOption = event.getOption("name");

        if (nameOption == null) {
            reply = "Critical error: No name given";
        } else {
            String name = nameOption.getAsString();

            String sql = "DELETE FROM `stock` WHERE name = ?";
            try (PreparedStatement stmt = Main.sql.prepareStatement(sql)) {

                stmt.setString(1, name);
                stmt.execute();
                reply = "Removed stock item " + name + " from stock";

            } catch (SQLException e) {
                reply = "Critical error: " + e.getMessage();
            }
        }
        event.reply(reply).queue();
    }
}

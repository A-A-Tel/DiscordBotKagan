package com.anthony.discordKagan.command.commands.stock.item;


import com.anthony.discordKagan.Main;
import com.anthony.discordKagan.command.ICommand;
import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;
import net.dv8tion.jda.api.interactions.commands.DefaultMemberPermissions;
import net.dv8tion.jda.api.interactions.commands.OptionMapping;
import net.dv8tion.jda.api.interactions.commands.OptionType;
import net.dv8tion.jda.api.interactions.commands.build.OptionData;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

public class AddStockItem implements ICommand {
    @Override
    public String getName() {
        return "add-stock-item";
    }

    @Override
    public String getDescription() {
        return "Add an item to the stock.";
    }

    @Override
    public List<OptionData> getOptions() {
        return List.of(
                new OptionData(OptionType.STRING, "name", "The name of the item to be added.", true, false)
        );
    }

    @Override
    public DefaultMemberPermissions getPermission() {
        return DefaultMemberPermissions.DISABLED;
    }

    @Override
    public void execute(SlashCommandInteractionEvent event) {
        String reply = null;

        OptionMapping nameOption = event.getOption("name");

        if (nameOption == null) {
            reply = "Critical error: No name given.";
        } else {
            String name = nameOption.getAsString();
            String sql;
            boolean isValid = false;


            sql = "SELECT * FROM stock WHERE name=?";
            try (PreparedStatement stmt = Main.sql.prepareStatement(sql)) {
                stmt.setString(1, name);
                ResultSet rs = stmt.executeQuery();
                if (!rs.next()) {
                    isValid = true;
                }
            } catch (SQLException e) {
                reply = "Fatal error: " + e.getMessage();
            }

            if (isValid) {


                sql = "INSERT INTO stock (name, amount, auto_stock, `interval`, auto_amount) VALUES (?, 0, false, 1000, 0)";
                try (PreparedStatement stmt = Main.sql.prepareStatement(sql)) {
                    stmt.setString(1, name);
                    stmt.execute();
                    reply = "Added " + name + " to the list of drugs.";
                } catch (SQLException e) {
                    reply = "Fatal error: " + e.getMessage();
                }
            } else if (reply == null) {
                reply = "Critical error: Item already exists.";
            }


        }
        event.reply(reply).queue();
    }
}

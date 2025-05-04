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

public class SetStockIntervalRates implements ICommand {
    @Override
    public String getName() {
        return "set-stock-interval-rates";
    }

    @Override
    public String getDescription() {
        return "The interval indicates how many SECONDS it will take for the amount to get added.";
    }

    @Override
    public List<OptionData> getOptions() {
        return List.of(
                new OptionData(OptionType.STRING, "name", "The name of the stock to edit.", true, false),
                new OptionData(OptionType.BOOLEAN, "active", "Sets the interval rate to be active or not", false, false),
                new OptionData(OptionType.INTEGER, "interval", "The rate in SECONDS", false, false),
                new OptionData(OptionType.INTEGER, "amount", "Item add rate after interval", false, false)

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
        OptionMapping activeOption = event.getOption("active");
        OptionMapping intervalOption = event.getOption("interval");
        OptionMapping amountOption = event.getOption("amount");

        if (nameOption == null) {
            reply = "Critical error: No name given.";
        } else {

            try {
                String sql;
                String name = nameOption.getAsString();
                PreparedStatement stmt;


                // Check if the product actually exists
                sql = "SELECT * FROM stock WHERE name = ?";
                stmt = Main.sql.prepareStatement(sql);

                stmt.setString(1, name);
                ResultSet rs = stmt.executeQuery();

                if (rs.next()) {
                    if (activeOption != null) {

                        boolean active = activeOption.getAsBoolean();
                        sql = "UPDATE stock SET auto_stock = ? WHERE name = ?";
                        stmt = Main.sql.prepareStatement(sql);

                        stmt.setBoolean(1, active);
                        stmt.setString(2, name);
                        stmt.execute();
                    }

                    if (intervalOption != null) {

                        int interval = intervalOption.getAsInt();
                        sql = "UPDATE stock SET `interval` = ? WHERE name = ?";
                        stmt = Main.sql.prepareStatement(sql);

                        stmt.setInt(1, interval);
                        stmt.setString(2, name);
                        stmt.execute();
                    }

                    if (amountOption != null) {

                        int amount = amountOption.getAsInt();
                        sql = "UPDATE stock SET `auto_amount` = ? WHERE name = ?";
                        stmt = Main.sql.prepareStatement(sql);

                        stmt.setInt(1, amount);
                        stmt.setString(2, name);
                        stmt.execute();
                    }
                    reply = "All operations completed successfully.";
                } else {
                    reply = "Critical error: Product does not exist.";
                }

            } catch (SQLException e) {
                reply = "Fatal error: " + e.getMessage();
            }
        }

        event.reply(reply).queue();
    }
}

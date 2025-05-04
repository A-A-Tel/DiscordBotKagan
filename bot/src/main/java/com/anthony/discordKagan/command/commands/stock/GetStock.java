package com.anthony.discordKagan.command.commands.stock;

import com.anthony.discordKagan.Main;
import com.anthony.discordKagan.command.ICommand;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;
import net.dv8tion.jda.api.interactions.commands.DefaultMemberPermissions;
import net.dv8tion.jda.api.interactions.commands.build.OptionData;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;

public class GetStock implements ICommand {
    @Override
    public String getName() {
        return "get-stock";
    }

    @Override
    public String getDescription() {
        return "Print out the drug stock";
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

        EmbedBuilder ebuilder = new EmbedBuilder();

        ebuilder.setTitle("Stock");

        ebuilder.setThumbnail("https://www.shutterstock.com/image-vector/grape-smoking-blunt-weed-nug-600nw-2046688814.jpg");

        try (Statement stmt = Main.sql.createStatement()) {

            String sql = "SELECT * FROM stock";

            ResultSet rs = stmt.executeQuery(sql);

            if (!rs.next()) {
                ebuilder.addField("Items:", "No drugs found.", false);

            } else {
                StringBuilder builder = new StringBuilder("```ITEM")
                        .repeat(" ", 11)
                        .append("AMOUNT")
                        .repeat(' ', 4)
                        .append("PROPERTIES");
                do {
                    String name = rs.getString("name");
                    int amount = rs.getInt("auto_amount");

                    builder.append("\n")
                            .append(name)
                            .repeat(' ', 15 - name.length())
                            .append(amount)
                            .repeat(' ', 10 - String.valueOf(amount).length())
                            .append(rs.getBoolean("auto_stock"))
                            .append('-')
                            .append(rs.getInt("interval"))
                            .append('-')
                            .append(rs.getString("auto_amount"));

                } while (rs.next());
                ebuilder.addField("Items:", builder.append("```").toString(), false);
            }

        } catch (SQLException e) {
            ebuilder.addField("Error:", e.getMessage(), false);
        }
        event.replyEmbeds(ebuilder.build()).queue();
    }
}

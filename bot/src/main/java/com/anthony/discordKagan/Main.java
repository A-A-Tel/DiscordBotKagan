package com.anthony.discordKagan;

import com.anthony.discordKagan.command.CommandManager;
import com.anthony.discordKagan.command.commands.Kill;
import com.anthony.discordKagan.command.commands.Sql;
import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.JDABuilder;
import net.dv8tion.jda.api.entities.Guild;
import net.dv8tion.jda.api.entities.channel.middleman.MessageChannel;
import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;
import net.dv8tion.jda.api.events.session.ReadyEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import net.dv8tion.jda.api.requests.GatewayIntent;
import net.dv8tion.jda.api.utils.MemberCachePolicy;
import org.jetbrains.annotations.NotNull;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.TimeZone;

public class Main extends ListenerAdapter {

    public static Connection sql;

    static {
        try {
            String host = System.getenv("DB_HOST");
            String port = "3306";
            String user = System.getenv("DB_USER");
            String pass = System.getenv("DB_PASS");
            String name = System.getenv("DB_NAME");

            String url = "jdbc:mysql://" + host + ":" + port + "/" + name;
            sql = DriverManager.getConnection(url, user, pass);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public static JDA jda = JDABuilder.createDefault(System.getenv("BOT_TOKEN"))
            .setMemberCachePolicy(MemberCachePolicy.ALL)
            .enableIntents(
                    GatewayIntent.GUILD_MEMBERS,
                    GatewayIntent.MESSAGE_CONTENT,
                    GatewayIntent.GUILD_MESSAGE_POLLS
            )
            .addEventListeners(new Main()).build();

    public static Guild guild;

    public static void main(String[] args) {
    }

    /// Begin app listener ///

    public CommandManager command = new CommandManager();

    @Override
    public void onReady(@NotNull ReadyEvent event) {

        StringBuilder builder = new StringBuilder(LocalDateTime.now(TimeZone.getTimeZone("Europe/Amsterdam").toZoneId()).toString());
        builder.replace(10, 11, " ");
        builder.replace(16, builder.length(), "");

        for (Guild guild : jda.getGuilds()) {
            for (MessageChannel channel : guild.getTextChannels()) {
                if (channel.getId().equals("1306668416916000839")) {
                    Main.guild = guild;
                    channel.sendMessage(
                            "Bot compiled and run at: " + builder
                            + "\nDatabase Connection: " + sql
                            + "\nGuild: " + guild.getName() + " (" + guild.getId() + ")"
                            + "\n"
                    ).queue();
                }
            }
        }
        if (Main.guild == null) {
            throw new IllegalStateException("No guild found");
        }

        command.add(new Kill());
        command.add(new Sql());
        command.loadCommands();


    }

    @Override
    public void onSlashCommandInteraction(@NotNull SlashCommandInteractionEvent event) {
        command.executeCommand(event);
    }
}

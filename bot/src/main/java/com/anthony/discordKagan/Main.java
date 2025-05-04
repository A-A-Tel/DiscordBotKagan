package com.anthony.discordKagan;

import com.anthony.discordKagan.command.CommandManager;
import com.anthony.discordKagan.flag.FlagManager;
import com.anthony.discordKagan.message.Filter;
import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.JDABuilder;
import net.dv8tion.jda.api.entities.Guild;
import net.dv8tion.jda.api.entities.Message;
import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import net.dv8tion.jda.api.events.session.ReadyEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import net.dv8tion.jda.api.requests.GatewayIntent;
import net.dv8tion.jda.api.utils.MemberCachePolicy;
import org.jetbrains.annotations.NotNull;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class Main extends ListenerAdapter {

    public static final Connection sql;

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
            throw new IllegalStateException(e);
        }
    }

    public static final JDA jda = JDABuilder.createDefault(System.getenv("BOT_TOKEN"))
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
    @Override
    public void onReady(@NotNull ReadyEvent event) {

        Main.guild = jda.getGuildById(System.getenv("GUILD_ID"));
        if (Main.guild == null) {
            throw new IllegalStateException("No guild found");
        }

        FlagManager.loadFlags();
        Filter.loadBannedWords();
        CommandManager.loadCommands();
    }

    @Override
    public void onSlashCommandInteraction(@NotNull SlashCommandInteractionEvent event) {
        CommandManager.executeCommand(event);
    }

    @Override
    public void onMessageReceived(@NotNull MessageReceivedEvent event) {

        Message message = event.getMessage();

        if (message.getAuthor().isBot()) return;

        if (FlagManager.getFlag("chat_filter")) {
            Filter.filterMessage(message);
        }
    }
}

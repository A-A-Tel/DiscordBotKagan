package com.anthony.discordKagan;

import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.JDABuilder;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import net.dv8tion.jda.api.requests.GatewayIntent;
import net.dv8tion.jda.api.utils.MemberCachePolicy;

public class Main extends ListenerAdapter {

    public static JDA jda = JDABuilder.createDefault(Token.BOT_TOKEN)
            .setMemberCachePolicy(MemberCachePolicy.ALL)
            .enableIntents(
                    GatewayIntent.GUILD_MEMBERS,
                    GatewayIntent.MESSAGE_CONTENT,
                    GatewayIntent.GUILD_MESSAGE_POLLS
            )
            .addEventListeners(new Main()).build();

    public static void main(String[] args) {
        System.out.println("Loading Bot...");
    }

    /// Begin app listener ///

    @Override
    public void onMessageReceived(MessageReceivedEvent event) {

    }

}

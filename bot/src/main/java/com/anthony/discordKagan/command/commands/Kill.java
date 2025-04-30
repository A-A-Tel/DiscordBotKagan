package com.anthony.discordKagan.command.commands;

import com.anthony.discordKagan.command.ICommand;
import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;
import net.dv8tion.jda.api.interactions.commands.DefaultMemberPermissions;
import net.dv8tion.jda.api.interactions.commands.build.OptionData;

import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

public class Kill implements ICommand {
    @Override
    public String getName() {
        return "kill";
    }

    @Override
    public String getDescription() {
        return "kill the bot allowing full rebuild";
    }

    @Override
    public List<OptionData> getOptions() {
        return List.of();
    }

    @Override
    public DefaultMemberPermissions getPermission() {
        return DefaultMemberPermissions.DISABLED;
    }

    @Override
    public void execute(SlashCommandInteractionEvent event) {
        event.reply("Shutting down in 15 seconds:").queue();

        TimerTask task = new TimerTask() {

            @Override
            public void run() {
                System.exit(0);
            }
        };

        Timer timer = new Timer();

        timer.schedule(task, 15000);
    }
}

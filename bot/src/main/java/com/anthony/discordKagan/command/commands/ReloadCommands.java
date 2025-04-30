package com.anthony.discordKagan.command.commands;

import com.anthony.discordKagan.Main;
import com.anthony.discordKagan.command.CommandManager;
import com.anthony.discordKagan.command.ICommand;
import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;
import net.dv8tion.jda.api.interactions.commands.Command;
import net.dv8tion.jda.api.interactions.commands.DefaultMemberPermissions;
import net.dv8tion.jda.api.interactions.commands.build.OptionData;

import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

public class ReloadCommands implements ICommand {
    @Override
    public String getName() {
        return "reload";
    }

    @Override
    public String getDescription() {
        return "Reloads the commands";
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

        event.reply("Reloading, this could take over a minute").queue();
        List<Command> commands = Main.guild.retrieveCommands().complete();

        for (Command command : commands) {
            Main.guild.deleteCommandById(command.getId()).queue();
        }

        TimerTask task = new TimerTask() {

            @Override
            public void run() {
                CommandManager.loadCommands();
            }
        };
        Timer timer = new Timer();

        timer.schedule(task, 30000);
    }
}

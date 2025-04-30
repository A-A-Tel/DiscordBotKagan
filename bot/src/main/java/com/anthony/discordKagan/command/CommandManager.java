package com.anthony.discordKagan.command;

import com.anthony.discordKagan.Main;
import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;

import java.util.ArrayList;

public class CommandManager {

    private final ArrayList<ICommand> commands = new ArrayList<>();

    public void add(ICommand command) {
        commands.add(command);
    }

    public void loadCommands() {
        for (ICommand command : commands) {
            Main.guild.upsertCommand(command.getName(), command.getDescription())
                    .addOptions(command.getOptions())
                    .setDefaultPermissions(command.getPermission())
                    .queue();
        }
    }

    public void executeCommand(SlashCommandInteractionEvent event) {
        String name = event.getName();

        for (ICommand command : commands) {
            if (command.getName().equals(name)) {
                command.execute(event);
                break;
            }
        }
    }
}

package com.anthony.discordKagan.command;

import com.anthony.discordKagan.Main;
import com.anthony.discordKagan.command.commands.*;
import com.anthony.discordKagan.command.commands.bannedWord.*;
import com.anthony.discordKagan.command.commands.flag.*;
import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;

import java.util.List;

public class CommandManager {

    private CommandManager() {}

    private static final List<ICommand> commands = List.of(
            new Kill(),
            new ReloadCommands(),
            new GetBannedWords(),
            new AddBannedWord(),
            new RemoveBannedWord(),
            new SetFlag(),
            new GetFlags()
    );


    public static void loadCommands() {
        for (ICommand command : commands) {
            Main.guild.upsertCommand(command.getName(), command.getDescription())
                    .addOptions(command.getOptions())
                    .setDefaultPermissions(command.getPermission())
                    .queue();
        }
    }

    public static void executeCommand(SlashCommandInteractionEvent event) {
        String name = event.getName();

        for (ICommand command : commands) {
            if (command.getName().equals(name)) {
                command.execute(event);
                break;
            }
        }
    }
}

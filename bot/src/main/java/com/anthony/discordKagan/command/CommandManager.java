package com.anthony.discordKagan.command;

import com.anthony.discordKagan.Main;
import com.anthony.discordKagan.command.commands.*;
import com.anthony.discordKagan.command.commands.bannedWord.*;
import com.anthony.discordKagan.command.commands.stock.*;
import com.anthony.discordKagan.command.commands.flag.*;
import com.anthony.discordKagan.command.commands.stock.item.AddStockItem;
import com.anthony.discordKagan.command.commands.stock.item.RemoveStockItem;
import com.anthony.discordKagan.command.commands.stock.item.SetStockIntervalRates;
import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;
import net.dv8tion.jda.api.interactions.commands.Command;

import java.util.List;

public class CommandManager {

    private CommandManager() {
    }

    private static final List<ICommand> commands = List.of(
            new Kill(),
            new ReloadCommands(),
            new GetBannedWords(),
            new AddBannedWord(),
            new RemoveBannedWord(),
            new SetFlag(),
            new GetFlags(),
            new GetStock(),
            new AddStockItem(),
            new RemoveStockItem(),
            new SetStockIntervalRates()
    );


    public static void loadCommands() {

        List<Command> commands = Main.guild.retrieveCommands().complete();
        String appID = Main.jda.getSelfUser().getId();

        for (ICommand iCommand : CommandManager.commands) {

            boolean found = false;

            for (Command command : commands) {
                if (command.getApplicationId().equals(appID) && command.getName().equals(iCommand.getName())) {
                    found = true;
                    break;
                }
            }
            if (!found) {
                Main.guild.upsertCommand(iCommand.getName(), iCommand.getDescription())
                        .addOptions(iCommand.getOptions())
                        .setDefaultPermissions(iCommand.getPermission())
                        .queue();
            }
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

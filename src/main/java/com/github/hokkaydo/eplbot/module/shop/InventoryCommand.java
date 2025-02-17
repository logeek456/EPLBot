package com.github.hokkaydo.eplbot.module.shop;

import com.github.hokkaydo.eplbot.Strings;
import com.github.hokkaydo.eplbot.command.Command;
import com.github.hokkaydo.eplbot.command.CommandContext;
import net.dv8tion.jda.api.entities.channel.middleman.MessageChannel;
import net.dv8tion.jda.api.interactions.commands.build.OptionData;

import java.util.List;
import java.util.function.Supplier;

public class InventoryCommand implements Command {

    private final ShopProcessor processor;

    public InventoryCommand(ShopProcessor processor) {
        this.processor = processor;
    }

    @Override
    public void executeCommand(CommandContext context) {
        if (context.interaction().getGuild() == null) return;
        String username = context.user().getName();
        context.replyCallbackAction().setContent(this.processor.showInventory(username)).queue();



    }

    @Override
    public String getName() {
        return "inventory";
    }

    @Override
    public Supplier<String> getDescription() {
        return () -> Strings.getString("INVENTORY_COMMAND_DESCRIPTION");
    }

    @Override
    public List<OptionData> getOptions() {
        return List.of();
    }

    @Override
    public boolean ephemeralReply() {
        return true;
    }

    @Override
    public boolean validateChannel(MessageChannel channel) {
        return true;
    }

    @Override
    public boolean adminOnly() {
        return false;
    }

    @Override
    public Supplier<String> help() {
        return () -> Strings.getString("INVENTORY_COMMAND_HELP");
    }
}

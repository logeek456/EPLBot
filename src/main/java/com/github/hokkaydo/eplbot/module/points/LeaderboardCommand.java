package com.github.hokkaydo.eplbot.module.points;

import com.github.hokkaydo.eplbot.Strings;
import com.github.hokkaydo.eplbot.command.Command;
import com.github.hokkaydo.eplbot.command.CommandContext;
import com.github.hokkaydo.eplbot.module.points.model.Points;
import net.dv8tion.jda.api.entities.channel.middleman.MessageChannel;
import net.dv8tion.jda.api.interactions.commands.OptionType;
import net.dv8tion.jda.api.interactions.commands.build.OptionData;

import java.util.List;
import java.util.function.Supplier;

public class LeaderboardCommand implements Command {


        private final PointsProcessor processor;

        public LeaderboardCommand(PointsProcessor processor) {
            this.processor = processor;
        }
        @Override
        public void executeCommand(CommandContext context) {
            if (context.interaction().getGuild() == null) return;
            this.processor.activateAuthor(context.author());
            List<Points> leaderboard;
            boolean role = false;
            if (!context.options().isEmpty() && context.options().getFirst().getAsBoolean()) {
                leaderboard = this.processor.getRoleLB();
                role = true;
            }
            else {
                leaderboard = this.processor.getLeaderboard();
            }
            StringBuilder sb = new StringBuilder();
            if (role) sb.append("## Classement des guildes :\n\n");
            else sb.append("### Classement des joueurs :\n\n");
            for (int i = 0; i < Math.min(leaderboard.size(),10); i++) {
                Points point = leaderboard.get(i);
                if (role) sb.append(i + 1).append(". ").append(point.username().substring(5)).append(" - ").append(point.points()).append("\n");
                else sb.append(i + 1).append(". ").append(point.username()).append(" - ").append(point.points()).append(" - *").append(point.role()).append("*\n");
            }
            context.replyCallbackAction().setContent(sb.toString()).queue();
        }



        @Override
        public String getName() {
            return "leaderboard";
        }

    @Override
    public Supplier<String> getDescription() {
        return () -> Strings.getString( "LEADERBOARD_COMMAND_DESCRIPTION");
    }

    @Override
    public List<OptionData> getOptions() {

            return List.of(
                    new OptionData(OptionType.BOOLEAN,"role",Strings.getString("LEADERBOARD_COMMAND_OPTION_ROLE_DESCRIPTION"),false)
            );
    }

    @Override
        public boolean ephemeralReply() {
            return false;
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
        return () -> Strings.getString("LEADERBOARD_COMMAND_HELP");
    }




}

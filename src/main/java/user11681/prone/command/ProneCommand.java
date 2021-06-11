package user11681.prone.command;

import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.builder.RequiredArgumentBuilder;
import com.mojang.brigadier.context.CommandContext;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import com.mojang.brigadier.exceptions.SimpleCommandExceptionType;
import java.util.Collection;
import net.minecraft.entity.Entity;
import net.minecraft.server.command.ServerCommandSource;
import net.minecraft.text.TranslatableText;
import user11681.prone.asm.access.ProneEntity;

import static com.mojang.brigadier.arguments.BoolArgumentType.bool;
import static com.mojang.brigadier.arguments.BoolArgumentType.getBool;
import static net.minecraft.command.argument.EntityArgumentType.entities;
import static net.minecraft.command.argument.EntityArgumentType.getEntities;
import static net.minecraft.server.command.CommandManager.argument;
import static net.minecraft.server.command.CommandManager.literal;

public class ProneCommand {
    public static final SimpleCommandExceptionType NO_PLAYER_EXCEPTION = new SimpleCommandExceptionType(new TranslatableText("command.prone.no_player"));

    public static void register(final CommandDispatcher<ServerCommandSource> dispatcher, final boolean dedicated) {
        final RequiredArgumentBuilder<ServerCommandSource, Boolean> flag = argument("flag", bool());

        dispatcher.register(
                literal("prone")
                        .executes((final CommandContext<ServerCommandSource> source) -> execute(source.getSource().getPlayer()))
                        .then(flag
                                .executes((final CommandContext<ServerCommandSource> context) -> execute(context.getSource().getPlayer(), getBool(context, "flag"))))
        );
        dispatcher.register(
                literal("prone").requires((final ServerCommandSource source) -> source.hasPermissionLevel(1))
                        .then(argument("entities", entities())
                                .executes((final CommandContext<ServerCommandSource> context) -> execute(getEntities(context, "entities")))
                                .then(flag
                                        .executes((final CommandContext<ServerCommandSource> context) -> execute(getEntities(context, "entities"), getBool(context, "flag")))))
        );
    }

    public static int execute(final Collection<? extends Entity> entities) throws CommandSyntaxException {
        for (final Entity entity : entities) {
            execute(entity);
        }

        return entities.size();
    }

    public static int execute(final Collection<? extends Entity> entities, final boolean prone) throws CommandSyntaxException {
        for (final Entity entity : entities) {
            execute(entity, prone);
        }

        return entities.size();
    }

    public static int execute(final Entity entity) throws CommandSyntaxException {
        checkPlayer(entity);

        final ProneEntity proneEntity = (ProneEntity) entity;

        proneEntity.prone_toggleProne();
        proneEntity.prone_sync();

        return 1;
    }

    public static int execute(final Entity entity, final boolean prone) throws CommandSyntaxException {
        checkPlayer(entity);

        final ProneEntity proneEntity = (ProneEntity) entity;

        proneEntity.prone_setProne(prone);
        proneEntity.prone_sync();

        return 1;
    }

    private static void checkPlayer(final Entity entity) throws CommandSyntaxException {
        if (entity == null) {
            throw NO_PLAYER_EXCEPTION.create();
        }
    }
}

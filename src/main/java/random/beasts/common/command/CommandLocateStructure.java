package random.beasts.common.command;

import net.minecraft.command.CommandBase;
import net.minecraft.command.CommandException;
import net.minecraft.command.ICommandSender;
import net.minecraft.command.WrongUsageException;
import net.minecraft.server.MinecraftServer;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.text.TextComponentTranslation;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.Collections;
import java.util.List;

public class CommandLocateStructure extends CommandBase {

    @Nonnull
    public String getName() {
        return "locate";
    }

    public int getRequiredPermissionLevel() {
        return 2;
    }

    @Nonnull
    public String getUsage(@Nonnull ICommandSender sender) {
        return "/locateVillage";
    }

    public void execute(@Nonnull MinecraftServer server, @Nonnull ICommandSender sender, String[] args) throws CommandException {
        if (args.length != 1) {
            throw new WrongUsageException("commands.locate.usage");
        } else {
            String s = args[0];
            BlockPos blockpos = sender.getEntityWorld().findNearestStructure(s, sender.getPosition(), false);
            if (blockpos != null) {
                sender.sendMessage(new TextComponentTranslation("commands.locate.success", s, blockpos.getX(), blockpos.getZ()));
            } else {
                throw new CommandException("commands.locate.failure", s);
            }
        }
    }

    @Nonnull
    public List<String> getTabCompletions(MinecraftServer server, ICommandSender sender, String[] args, @Nullable BlockPos targetPos) {
        return args.length == 1 ? getListOfStringsMatchingLastWord(args, "Stronghold", "Monument", "Village", "Mansion", "EndCity", "Fortress", "Temple", "Mineshaft") : Collections.emptyList();
    }
}

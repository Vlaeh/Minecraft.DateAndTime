package vlaeh.minecraft.forge.dateandtime.client;

import net.minecraft.util.text.StringTextComponent;
import net.minecraftforge.client.event.ClientChatReceivedEvent;
import net.minecraftforge.client.event.ClientPlayerNetworkEvent.LoggedInEvent;
import net.minecraftforge.client.event.ClientPlayerNetworkEvent.LoggedOutEvent;
import net.minecraftforge.event.world.WorldEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import vlaeh.minecraft.forge.dateandtime.DateAndTime;
import vlaeh.minecraft.forge.dateandtime.DateAndTimeConfig;

public class DateAndTimeClientSide {

    @SubscribeEvent
    public void chatmessage(final ClientChatReceivedEvent event) {
        if (DateAndTimeConfig.printTimestamp) {
            StringTextComponent message = new StringTextComponent(DateAndTimeClientThread.getTime());
            message.append(event.getMessage());
            event.setMessage(message);
        }
    }

    @SubscribeEvent
    public void clientConnected(LoggedInEvent event) {
        DateAndTime.LOGGER.info("Logged in " + event.getPlayer());
    }

    @SubscribeEvent
    public void clientDisconnected(LoggedOutEvent event) {
        DateAndTime.LOGGER.info("Logged out " + event.getPlayer());
    }

    int world_count = 0;

    @SubscribeEvent
    public void worldLoad(WorldEvent.Load event) {
        if (world_count++ == 0)
            DateAndTimeClientThread.load();
        DateAndTime.LOGGER.info("World load " + world_count);
    }

    @SubscribeEvent
    public void worldUnload(WorldEvent.Unload event) {
        if (--world_count == 0)
            DateAndTimeClientThread.unload();
        DateAndTime.LOGGER.info("World unload " + world_count);
    }

}

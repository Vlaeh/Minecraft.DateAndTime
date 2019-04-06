package vlaeh.minecraft.forge.dateandtime.client;

import net.minecraft.util.text.TextComponentString;
import net.minecraftforge.client.event.ClientChatReceivedEvent;
import net.minecraftforge.event.world.WorldEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import vlaeh.minecraft.forge.dateandtime.DateAndTime;

public class DateAndTimeClientProxy {

    @SubscribeEvent
    public void chatmessage(final ClientChatReceivedEvent event) {
        if (DateAndTime.printTimestamp) {
            TextComponentString message = new TextComponentString(DateAndTimeThread.getTime());
            message.appendSibling(event.getMessage());
            event.setMessage(message);
        }
    }

/* TODO 1.13
    @SubscribeEvent
    public void clientConnected(ClientConnectedToServerEvent event) {
        DateAndTimeThread.load();
    }

    @SubscribeEvent
    public void clientDisconnected(ClientDisconnectionFromServerEvent event) {
        DateAndTimeThread.unload();
    }

    @SubscribeEvent
    public void onConfigChanged(ConfigChangedEvent.OnConfigChangedEvent eventArgs) {
        if (eventArgs.getModID().equals(DateAndTime.MODID))
            DateAndTime.syncConfig();
    }
*/

    int world_count = 0;

    @SubscribeEvent
    public void worldLoad(WorldEvent.Load event) {
        if (world_count++ == 0)
            DateAndTimeThread.load();
    }

    @SubscribeEvent
    public void worldUnload(WorldEvent.Unload event) {
        if (--world_count == 0)
            DateAndTimeThread.unload();
    }

}

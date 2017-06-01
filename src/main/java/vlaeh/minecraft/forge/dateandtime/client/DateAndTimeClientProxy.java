package vlaeh.minecraft.forge.dateandtime.client;

import net.minecraft.util.text.TextComponentString;
import net.minecraftforge.client.event.ClientChatReceivedEvent;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.client.event.ConfigChangedEvent;
import net.minecraftforge.fml.common.Mod.EventHandler;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.network.FMLNetworkEvent.ClientConnectedToServerEvent;
import net.minecraftforge.fml.common.network.FMLNetworkEvent.ClientDisconnectionFromServerEvent;

import vlaeh.minecraft.forge.dateandtime.DateAndTime;

public class DateAndTimeClientProxy {
	
    @EventHandler
    public void postInit(FMLPostInitializationEvent event)
    {
    	MinecraftForge.EVENT_BUS.register(this);
    }

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
    	if(eventArgs.getModID().equals(DateAndTime.MODID))
    		DateAndTime.syncConfig();
    }

    @SubscribeEvent
    public void chatmessage(final ClientChatReceivedEvent event) {
    	if (DateAndTime.printTimestamp) {
    		TextComponentString message = new TextComponentString(DateAndTimeThread.getTime());
    		message.appendSibling(event.getMessage());
    		event.setMessage(message);
    	}
    }

}

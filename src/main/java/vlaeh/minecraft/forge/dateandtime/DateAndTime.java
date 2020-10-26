package vlaeh.minecraft.forge.dateandtime;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.ModLoadingContext;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.config.ModConfig;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import vlaeh.minecraft.forge.dateandtime.client.DateAndTimeClientSide;

@Mod(DateAndTime.MODID)
// TODO 1.13 guiFactory = "vlaeh.minecraft.forge.dateandtime.DateAndTimeGUIFactory")
public class DateAndTime {
    public static final String MODID = "dateandtime";
    public static final Logger LOGGER = LogManager.getLogger();
    public static long threadCheckFrequency = 3000; // milliseconds

    public DateAndTime() {
        LOGGER.debug("Creating Date And Time mod");
        ModLoadingContext.get().registerConfig(ModConfig.Type.CLIENT, DateAndTimeConfig.clientSpec);
        FMLJavaModLoadingContext.get().getModEventBus().register(this);
    }

    @SubscribeEvent
    public void doClientStuff(final FMLClientSetupEvent event) {
        LOGGER.debug("Registering Date And Time client mod");
        MinecraftForge.EVENT_BUS.register(new DateAndTimeClientSide());
   }

    @SubscribeEvent
    public void onLoad(final ModConfig.Loading configEvent) {
        final ModConfig config = configEvent.getConfig();
        DateAndTime.LOGGER.debug("Loading configuration {}", config);
        DateAndTimeConfig.instance.updateConfig(config.getConfigData());
    }

    @SubscribeEvent
    public void onFileChange(final ModConfig.Reloading configEvent) {
        final ModConfig config = configEvent.getConfig();
        DateAndTime.LOGGER.debug("file changed {}", config);
        DateAndTimeConfig.instance.updateConfig(config.getConfigData());
    }

}

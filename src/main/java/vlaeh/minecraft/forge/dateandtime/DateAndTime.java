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
import vlaeh.minecraft.forge.dateandtime.client.DateAndTimeClientProxy;

@Mod(DateAndTime.MODID)
// TODO 1.13 guiFactory = "vlaeh.minecraft.forge.dateandtime.DateAndTimeGUIFactory")
public class DateAndTime {
    public static final String MODID = "dayandtime";
    public static final Logger LOGGER = LogManager.getLogger();
    public static boolean printTimestamp = true;
    public static boolean printDayPhases = true;
    public static boolean printMoonPhases = true;
    public static long threadCheckFrequency = 3000; // milliseconds


    public DateAndTime() {
        FMLJavaModLoadingContext.get().getModEventBus().addListener(this::doClientStuff);
        ModLoadingContext.get().registerConfig(ModConfig.Type.CLIENT, DateAndTimeConfig.clientSpec);
        FMLJavaModLoadingContext.get().getModEventBus().register(this);
    }

    @SubscribeEvent
    public void doClientStuff(final FMLClientSetupEvent event) {
        LOGGER.info("Registering Date And Time client mod");
        MinecraftForge.EVENT_BUS.register(new DateAndTimeClientProxy());
   }

    @SubscribeEvent
    public void onLoad(final ModConfig.Loading configEvent) {
        syncConfig(configEvent.getConfig());
    }

    @SubscribeEvent
    public void onFileChange(final ModConfig.ConfigReloading configEvent) {
        syncConfig(configEvent.getConfig());
    }

    private void syncConfig(final ModConfig config) {
        LOGGER.debug("Loading configuration from {}", config.getFileName());
        config.getConfigData().valueMap().forEach((key, value) -> {LOGGER.info("-- {} = {}", key, value);} );
        printTimestamp = config.getConfigData().get(DateAndTimeConfig.instance.timestamp.getPath());
        printDayPhases = config.getConfigData().get(DateAndTimeConfig.instance.dayPhases.getPath());
        printMoonPhases = config.getConfigData().get(DateAndTimeConfig.instance.moonPhases.getPath());
    }

}

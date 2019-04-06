package vlaeh.minecraft.forge.dateandtime;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.common.Mod;
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

    public DateAndTime() {
        FMLJavaModLoadingContext.get().getModEventBus().addListener(this::doClientStuff);
    }

    private void doClientStuff(final FMLClientSetupEvent event) {
        LOGGER.info("Registering Date And Time mod");
        MinecraftForge.EVENT_BUS.register(new DateAndTimeClientProxy());
    }

/* TODO 1.13
    public static Configuration config;

    @EventHandler
    public void preInit(FMLPreInitializationEvent event) {
        config = new Configuration(event.getSuggestedConfigurationFile());
        syncConfig();
    }

    @EventHandler
    public void postInit(FMLPostInitializationEvent event) {
        proxy.postInit(event);
    }

    public static void syncConfig() {
        printTimestamp = config.getBoolean("1.printTimestamp", Configuration.CATEGORY_GENERAL, printTimestamp,
                "dateandtime.conf.timestamp.tooltip", "dateandtime.conf.timestamp");
        printDayPhases = config.getBoolean("2.printDayPhases", Configuration.CATEGORY_GENERAL, printDayPhases,
                "dateandtime.conf.dayphases.tooltip", "dateandtime.conf.dayphases");
        printMoonPhases = config.getBoolean("3.printMoonPhases", Configuration.CATEGORY_GENERAL, printMoonPhases,
                "dateandtime.conf.moonphases.tooltip", "dateandtime.conf.moonphases");
        if (config.hasChanged())
            config.save();
    }
*/

}

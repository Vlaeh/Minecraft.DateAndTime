package vlaeh.minecraft.forge.dateandtime;

import net.minecraftforge.common.config.Configuration;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.Mod.EventHandler;
import net.minecraftforge.fml.common.SidedProxy;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;

import vlaeh.minecraft.forge.dateandtime.client.DateAndTimeClientProxy;

@Mod(modid = DateAndTime.MODID,
     version = DateAndTime.VERSION, 
     name = DateAndTime.NAME, 
     clientSideOnly = true, 
     acceptableRemoteVersions = "*", 
     acceptedMinecraftVersions = "[1.9,1.13)",
     guiFactory = "vlaeh.minecraft.forge.dateandtime.DateAndTimeGUIFactory")
public class DateAndTime {
	public static final String MODID = "dayandtime";
	public static final String VERSION = "1.2";
	public static final String NAME = "Day & Time";

    public static Configuration config;
    public static boolean printTimestamp = true;
    public static boolean printDayPhases = true;
    public static boolean printMoonPhases = true;

    @SidedProxy(clientSide = "vlaeh.minecraft.forge.dateandtime.client.DateAndTimeClientProxy")
	public static DateAndTimeClientProxy proxy;

	@Mod.Instance
	public static DateAndTime instance;

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
		printTimestamp = config.getBoolean("1.printTimestamp", Configuration.CATEGORY_GENERAL, printTimestamp, "dateandtime.conf.timestamp.tooltip", "dateandtime.conf.timestamp");
		printDayPhases = config.getBoolean("2.printDayPhases", Configuration.CATEGORY_GENERAL, printDayPhases, "dateandtime.conf.dayphases.tooltip", "dateandtime.conf.dayphases");
		printMoonPhases = config.getBoolean("3.printMoonPhases", Configuration.CATEGORY_GENERAL, printMoonPhases, "dateandtime.conf.moonphases.tooltip", "dateandtime.conf.moonphases");
	    if(config.hasChanged())
	      config.save();
	}

}

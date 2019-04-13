package vlaeh.minecraft.forge.dateandtime;

import org.apache.commons.lang3.tuple.Pair;

import com.electronwill.nightconfig.core.CommentedConfig;
import com.electronwill.nightconfig.core.file.CommentedFileConfig;

import net.minecraftforge.common.ForgeConfigSpec;
import net.minecraftforge.common.ForgeConfigSpec.BooleanValue;

public class DateAndTimeConfig {
    public static boolean printTimestamp = true;
    public static boolean printDayPhases = true;
    public static boolean printMoonPhases = true;

    public static final DateAndTimeConfig instance;
    public static final ForgeConfigSpec clientSpec;

    private final BooleanValue timestamp_c;
    private final BooleanValue dayPhases_c;
    private final BooleanValue moonPhases_c;

    static {
        final Pair<DateAndTimeConfig, ForgeConfigSpec> specPair = new ForgeConfigSpec.Builder().configure(DateAndTimeConfig::new);
        clientSpec = specPair.getRight();
        instance = specPair.getLeft();
    }

    DateAndTimeConfig(ForgeConfigSpec.Builder builder) {
        timestamp_c = builder
            .comment("Add local time to chat messages received.")
            .translation("dateandtime.conf.timestamp.tooltip")
            .define("timestamp", printTimestamp);

        dayPhases_c = builder
            .comment("Add chat message indicating current phase of the day.")
            .translation("dateandtime.conf.dayphases.tooltip")
            .define("dayPhases", printDayPhases);

        moonPhases_c = builder
            .comment("Add current moon phase to day phase indication and tooltip.")
            .translation("dateandtime.conf.moonphases.tooltip")
            .define("moonPhases", printMoonPhases);
    }

    public void updateConfig(final CommentedConfig config) {
        if (config instanceof CommentedFileConfig)
            ((CommentedFileConfig) config).load();
        printTimestamp = timestamp_c.get();
        printDayPhases = dayPhases_c.get();
        printMoonPhases = moonPhases_c.get();
    }

}
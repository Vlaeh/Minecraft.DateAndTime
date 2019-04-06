package vlaeh.minecraft.forge.dateandtime;

import org.apache.commons.lang3.tuple.Pair;

import net.minecraftforge.common.ForgeConfigSpec;
import net.minecraftforge.common.ForgeConfigSpec.BooleanValue;

public class DateAndTimeConfig {
    public final BooleanValue timestamp;
    public final BooleanValue dayPhases;
    public final BooleanValue moonPhases;

    public static final DateAndTimeConfig instance;
    public static final ForgeConfigSpec clientSpec;

    static {
        final Pair<DateAndTimeConfig, ForgeConfigSpec> specPair = new ForgeConfigSpec.Builder().configure(DateAndTimeConfig::new);
        clientSpec = specPair.getRight();
        instance = specPair.getLeft();
    }

    DateAndTimeConfig(ForgeConfigSpec.Builder builder) {
        timestamp = builder
            .comment("Add local time to chat messages received.")
            .translation("dateandtime.conf.timestamp.tooltip")
            .define("timestamp", true);

        dayPhases = builder
            .comment("Add chat message indicating current phase of the day.")
            .translation("dateandtime.conf.dayphases.tooltip")
            .define("dayPhases", true);

        moonPhases = builder
            .comment("Add current moon phase to day phase indication and tooltip.")
            .translation("dateandtime.conf.moonphases.tooltip")
            .define("moonPhases", true);
    }
}
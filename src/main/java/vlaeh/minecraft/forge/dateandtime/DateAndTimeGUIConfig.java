package vlaeh.minecraft.forge.dateandtime;

import net.minecraft.client.gui.GuiScreen;
import net.minecraftforge.common.config.ConfigElement;
import net.minecraftforge.common.config.Configuration;
import net.minecraftforge.fml.client.config.GuiConfig;

public class DateAndTimeGUIConfig extends GuiConfig {

    public DateAndTimeGUIConfig(GuiScreen parent) {
        super(parent,
                new ConfigElement(DateAndTime.config.getCategory(Configuration.CATEGORY_GENERAL)).getChildElements(),
                DateAndTime.MODID, false, false, GuiConfig.getAbridgedConfigPath(DateAndTime.config.toString()));
    }
}

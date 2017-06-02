package vlaeh.minecraft.forge.dateandtime.client;

import java.util.Calendar;

import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraft.client.multiplayer.WorldClient;
import net.minecraft.client.resources.I18n;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TextComponentString;

import vlaeh.minecraft.forge.dateandtime.DateAndTime;

/**
 * 
 *            0 sunrise & wakeup
 * 50s     1000 day 
 * 5m      6000 noon
 * 10m    12000 sunset
 * 10m25s 12550 go to bed
 * 11m40s 14000 night
 * 15m    18000 midnight
 * 20m    24000 = 0
 */

public final class DateAndTimeThread extends Thread {
	private final static String[] dayPhases = {  //
			"dateandtime.dayphase.morning",   // 0
			"dateandtime.dayphase.noon",      // 5900
			"dateandtime.dayphase.afternoon", // 6500
			"dateandtime.dayphase.sunset",    // 12000
			"dateandtime.dayphase.night",     // 13000
			"dateandtime.dayphase.midnight",  // 18000
			"dateandtime.dayphase.sunrise"    // 23000
	};

	private final static String[][] moonPhases = {
		{ "dateandtime.moonphase.fullmoon", "\u25CF", "\ud83c\udf15"},
		{ "dateandtime.moonphase.waninggibbous", "\u263E", "\ud83c\udf16"},
		{ "dateandtime.moonphase.lastquarter", "\u263E", "\ud83c\udf17"},
		{ "dateandtime.moonphase.waningcrescent", "\u263E", "\ud83c\udf18"},
		{ "dateandtime.moonphase.newmoon", "\u25CB", "\ud83c\udf11"},
		{ "dateandtime.moonphase.waxingcrescent", "\u263D", "\ud83c\udf12"},
		{ "dateandtime.moonphase.firstquarter", "\u263D", "\ud83c\udf13"},
		{ "dateandtime.moonphase.waxinggibbous", "\u263D", "\ud83c\udf14"}
	};
	
	private final static Calendar cal = Calendar.getInstance();
	private static DateAndTimeThread instance = null; 
	
	public static void load() {
		final DateAndTimeThread d = instance = new DateAndTimeThread();
		d.start();
	}
	
	public static void unload() {
		instance = null;
	}
	
	private int lastPhase = -1;
	private long lastCheck = 0;
	
	public DateAndTimeThread() {
		super();
        setName("DateAndTimeThread");
        setDaemon(true);
	}
	
	private final void schedule(final WorldClient world, final EntityPlayerSP player) {
		final long totalTime = world.getWorldTime();
		if (totalTime == 0)
			return; // not yet initialized
		final long time = totalTime % 24000L;
		final int phase;
		if (time >= 23000)
			phase = 6;
		else if ((time >= 18000) && (lastPhase != -1)) // midnight
			phase = 5;
		else if (time >= 13000)
			phase = 4;
		else if (time >= 12000)
			phase = 3;
		else if (time >= 6500)
			phase = 2;
		else if (time >= 5900)
			phase = 1;
		else 
			phase = 0;
        if ((lastPhase != phase) || (totalTime - lastCheck >= 24000)) {
        	lastPhase = phase;
        	lastCheck = totalTime;
        	final String tooltip;
        	final String message;
        	if (DateAndTime.printMoonPhases) {
            	final int moonPhase = (int)((totalTime / 24000L) % 8L);
        		tooltip =  I18n.format("dateandtime.moonphase.tooltip") + " " + moonPhases[moonPhase][1] + " " + I18n.format(moonPhases[moonPhase][0]);
        		message = "§f" + moonPhases[moonPhase][1] + "§r " + I18n.format(dayPhases[phase]) + " " ;
        	} else {
        		tooltip = null;
        		message = I18n.format(dayPhases[phase]);
        	}
        	sendMessageToPlayer(message, tooltip, player);
        }
	}

	@Override
	public void run() {
		while (instance == this) {
			final long sleepTime = 3000;
			if (DateAndTime.printDayPhases) {
				try {
					final Minecraft minecraft = Minecraft.getMinecraft();
					if (minecraft != null) {
						final WorldClient world = minecraft.world;
						final EntityPlayerSP player = minecraft.player;
						if ((world != null) && (player != null))
							try {
								schedule(world, player);
							} catch (final Throwable t) {
								t.printStackTrace();
							}
					}
				} catch (final Throwable e) {
					System.out.println("DateAndTimeThread loop error " + e);
				}
			}
			try {
				Thread.sleep(sleepTime);
			} catch (final Exception e) {
				e.printStackTrace();
			}
		}
	}

	private static final void sendMessageToPlayer(final String message, final String tooltip, final EntityPlayerSP player) {
    	try {
    		if (player == null)
    			return;
        	Minecraft.getMinecraft().addScheduledTask(new Runnable() {
				@Override
				public void run() {
					final ITextComponent component;
					if (tooltip != null) 
						component = ITextComponent.Serializer.jsonToComponent("{\"text\":\"" + getTime() + "\", \"extra\":[{\"text\":\"" + message + "\",\"hoverEvent\":{\"action\":\"show_text\", \"value\":\"" + tooltip + "\"}}]}");
					else 
						component = new TextComponentString(message);
		            player.sendMessage(component);
				}
        	});
    	} catch (Throwable t) {}
    }
    
    public static final String getTime() {
    	if (! DateAndTime.printTimestamp)
    		return "";
    	cal.setTimeInMillis(System.currentTimeMillis());
        final int hour = cal.get(Calendar.HOUR_OF_DAY); 
        final int minute = cal.get(Calendar.MINUTE);
        final int second = cal.get(Calendar.SECOND);
        final StringBuilder b = new StringBuilder("[");
        b.append(hour / 10);
        b.append(hour % 10);
        b.append(" : ");
        b.append(minute / 10);
        b.append(minute % 10);
        b.append(" : ");
        b.append(second / 10);
        b.append(second % 10);
        b.append("]  ");
        return b.toString();
    }


}
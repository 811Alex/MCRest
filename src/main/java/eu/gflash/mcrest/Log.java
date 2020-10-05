package eu.gflash.mcrest;

import org.bukkit.Bukkit;

public class Log{
    public static void info(String str){
        Bukkit.getLogger().info("[§dMCRest§r] " + str);
    }
}
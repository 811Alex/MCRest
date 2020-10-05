package eu.gflash.mcrest.endpointHandlers;

import org.bukkit.Bukkit;
import org.bukkit.World;
import org.bukkit.block.Biome;

import java.util.Map;

public class BiomeHandler {
    public static String handle(Map<String, String> params) {
        World world = Bukkit.getWorld(params.get("world") != null ? params.get("world") : "world");
        if(world == null) return "World not found!";
        int x = params.get("x") != null ? Integer.parseInt(params.get("x")) : 0;
        int y = params.get("y") != null ? Integer.parseInt(params.get("y")) : 64;
        int z = params.get("z") != null ? Integer.parseInt(params.get("z")) : 0;
        Biome biome = world.getBiome(x, y, z);
        return biome.name();
    }
}

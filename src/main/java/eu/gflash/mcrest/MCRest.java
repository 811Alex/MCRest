package eu.gflash.mcrest;

import eu.gflash.mcrest.endpointHandlers.BiomeHandler;
import eu.gflash.mcrest.endpointHandlers.PermHandler;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.plugin.java.JavaPlugin;

public final class MCRest extends JavaPlugin {
    public static FileConfiguration config;

    @Override
    public void onEnable() {
        loadConfig();
        Server s = new Server(config.getInt("port"));
        s.addEndpoint("biome", BiomeHandler::handle);
        s.addEndpoint("perms", PermHandler::handle);
    }

    @Override
    public void onDisable() {
        Server.killAll();
    }

    private void loadConfig(){
        config = this.getConfig();
        config.addDefault("port", 25600);
        config.options().copyDefaults(true);
        saveConfig();
    }
}

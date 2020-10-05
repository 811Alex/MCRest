package eu.gflash.mcrest.endpointHandlers;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.permissions.Permission;
import org.bukkit.permissions.PermissionDefault;

import java.util.Map;

public class PermHandler {
    public static String handle(Map<String, String> params) {
        if(params.get("request") == null) return "Provide a request!";
        switch (params.get("request")){
            case "hasgroup":
                if(params.get("player") == null) return "Provide a player!";
                if(params.get("group") == null) return "Provide a group!";
                if(Bukkit.getPlayer(params.get("player")) == null) return "User not found or offline!";
                return hasPermission(params.get("player"), "group." + params.get("group")) ? "true" : "false";
            case "hasperm":
                if(params.get("player") == null) return "Provide a player!";
                if(params.get("node") == null) return "Provide a permission node!";
                if(Bukkit.getPlayer(params.get("player")) == null) return "User not found or offline!";
                return hasPermission(params.get("player"), params.get("node")) ? "true" : "false";
            default:
                return "Unknown request!";
        }
    }

    private static Boolean hasPermission(String player, String node){
        Player ply = Bukkit.getPlayer(player);
        Permission perm = new Permission(node, PermissionDefault.FALSE);
        if(ply == null) return false;
        return ply.hasPermission(perm);
    }
}

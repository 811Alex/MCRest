package eu.gflash.mcrest;

import com.sun.net.httpserver.HttpServer;

import java.io.IOException;
import java.io.OutputStream;
import java.net.InetSocketAddress;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

public class Server {
    private static Map<Integer, Server> servers = new HashMap<>();
    private static List<String> portEndpoints = new ArrayList<>();
    private HttpServer server;
    private int port;

    public Server(int port) {
        this.port = port;
        try {
            if(servers.containsKey(port)){
                Log.info("Already running on: " + port);
                return;
            }
            servers.put(port, this);
            server = HttpServer.create(new InetSocketAddress(port), 0);
            server.setExecutor(null); // creates a default executor
            server.start();
            Log.info("Started on port: " + port);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void addEndpoint(String endPoint, Function<Map<String, String>, String> handler){
        String ep = endPoint.startsWith("/") ? endPoint : "/" + endPoint;
        if(hasPortEndpoint(port, ep)){
            Log.info("Endpoint already exists (" + port + "): " + ep);
            return;
        }
        server.createContext(ep, (exchange -> {
            String query = exchange.getRequestURI().getQuery();
            String respText = handler.apply(queryToMap(query));
            exchange.sendResponseHeaders(200, respText.getBytes().length);
            OutputStream output = exchange.getResponseBody();
            output.write(respText.getBytes());
            output.flush();
            exchange.close();
        }));
        Server.addPortEndpoint(port, ep);
        Log.info("Added endpoint (" + port + "): " + ep);
    }

    public Map<String, String> queryToMap(String query) {
        Map<String, String> result = new HashMap<>();
        if(query != null && !query.isEmpty()){
            for (String param : query.split("&")) {
                String[] entry = param.split("=");
                result.put(entry[0], (entry.length > 1 ? entry[1] : ""));
            }
        }
        return result;
    }

    private static void addPortEndpoint(int port, String ep){
        portEndpoints.add(port + ":" + ep);
    }

    private static boolean hasPortEndpoint(int port, String ep){
        return portEndpoints.contains(port + ":" + ep);
    }

    public static Server get(int port){
        return servers.get(port);
    }

    public void kill(){
        Server.kill(port);
    }

    public static void kill(int port){
        if(get(port) == null){
            Log.info("Not running on port: " + port);
            return;
        }
        Log.info("Stopping on port: " + port);
        get(port).server.stop(1);
        List<String> toRemove = portEndpoints.stream()
                .filter(pep -> pep.startsWith(port + ":"))
                .collect(Collectors.toList());
        portEndpoints.removeAll(toRemove);
        servers.remove(port);
    }

    public static void killAll(){
        new ArrayList<>(servers.values()).forEach(Server::kill);
    }
}

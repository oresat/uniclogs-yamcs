package org.oresat.uniclogs;


import org.yamcs.*;
import org.yamcs.http.HttpServer;
import org.yamcs.logging.Log;

import java.io.File;
import java.nio.file.Path;

public class UniclogsServer extends HttpServer {
    private final static Log LOG = new Log(UniclogsServer.class);
    private final static YamcsServer server = YamcsServer.getServer();

    @Override
    public void init(String instanceName, String serviceName, YConfiguration config) throws InitException {
        // Call the parent server init
        super.init(instanceName, serviceName, config);
        LOG.info("Started " + serviceName + ":" + instanceName + " with config: " + config + " from: " + config.configDirectory);

        // Loac Config Plugins
        PluginManager plugins = server.getPluginManager();
        plugins.getPlugin(ConfigSpecLoader.class);

        // Load Custom Config Specs
        server.addConfigurationSection(ConfigScope.YAMCS_INSTANCE, "payloadConfig", new PayloadConfig());

        // Add listeners
        server.addReadyListener(new PrepareEnvironment());
    }

    public static final YamcsServer getServerInstance() {
        return server;
    }

    public static Path getCacheDir() {
        return new File(System.getProperty("user.home") + "/.cache/yamcs").toPath().toAbsolutePath();
    }

    public static Path getConfigDir() {
        return server.getConfigDirectory().toAbsolutePath();
    }

    public static Path getDataDir() {
        return server.getDataDirectory().toAbsolutePath();
    }

    public static void throwFatalException(String type, String message) {
        server.getGlobalCrashHandler().handleCrash(type, message);
        server.shutDown();
        System.exit(-1);
    }
}
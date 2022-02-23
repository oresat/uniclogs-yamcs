package org.oresat.uniclogs;

import org.yamcs.Plugin;
import org.yamcs.PluginException;
import org.yamcs.Spec;
import org.yamcs.YConfiguration;
import org.yamcs.logging.Log;

public class ConfigSpecLoader implements Plugin {
    final static Log LOG = new Log(ConfigSpecLoader.class);

    public Spec getSpec() {
        return new PayloadConfig();
    }

    @Override
    public void onLoad(YConfiguration config) throws PluginException {
        LOG.info("CONFIG: " + config.get("payloadConfig"));
    }
}
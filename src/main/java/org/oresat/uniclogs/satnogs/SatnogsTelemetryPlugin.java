package org.oresat.uniclogs.satnogs;

import com.fasterxml.jackson.databind.ObjectMapper;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import org.yamcs.AbstractYamcsService;
import org.yamcs.Spec;
import org.yamcs.YConfiguration;
import org.yamcs.logging.Log;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;

public class SatnogsTelemetryPlugin extends AbstractYamcsService {
    private final static Log LOG = new Log(SatnogsTelemetryPlugin.class);

    private OkHttpClient client = new OkHttpClient();
    private String apiUrl = "https://db.satnogs.org/api/{}?format=json";
    private String apiToken;
    private Integer apiPollInterval;
    private Integer noradID;

    private URL generateApiUrl(String endpoint) {
        try{
            return new URL(apiUrl.replace("{}", endpoint));
        } catch (MalformedURLException e) {
            LOG.error("Cannot form API Endpoint URL:" + e.getMessage());
            e.printStackTrace();
            return null;
        }
    }

    private Object callApi(String apiEndpoint, Class targetClass) {
        return  callApi(apiEndpoint, targetClass, null);
    }

    private Object callApi(String apiEndpoint, Class targetClass, String apiToken) {
        // Build the HTTP Request, enforce a response of JSON
        Request.Builder form = new Request.Builder()
                                          .url(apiEndpoint)
                                          .addHeader("accept", "application/json");
        if(apiToken != null) form = form.addHeader("Authorization", "Token " + apiToken);
        Request req = form.build();

        // Make the request
        try (Response res = client.newCall(req).execute()) {
            if(!res.isSuccessful()) {
                LOG.error("[HTTP " + res.code() + "] Request for " + apiEndpoint  + " failed with message: `" + res.priorResponse() + "`");
                return null;
            }

            String rawJson = res.body().string();
            ObjectMapper mapper = new ObjectMapper();
            return mapper.readValue(rawJson, targetClass);
        } catch (IOException e) {
            LOG.error("Failed to hit endpoint `" + apiEndpoint + "`: " + e.getMessage());
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public void init(String yamcsInstance, String serviceName, YConfiguration config) {
        this.yamcsInstance = yamcsInstance;
        this.serviceName = serviceName;
        this.config = config;

        // Load Plugin-specific configs
        if(config.containsKey("url")) {
            apiUrl = config.getString("url");
        }
        apiToken = config.getString("api-token");
        apiPollInterval = config.getInt("api-poll-interval");
        noradID = config.getInt("norad-id");
    }

    @Override
    protected void doStart() {
        // Create endpoint URL's
        URL satelliteEndpoint = generateApiUrl("satellites/" + noradID);
        URL telemetryEndpoint = generateApiUrl("telemetry/" + noradID);
        if(satelliteEndpoint == null) return;
        if(telemetryEndpoint == null) return;

        // Fetch satellite metadata
        SatelliteMetaResponse satellite = (SatelliteMetaResponse) callApi(satelliteEndpoint.toString(), SatelliteMetaResponse.class);
        LOG.info("Got metadata for " + satellite.name() + ": " + satellite);

        // Fetch satellite telemetry
        TelemetryResponse telemetry = (TelemetryResponse) callApi(telemetryEndpoint.toString(), TelemetryResponse.class, apiToken);
        LOG.info("Got telemetry for " + satellite.name() + ": " + telemetry);

    }

    @Override
    protected void doStop() {
    }

    @Override
    public Spec getSpec() {
        return super.getSpec();
    }
}
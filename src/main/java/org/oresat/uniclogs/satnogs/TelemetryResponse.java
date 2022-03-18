package org.oresat.uniclogs.satnogs;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonIgnoreProperties({"details", "associated_satellites"})
public record TelemetryResponse(String satId,
                                Integer noradCatId,
                                String transmitter,
                                String appSource,
                                String schema,
                                String decoded,
                                String frame,
                                String observer,
                                String timestamp,
                                String version,
                                Integer observationId,
                                Integer stationId) {
    public TelemetryResponse(@JsonProperty("sat_id") String satId,
                             @JsonProperty("norad_cat_id") Integer noradCatId,
                             @JsonProperty("transmitter") String transmitter,
                             @JsonProperty("app_source") String appSource,
                             @JsonProperty("schema") String schema,
                             @JsonProperty("decoded") String decoded,
                             @JsonProperty("frame") String frame,
                             @JsonProperty("observer") String observer,
                             @JsonProperty("timestamp") String timestamp,
                             @JsonProperty("version") String version,
                             @JsonProperty("observation_id") Integer observationId,
                             @JsonProperty("station_id") Integer stationId) {
        this.satId = satId;
        this.noradCatId = noradCatId;
        this.transmitter = transmitter;
        this.appSource = appSource;
        this.schema = schema;
        this.decoded = decoded;
        this.frame = frame;
        this.observer = observer;
        this.timestamp = timestamp;
        this.version = version;
        this.observationId = observationId;
        this.stationId = stationId;
    }
}

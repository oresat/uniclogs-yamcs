package org.oresat.uniclogs.satnogs;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonIgnoreProperties({"details", "telemetries", "citation", "associated_satellites"})
public record SatelliteMetaResponse(String satId,
                                    Integer noradCatId,
                                    String name,
                                    String names,
                                    String image,
                                    String status,
                                    String decayed,
                                    String launched,
                                    String deployed,
                                    String website,
                                    String operator,
                                    String countries,
                                    String updated) {

    public SatelliteMetaResponse(@JsonProperty("sat_id") String satId,
                                 @JsonProperty("norad_cat_id") Integer noradCatId,
                                 @JsonProperty("name") String name,
                                 @JsonProperty("names") String names,
                                 @JsonProperty("image") String image,
                                 @JsonProperty("status") String status,
                                 @JsonProperty("decayed") String decayed,
                                 @JsonProperty("launched") String launched,
                                 @JsonProperty("deployed") String deployed,
                                 @JsonProperty("website") String website,
                                 @JsonProperty("operator") String operator,
                                 @JsonProperty("countries") String countries,
                                 @JsonProperty("updated") String updated) {
        this.satId = satId;
        this.noradCatId = noradCatId;
        this.name = name;
        this.names = names;
        this.image = image;
        this.status = status;
        this.decayed = decayed;
        this.launched = launched;
        this.deployed = deployed;
        this.website = website;
        this.operator = operator;
        this.countries = countries;
        this.updated = updated;
    }
}

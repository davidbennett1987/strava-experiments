package dev.dab.strava.geo;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;

@JsonDeserialize(using = LatLngDeserializer.class)
public class LatLng {
    private double latitude;
    private double longitude;

    public LatLng(Double latitude, Double longitude) {
        this.latitude = latitude;
        this.longitude = longitude;
    }

    public LatLng(String latLngString) {
        extractLatLng(latLngString);
    }

    private LatLng() {}

    private void extractLatLng(String latLngString) throws IllegalArgumentException {
        String[] latLngParts = latLngString.split(",");

        if (latLngParts.length != 2) {
            throw new IllegalArgumentException("Invalid latLng");
        }

        try {
            double latitude = Double.parseDouble(latLngParts[0]);
            double longitude = Double.parseDouble(latLngParts[1]);

            setLatitude(latitude);
            setLongitude(longitude);
        } catch (NumberFormatException | NullPointerException nfe) {
            throw new IllegalArgumentException("Invalid latLng");
        }
    }

    public double getLatitude() {
        return latitude;
    }

    private void setLatitude(double latitude) {
        this.latitude = latitude;
    }

    public double getLongitude() {
        return longitude;
    }

    private void setLongitude(double longitude) {
        this.longitude = longitude;
    }

    @Override
    public String toString() {
        return STR."\{latitude},\{longitude}";
    }
}

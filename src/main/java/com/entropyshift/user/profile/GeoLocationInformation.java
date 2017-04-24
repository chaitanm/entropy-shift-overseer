package com.entropyshift.user.profile;

import info.archinnov.achilles.annotations.Column;
import info.archinnov.achilles.annotations.UDT;

/**
 * Created by chaitanya.m on 2/20/17.
 */
@UDT(keyspace = "overseer",name = "geolocation")
public class GeoLocationInformation
{
    @Column("latitude")
    private Double latitude;

    @Column("longitude")
    private Double longitude;

    @Column("altitude")
    private Double altitude;

    @Column("accuracy")
    private Double accuracy;

    @Column("altitude_accuracy")
    private Double altitudeAccuracy;

    @Column("heading")
    private Double heading;

    @Column("speed")
    private Double speed;

    public Double getLatitude()
    {
        return latitude;
    }

    public void setLatitude(Double latitude)
    {
        this.latitude = latitude;
    }

    public Double getLongitude()
    {
        return longitude;
    }

    public void setLongitude(Double longitude)
    {
        this.longitude = longitude;
    }

    public Double getAltitude()
    {
        return altitude;
    }

    public void setAltitude(Double altitude)
    {
        this.altitude = altitude;
    }

    public Double getAccuracy()
    {
        return accuracy;
    }

    public void setAccuracy(Double accuracy)
    {
        this.accuracy = accuracy;
    }

    public Double getAltitudeAccuracy()
    {
        return altitudeAccuracy;
    }

    public void setAltitudeAccuracy(Double altitudeAccuracy)
    {
        this.altitudeAccuracy = altitudeAccuracy;
    }

    public Double getHeading()
    {
        return heading;
    }

    public void setHeading(Double heading)
    {
        this.heading = heading;
    }

    public Double getSpeed()
    {
        return speed;
    }

    public void setSpeed(Double speed)
    {
        this.speed = speed;
    }
}

package com.entropyshift.overseer.crypto.ellipticalcurve;

import com.entropyshift.overseer.crypto.key.AsymmetricKeyInformation;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by chaitanya.m on 1/12/17.
 */
public class EllipticalCurveKeyInformation extends AsymmetricKeyInformation
{
    private String id;
    private String x;
    private String y;
    private String d;
    private String curve;

    public String getId()
    {
        return id;
    }

    public void setId(String id)
    {
        this.id = id;
    }

    public String getX()
    {
        return x;
    }

    public void setX(String x)
    {
        this.x = x;
    }

    public String getY()
    {
        return y;
    }

    public void setY(String y)
    {
        this.y = y;
    }

    public String getD()
    {
        return d;
    }

    public void setD(String d)
    {
        this.d = d;
    }

    public String getCurve()
    {
        return curve;
    }

    public void setCurve(String curve)
    {
        this.curve = curve;
    }

    public Map<String, Object> toMap()
    {
        Map<String, Object> result = new HashMap<>();
        result.put("crv", this.getCurve());
        result.put("x", this.getX());
        result.put("y", this.getY());
        result.put("d", this.getD());
        return result;
    }
}

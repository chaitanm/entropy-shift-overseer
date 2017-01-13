package com.entropyshift.overseer.crypto.rsa;

import com.entropyshift.overseer.crypto.key.AsymmetricKeyInformation;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by chaitanya.m on 1/12/17.
 */
public class RsaKeyInformation extends AsymmetricKeyInformation
{
    private String id;
    private String n;
    private String e;
    private String d;
    private String p;
    private String q;
    private String dp;
    private String dq;

    public String getN()
    {
        return n;
    }

    public void setN(String n)
    {
        this.n = n;
    }

    public String getE()
    {
        return e;
    }

    public void setE(String e)
    {
        this.e = e;
    }

    public String getD()
    {
        return d;
    }

    public void setD(String d)
    {
        this.d = d;
    }

    public String getP()
    {
        return p;
    }

    public void setP(String p)
    {
        this.p = p;
    }

    public String getQ()
    {
        return q;
    }

    public void setQ(String q)
    {
        this.q = q;
    }

    public String getDp()
    {
        return dp;
    }

    public void setDp(String dp)
    {
        this.dp = dp;
    }

    public String getDq()
    {
        return dq;
    }

    public void setDq(String dq)
    {
        this.dq = dq;
    }

    public String getQi()
    {
        return qi;
    }

    public void setQi(String qi)
    {
        this.qi = qi;
    }

    private String qi;

    public String getId()
    {
        return id;
    }

    public void setId(String id)
    {
        this.id = id;
    }

    public Map<String,Object> toMap()
    {
        Map<String, Object> result = new HashMap<>();
        result.put("n", this.getN());
        result.put("e", this.getE());
        result.put("d", this.getD());
        result.put("p", this.getP());
        result.put("q", this.getQ());
        result.put("dp", this.getDp());
        result.put("dq", this.getDq());
        result.put("qi", this.getQi());
        return result;
    }
}

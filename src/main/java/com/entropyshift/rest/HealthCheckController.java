package com.entropyshift.rest;

import com.entropyshift.overseer.crypto.jwt.IJsonWebTokenProvider;
import com.entropyshift.overseer.crypto.key.KeyNotFoundException;
import org.jose4j.lang.JoseException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import java.time.Instant;
import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by chaitanya.m on 1/14/17.
 */
@Path("/health")
public class HealthCheckController
{
    @Autowired
    private ApplicationContext appContext;

    @GET
    @Path("/")
    public String get()
    {
        IJsonWebTokenProvider jsonWebTokenProvider = (IJsonWebTokenProvider) appContext.getBean("jsonWebTokenProvider");
        try
        {
            jsonWebTokenProvider.generateToken("xxx","xxx",new ArrayList<>(), Instant.now().toEpochMilli(),new HashMap<>());
        }
        catch (JoseException e)
        {
            e.printStackTrace();
        }
        catch (KeyNotFoundException e)
        {
            e.printStackTrace();
        }
        return "All is Well!";
    }
}

package com.entropyshift.rest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;

import javax.ws.rs.GET;
import javax.ws.rs.Path;

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
        return "All is Well!";
    }
}

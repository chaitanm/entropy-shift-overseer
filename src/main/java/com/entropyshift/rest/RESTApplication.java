package com.entropyshift.rest;

import com.entropyshift.GlobalValidationExceptionMapper;
import org.glassfish.jersey.jackson.JacksonFeature;
import org.glassfish.jersey.server.ResourceConfig;

import javax.ws.rs.ApplicationPath;


@ApplicationPath("api")
public class RESTApplication extends ResourceConfig
{

    public RESTApplication() {
        packages("com.entropyshift.rest");
        register(JacksonFeature.class);
        register(GlobalValidationExceptionMapper.class);
    }

}
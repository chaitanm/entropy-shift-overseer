package com.entropyshift.rest;

import com.entropyshift.overseer.auth.annotations.AuthorizeSession;
import com.entropyshift.overseer.auth.filter.AuthorizationFilter;

import javax.ws.rs.container.DynamicFeature;
import javax.ws.rs.container.ResourceInfo;
import javax.ws.rs.core.FeatureContext;

/**
 * Created by chaitanya.m on 4/27/17.
 */
public class ResourceFilterBindingFeature implements DynamicFeature
{
    @Override
    public void configure(ResourceInfo resourceInfo, FeatureContext featureContext)
    {
        if(resourceInfo.getResourceMethod().isAnnotationPresent(AuthorizeSession.class))
        {
            featureContext.register(AuthorizationFilter.class);
        }
    }
}

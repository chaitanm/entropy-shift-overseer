package com.entropyshift.rest;

import com.entropyshift.overseer.credentials.exceptions.PasswordHashGeneratorNotFoundException;
import com.entropyshift.user.exceptions.UserValidationException;
import com.entropyshift.user.registration.IRegistrationService;
import com.entropyshift.user.registration.RegisterUserRequest;
import com.entropyshift.user.registration.RegisterUserResponse;
import org.springframework.beans.factory.annotation.Autowired;

import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

/**
 * Created by chaitanya.m on 2/21/17.
 */
@Path("/register")
public class RegistrationController
{
    @Autowired
    private IRegistrationService registrationService;

    @Path("/user")
    @POST
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    public Response registerUser(RegisterUserRequest request, @Context HttpServletRequest servletRequest)
    {
        try
        {
            this.registrationService.registerUser(request, null, null, null, null);
        }
        catch (UserValidationException e)
        {
            e.printStackTrace();
        }
        catch (PasswordHashGeneratorNotFoundException e)
        {
            e.printStackTrace();
        }
        return Response.ok(new RegisterUserResponse(request.getRequestId(),1)).build();
    }
}

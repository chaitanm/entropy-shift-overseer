package com.entropyshift.rest;

import com.entropyshift.user.constants.UserValidationErrorCodeDescriptors;
import com.entropyshift.user.exceptions.UserValidationException;
import com.entropyshift.user.registration.IRegistrationService;
import com.entropyshift.user.registration.RegisterUserRequest;
import com.entropyshift.user.registration.RegisterUserResponse;
import com.entropyshift.user.registration.RegistrationStatusCodes;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by chaitanya.m on 2/21/17.
 */
@Path("/register")
@Api(value = "/register", description = "User Registration")
public class RegistrationController
{
    @Autowired
    private IRegistrationService registrationService;

    @Path("/user")
    @POST
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    @ApiOperation(value = "Registers the user", response = RegisterUserResponse.class)
    public Response registerUser(@Valid @ApiParam RegisterUserRequest request, @Context HttpServletRequest servletRequest)
    {
        try
        {
            this.registrationService.registerUser(request, null, null, null, null);
        }
        catch (UserValidationException e)
        {
            UserValidationErrorCodeDescriptors descriptor = e.getErrorCodeDescriptor();
            if (statusCodeLookup.containsKey(descriptor))
            {
                return Response.ok(new RegisterUserResponse(request.getRequestId(), statusCodeLookup.get(descriptor))).build();
            }
            else
            {
                return Response.ok(new RegisterUserResponse(request.getRequestId(), RegistrationStatusCodes.SERVER_ERROR)).status(Response.Status.INTERNAL_SERVER_ERROR).build();
            }
        }
        catch (Exception e)
        {
            return Response.ok(new RegisterUserResponse(request.getRequestId(), RegistrationStatusCodes.SERVER_ERROR)).status(Response.Status.INTERNAL_SERVER_ERROR).build();
        }
        return Response.ok(new RegisterUserResponse(request.getRequestId(), RegistrationStatusCodes.SUCCESS)).build();
    }

    private Map<UserValidationErrorCodeDescriptors, String> statusCodeLookup = new HashMap<UserValidationErrorCodeDescriptors, String>()
    {{
        put(UserValidationErrorCodeDescriptors.USER_ID_NOT_AVAILABLE_FOR_REGISTRATION, RegistrationStatusCodes.USERNAME_NOT_AVAILABLE);
        put(UserValidationErrorCodeDescriptors.EMAIL_ADDRESS_ALREADY_REGISTERED, RegistrationStatusCodes.EMAIL_ADDRESS_ALREADY_REGISTERED);
        put(UserValidationErrorCodeDescriptors.CONFIRM_PASSWORD_DOES_NOT_MATCH, RegistrationStatusCodes.PASSWORDS_DO_NOT_MATCH);
    }};
}

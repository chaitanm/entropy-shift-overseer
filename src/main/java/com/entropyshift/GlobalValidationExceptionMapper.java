package com.entropyshift;

import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;
import javax.validation.ValidationException;
import javax.validation.metadata.ConstraintDescriptor;
import javax.ws.rs.core.Response;
import javax.ws.rs.ext.ExceptionMapper;

/**
 * Created by chaitanya.m on 2/23/17.
 */
public class GlobalValidationExceptionMapper implements ExceptionMapper<ValidationException>
{
    @Override
    public Response toResponse(ValidationException e)
    {
        MalformedRequestErrorResponse response = new MalformedRequestErrorResponse();
        for(ConstraintViolation<?> cv : ((ConstraintViolationException) e).getConstraintViolations())
        {
            ConstraintDescriptor<?> descriptor =  cv.getConstraintDescriptor();
            response.setDescription(descriptor.getMessageTemplate());
        }
        return Response.status(Response.Status.BAD_REQUEST).entity(response).build();
    }
}

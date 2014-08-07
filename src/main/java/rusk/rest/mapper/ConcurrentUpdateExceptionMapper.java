package rusk.rest.mapper;

import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;

import rusk.domain.ConcurrentUpdateException;

@Provider
public class ConcurrentUpdateExceptionMapper implements ExceptionMapper<ConcurrentUpdateException> {

    @Override
    public Response toResponse(ConcurrentUpdateException exception) {
        return Response.status(Status.CONFLICT).build();
    }
}

package rusk.rest.mapper;

import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;

import rusk.domain.task.exception.UnexpectedSwitchStatusException;

@Provider
public class UnexpectedSwitchStatusMapper implements ExceptionMapper<UnexpectedSwitchStatusException> {

    @Override
    public Response toResponse(UnexpectedSwitchStatusException exception) {
        return Response.status(Status.CONFLICT).build();
    }
}

package rusk.rest.mapper;

import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import rusk.domain.task.exception.DuplicateWorkTimeException;

@Provider
public class DuplicateWorkTimeExceptionMapper implements ExceptionMapper<DuplicateWorkTimeException> {
    private static final Logger logger = LoggerFactory.getLogger(DuplicateWorkTimeExceptionMapper.class);

    @Override
    public Response toResponse(DuplicateWorkTimeException exception) {
        logger.warn(exception.getMessage());
        return Response.status(Status.BAD_REQUEST).build();
    }
}

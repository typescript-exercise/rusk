package rusk.rest.mapper;

import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import rusk.domain.EntityNotFoundException;

@Provider
public class EntityNotFoundMapper implements ExceptionMapper<EntityNotFoundException> {
    private static final Logger logger = LoggerFactory.getLogger(EntityNotFoundMapper.class);
    
    @Override
    public Response toResponse(EntityNotFoundException exception) {
        logger.warn(exception.getMessage());
        return Response.status(Status.NOT_FOUND).build();
    }
}

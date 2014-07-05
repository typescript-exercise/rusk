package rusk.rest;

import javax.ws.rs.GET;
import javax.ws.rs.Path;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Path("sample")
public class SampleResource {
    
    private static final Logger logger = LoggerFactory.getLogger(SampleResource.class);
    
    @GET
    public String method() {
        logger.info("sample resource");
        return "sample";
    }
}

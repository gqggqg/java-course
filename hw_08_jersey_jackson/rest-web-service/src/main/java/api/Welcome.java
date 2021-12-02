package api;

import javax.annotation.security.PermitAll;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.core.Response;

@Path("/")
public class Welcome {

    @GET
    @PermitAll
    public Response get() {
        return Response
                .ok("Welcome!")
                .build();
    }
}

package server;

import javax.ws.rs.core.HttpHeaders;
import javax.ws.rs.core.Response;

public final class ResponseGenerator {

    public static Response getInternalServerErrorResponse(Object entity) {
        return Response
                .status(Response.Status.INTERNAL_SERVER_ERROR)
                .entity(entity)
                .header(HttpHeaders.CACHE_CONTROL, "no-cache")
                .build();
    }

    public static Response getOkResponse(Object entity) {
        return Response
                .ok(entity)
                .header(HttpHeaders.CACHE_CONTROL, "no-cache")
                .build();
    }

    public static Response getNotFoundResponse(Object entity) {
        return Response
                .status(Response.Status.NOT_FOUND)
                .entity(entity)
                .header(HttpHeaders.CACHE_CONTROL, "no-cache")
                .build();
    }

    public static Response getBadRequestResponse(Object entity) {
        return Response
                .status(Response.Status.BAD_REQUEST)
                .entity(entity)
                .header(HttpHeaders.CACHE_CONTROL, "no-cache")
                .build();
    }
}

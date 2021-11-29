package api;

import javax.annotation.security.DenyAll;
import javax.annotation.security.PermitAll;
import javax.annotation.security.RolesAllowed;

import javax.ws.rs.container.ContainerRequestContext;
import javax.ws.rs.container.ContainerRequestFilter;
import javax.ws.rs.container.ResourceInfo;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.Response;
import javax.ws.rs.ext.Provider;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.*;

@Provider
public class AuthenticationFilter implements ContainerRequestFilter {

    private static final String AUTHORIZATION_PROPERTY = "Authorization";
    private static final String AUTHENTICATION_SCHEME = "Basic";

    @Context
    private ResourceInfo resourceInfo;

    @Override
    public void filter(ContainerRequestContext requestContext) {
        var method = resourceInfo.getResourceMethod();
        if (method.isAnnotationPresent(PermitAll.class)) {
            return;
        }

        if (method.isAnnotationPresent(DenyAll.class) ||
            !method.isAnnotationPresent(RolesAllowed.class)) {
            requestContext.abortWith(Response
                    .status(Response.Status.FORBIDDEN)
                    .entity("Access is denied to all.")
                    .build()
            );
            return;
        }

        final var headers = requestContext.getHeaders();
        final var authorization = headers.get(AUTHORIZATION_PROPERTY);

        if (authorization == null || authorization.isEmpty()) {
            requestContext.abortWith(Response
                    .status(Response.Status.UNAUTHORIZED)
                    .entity("You do not have access to the resource.")
                    .build());
            return;
        }

        var username = "";
        var password = "";

        final var encodedAuthData = authorization.get(0).replaceFirst(AUTHENTICATION_SCHEME + " ", "");
        try {
            final var decodedAuthData = new String(Base64
                    .getDecoder()
                    .decode(encodedAuthData.getBytes(StandardCharsets.UTF_8)));
            final var authData = decodedAuthData.split(":");
            if (authData.length == 2) {
                username = authData[0];
                password = authData[1];
            }
        } catch (IllegalArgumentException e) {
            e.printStackTrace();
        }

        final var rolesAnnotation = method.getAnnotation(RolesAllowed.class);
        final var rolesSet = new HashSet<>(Arrays.asList(rolesAnnotation.value()));

        if (isUserAllowed(username, password, rolesSet)) {
            return;
        }

        requestContext.abortWith(Response.status(Response
                .Status.UNAUTHORIZED)
                .entity("You do not have access to the resource.")
                .build());
    }

    private boolean isUserAllowed(final String username, final String password, final Set<String> rolesSet) {
        return true;
    }
}

package api;

import lombok.AllArgsConstructor;
import org.jetbrains.annotations.NotNull;

import javax.annotation.security.DenyAll;
import javax.annotation.security.PermitAll;
import javax.annotation.security.RolesAllowed;

import javax.ws.rs.container.ContainerRequestContext;
import javax.ws.rs.container.ContainerRequestFilter;
import javax.ws.rs.container.ResourceInfo;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.Response;
import javax.ws.rs.ext.Provider;

import java.nio.charset.StandardCharsets;
import java.util.*;

@Provider
public class AuthenticationFilter implements ContainerRequestFilter {

    @AllArgsConstructor
    private static class LoginData {

        public String username;
        public String password;
    }

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
            sendForbiddenResponse(requestContext);
            return;
        }

        final var headers = requestContext.getHeaders();
        final var authorization = headers.get(AUTHORIZATION_PROPERTY);

        if (authorization == null || authorization.isEmpty()) {
            sendUnauthorizedResponse(requestContext);
            return;
        }

        var loginData = getLoginData(authorization);

        final var rolesAnnotation = method.getAnnotation(RolesAllowed.class);
        final var rolesSet = new HashSet<>(Arrays.asList(rolesAnnotation.value()));

        if (isUserAllowed(loginData, rolesSet)) {
            return;
        }

        sendUnauthorizedResponse(requestContext);
    }

    @NotNull
    private LoginData getLoginData(@NotNull List<String> authorization) {
        var username = "";
        var password = "";

        final var encodedLoginData = authorization
                .get(0)
                .replaceFirst(AUTHENTICATION_SCHEME + " ", "");

        try {
            final var decodedLoginData = getDecodeLoginData(encodedLoginData);
            if (decodedLoginData.length == 2) {
                username = decodedLoginData[0];
                password = decodedLoginData[1];
            }
        } catch (IllegalArgumentException e) {
            System.out.println(e.getMessage());
            e.printStackTrace();
        }

        return new LoginData(username, password);
    }

    @NotNull
    private String[] getDecodeLoginData(@NotNull String encodedLoginData) throws IllegalArgumentException {
        var decodedLoginData = new String(Base64
                .getDecoder()
                .decode(encodedLoginData.getBytes(StandardCharsets.UTF_8)));
        return decodedLoginData.split(":");
    }

    private boolean isUserAllowed(LoginData loginData, final Set<String> rolesSet) {
        return true;
    }

    private void sendUnauthorizedResponse(@NotNull ContainerRequestContext requestContext) {
        requestContext.abortWith(Response
                .status(Response.Status.UNAUTHORIZED)
                .entity("You do not have access to the resource.")
                .build());
    }

    private void sendForbiddenResponse(@NotNull ContainerRequestContext requestContext) {
        requestContext.abortWith(Response
                .status(Response.Status.FORBIDDEN)
                .entity("Access is denied to all.")
                .build()
        );
    }
}

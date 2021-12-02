package api;

import db.ProductDatabase;
import db.ProductDatabase.Select;
import json.JsonConverter;
import json.Product;
import server.ResponseGenerator;

import javax.annotation.security.RolesAllowed;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import java.io.IOException;

import java.sql.SQLException;

@Path("/db")
public final class DatabaseService {

    private static final String ROLE_GUEST = "guest";
    private static final String ROLE_MANAGER = "manager";
    private static final String INTERNAL_SERVER_ERROR_RESPONSE_MESSAGE = "Something went wrong.";

    @GET
    @Path("/products/show/all")
    @Produces({MediaType.APPLICATION_JSON, MediaType.TEXT_PLAIN})
    @RolesAllowed({ROLE_GUEST, ROLE_MANAGER})
    public Response showAllProducts() {
        return getResponseForShowAllProductsRequest();
    }

    @GET
    @Path("/products/show/manufacturer")
    @Produces(MediaType.TEXT_PLAIN)
    @Consumes(MediaType.TEXT_PLAIN)
    @RolesAllowed({ROLE_GUEST, ROLE_MANAGER})
    public Response showProductsByManufacturer(String manufacturer) {
        if (manufacturer == null || manufacturer.isEmpty()) {
            return ResponseGenerator.getBadRequestResponse("Manufacturer is not set.");
        }

        return getResponseToShowProductsByFilterRequest(Select.BY_MANUFACTURER, manufacturer);
    }

    @POST
    @Path("/products/add")
    @Produces(MediaType.TEXT_PLAIN)
    @Consumes(MediaType.APPLICATION_JSON)
    @RolesAllowed(ROLE_MANAGER)
    public Response addProduct(Product product) {
        if (product == null) {
            return ResponseGenerator.getBadRequestResponse("Product is not set.");
        }

        try {
            ProductDatabase.addProduct(product);
            return ResponseGenerator.getOkResponse("Product was added.");
        } catch (SQLException e) {
            System.out.println(e.getMessage());
            e.printStackTrace();
            return ResponseGenerator.getInternalServerErrorResponse(INTERNAL_SERVER_ERROR_RESPONSE_MESSAGE);
        }
    }

    @POST
    @Path("/products/delete")
    @Produces(MediaType.TEXT_PLAIN)
    @Consumes(MediaType.TEXT_PLAIN)
    @RolesAllowed(ROLE_MANAGER)
    public Response deleteProduct(String productName) {
        if (productName == null || productName.isEmpty()) {
            return ResponseGenerator.getBadRequestResponse("Product name is not set.");
        }

        try {
            var numberOfDeletions = ProductDatabase.deleteProductsByName(productName);
            if (numberOfDeletions > 0) {
                return ResponseGenerator.getOkResponse("Deleted products: " + numberOfDeletions);
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
            e.printStackTrace();
            return ResponseGenerator.getInternalServerErrorResponse(INTERNAL_SERVER_ERROR_RESPONSE_MESSAGE);
        }

        return ResponseGenerator.getNotFoundResponse("Products with this name are not in the database.");
    }

    private Response getResponseForShowAllProductsRequest() {
        return getResponseToShowProductsByFilterRequest(Select.ALL, null);
    }

    private Response getResponseToShowProductsByFilterRequest(Select select, String filter) {
        try {
            final var products = ProductDatabase.getProducts(select, filter);
            final var json = JsonConverter.convertListToJson(products);
            return ResponseGenerator.getOkResponse(json);
        } catch (SQLException | IOException e) {
            System.out.println(e.getMessage());
            e.printStackTrace();
            return ResponseGenerator.getInternalServerErrorResponse(INTERNAL_SERVER_ERROR_RESPONSE_MESSAGE);
        }
    }
}

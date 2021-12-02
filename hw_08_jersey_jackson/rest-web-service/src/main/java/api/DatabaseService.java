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
        return getResponseToShowProductsByFilterRequest(Select.BY_MANUFACTURER, manufacturer);
    }

    @POST
    @Path("/products/add")
    @Produces(MediaType.TEXT_PLAIN)
    @Consumes(MediaType.APPLICATION_JSON)
    @RolesAllowed(ROLE_MANAGER)
    public Response addProduct(Product product) {
        try {
            ProductDatabase.addProduct(product);
            return ResponseGenerator.getOkResponse("Product was added.");
        } catch (SQLException e) {
            System.out.println(e.getMessage());
            e.printStackTrace();
            return ResponseGenerator.getInternalServerErrorResponse("Something went wrong.");
        }
    }

    @POST
    @Path("/products/delete")
    @Produces(MediaType.TEXT_PLAIN)
    @Consumes(MediaType.TEXT_PLAIN)
    @RolesAllowed(ROLE_MANAGER)
    public Response deleteProduct(String productName) {
        try {
            var numberOfDeletions = ProductDatabase.deleteProductsByName(productName);
            if (numberOfDeletions > 0) {
                return ResponseGenerator.getOkResponse("Deleted products: " + numberOfDeletions);
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
            e.printStackTrace();
            return ResponseGenerator.getInternalServerErrorResponse("Cannot add a product.");
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
            return ResponseGenerator.getInternalServerErrorResponse("Something went wrong.");
        }
    }
}

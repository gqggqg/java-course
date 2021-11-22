import DAO.*;

import generated.tables.records.*;
import org.jetbrains.annotations.NotNull;

import org.jooq.*;
import org.jooq.exception.DataAccessException;
import org.jooq.impl.DSL;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Random;

import static generated.Tables.*;
import static org.jooq.impl.DSL.*;

public class Main {

    private static final @NotNull JDBCCredentials CREDS = JDBCCredentials.DEFAULT;

    public static void main(@NotNull String[] args) {
        FlywayInitializer.initDB();

        try (Connection conn = DriverManager.getConnection(CREDS.getUrl(), CREDS.getLogin(), CREDS.getPassword())) {
            final DSLContext context = DSL.using(conn, SQLDialect.POSTGRES);

            fillDB(context);

            System.out.println("The first 10 vendors by quantity of delivered products.");
            for (Record record : selectTopTenOrganizationsByProductQuantity(context)) {
                System.out.println(record);
            }

            System.out.println();
            System.out.println("Vendors with the total quantity of the delivered product above the limit.");
            for (Record record : selectOrganizationsByTotalQuantityProductAboveLimit(context,1L, BigDecimal.valueOf(1L))) {
                System.out.println(record);
            }

            var startDate = LocalDate.of(2021, 11, 1);
            var endData = LocalDate.of(2021, 12, 1);

            System.out.println();
            System.out.println("Quantity and total price of the product for the period.");
            for (Record record : calculateProductQuantityAndTotalPriceForPeriod(context, startDate, endData)) {
                System.out.println(record);
            }

            System.out.println();
            var avgPrice = calculateAVGPriceForPeriod(context, startDate, endData);
            System.out.printf("Average price of received product for the period: %.2f\n", avgPrice);

            System.out.println();
            System.out.println("Products delivered by organizations for the period.");
            for (Record record : selectProductsDeliveredByOrganizationsForPeriod(context, startDate, endData)) {
                System.out.println(record);
            }

        } catch (SQLException e) {
            System.err.println("Connection failure.");
            e.printStackTrace();
        } catch (DataAccessException e) {
            System.err.println("Something went wrong executing the query.");
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @NotNull
    public static Result<Record3<Long, String, BigDecimal>> selectTopTenOrganizationsByProductQuantity(@NotNull DSLContext context) throws DataAccessException {
        final String itemsSum = "ITEMS_SUM";
        final Field<BigDecimal> sumField = sum(ITEM.QUANTITY).as(itemsSum);

        return context
                .select(
                        ORGANIZATION.INN,
                        ORGANIZATION.NAME,
                        sumField
                )
                .from(ORGANIZATION)
                .join(WAYBILL).on(WAYBILL.ORGANIZATION_ID.eq(ORGANIZATION.INN))
                .join(WAYBILL_ITEM).on(WAYBILL_ITEM.WAYBILL_ID.eq(WAYBILL.NUMBER))
                .join(ITEM).on(ITEM.ID.eq(WAYBILL_ITEM.ITEM_ID.cast(Long.class)))
                .groupBy(ORGANIZATION.INN, ORGANIZATION.NAME)
                .orderBy(sumField.desc())
                .limit(10)
                .fetch();
    }

    @NotNull
    public static Result<Record2<Long, String>> selectOrganizationsByTotalQuantityProductAboveLimit(@NotNull DSLContext context, long productId, BigDecimal limit) throws DataAccessException {
        return context
                .select(ORGANIZATION.INN,
                        ORGANIZATION.NAME)
                .from(ORGANIZATION)
                .join(WAYBILL).on(WAYBILL.ORGANIZATION_ID.eq(ORGANIZATION.INN))
                .join(WAYBILL_ITEM).on(WAYBILL_ITEM.WAYBILL_ID.eq(WAYBILL.NUMBER))
                .join(ITEM).on(ITEM.ID.eq(WAYBILL_ITEM.ITEM_ID.cast(Long.class)))
                .where(ITEM.ID.eq(productId))
                .groupBy(ORGANIZATION.INN, ORGANIZATION.NAME)
                .having(sum(ITEM.QUANTITY).gt(limit))
                .fetch();
    }

    @NotNull
    public static Result<Record4<String, LocalDate, BigDecimal, BigDecimal>> calculateProductQuantityAndTotalPriceForPeriod(@NotNull DSLContext context, LocalDate startDate, LocalDate endData) throws DataAccessException {
        return context
                .select(PRODUCT.NAME,
                        WAYBILL.DATE,
                        sum(ITEM.QUANTITY).as("total_quantity"),
                        sum(ITEM.PRICE).as("total_price"))
                .from(WAYBILL)
                .join(WAYBILL_ITEM).on(WAYBILL_ITEM.WAYBILL_ID.eq(WAYBILL.NUMBER))
                .join(ITEM).on(ITEM.ID.eq(WAYBILL_ITEM.ITEM_ID.cast(Long.class)))
                .join(PRODUCT).on(PRODUCT.ID.eq(ITEM.PRODUCT_ID.cast(Long.class)))
                .where(WAYBILL.DATE.between(startDate, endData))
                .groupBy(PRODUCT.NAME, WAYBILL.DATE)
                .fetch();
    }

    public static BigDecimal calculateAVGPriceForPeriod(@NotNull DSLContext context, LocalDate startDate, LocalDate endData) throws DataAccessException {
        return context
                .select(avg(ITEM.PRICE))
                .from(WAYBILL)
                .join(WAYBILL_ITEM).on(WAYBILL_ITEM.WAYBILL_ID.eq(WAYBILL.NUMBER))
                .join(ITEM).on(ITEM.ID.eq(WAYBILL_ITEM.ITEM_ID.cast(Long.class)))
                .join(PRODUCT).on(PRODUCT.ID.eq(ITEM.PRODUCT_ID.cast(Long.class)))
                .where(WAYBILL.DATE.between(startDate, endData))
                .fetchOneInto(BigDecimal.class);
    }

    @NotNull
    public static Result<Record3<Long, String, String>> selectProductsDeliveredByOrganizationsForPeriod(@NotNull DSLContext context, LocalDate startDate, LocalDate endData) throws DataAccessException {
        var dateCondition = WAYBILL.DATE.between(startDate, endData);
        var waybillCondition = PRODUCT.CODE.isNull().and(WAYBILL.NUMBER.isNull());
        var productCondition = PRODUCT.CODE.isNotNull();

        return context
                .select(ORGANIZATION.INN,
                        ORGANIZATION.NAME,
                        PRODUCT.NAME)
                .from(ORGANIZATION)
                .leftJoin(WAYBILL).on(WAYBILL.ORGANIZATION_ID.eq(ORGANIZATION.INN))
                .leftJoin(WAYBILL_ITEM).on(WAYBILL_ITEM.WAYBILL_ID.eq(WAYBILL.NUMBER))
                .leftJoin(ITEM).on(ITEM.ID.eq(WAYBILL_ITEM.ITEM_ID.cast(Long.class)))
                .leftJoin(PRODUCT).on(PRODUCT.ID.eq(ITEM.PRODUCT_ID.cast(Long.class)))
                .where(dateCondition.and(waybillCondition.or(productCondition)))
                .groupBy(ORGANIZATION.INN, ORGANIZATION.NAME, PRODUCT.NAME)
                .orderBy(ORGANIZATION.INN)
                .fetch();
    }

    public static void fillDB(DSLContext context) {
        Random random = new Random(java.time.Instant.now().getEpochSecond());

        createProducts(context);
        createOrganizations(context);
        createItems(context, random);
        createWaybills(context, random);
        createWaybillItems(context, random);
    }

    public static void createProducts(DSLContext context) {
        var productDAO = new ProductDAO(context);
        for (long i = 1; i <= 10; i++) {
            productDAO.save(new ProductRecord(i, "product_" + i, 100 + i));
        }
    }

    public static void createOrganizations(DSLContext context) {
        var organizationDAO = new OrganizationDAO(context);
        for (long i = 1; i <= 10; i++) {
            organizationDAO.save(new OrganizationRecord("organization_" + i, 1000 + i, 2000 + i));
        }
    }

    public static void createItems(DSLContext context, Random random) {
        var itemDAO = new ItemDAO(context);
        for (long i = 1; i <= 10; i++) {
            itemDAO.save(new ItemRecord(i, (long)random.nextInt(91) + 10, random.nextInt(10) + 1, (long)random.nextInt(51) + 1));
        }
    }

    public static void createWaybills(DSLContext context, Random random) {
        var waybillDAO = new WaybillDAO(context);
        for (long i = 1; i <= 10; i++) {
            waybillDAO.save(new WaybillRecord(100 + i, LocalDate.of(2021, 11, random.nextInt(30) + 1), (long)random.nextInt(10) + 1001));
        }
    }

    public static void createWaybillItems(DSLContext context, Random random) {
        var waybillItemDAO = new WaybillItemDAO(context);
        for (long i = 1; i <= 10; i++) {
            waybillItemDAO.save(new WaybillItemRecord(i, (long)random.nextInt(10) + 101, random.nextInt(10) + 1));
        }
    }
}

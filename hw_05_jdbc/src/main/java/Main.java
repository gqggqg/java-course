import DAO.OrganizationDAO;
import DAO.ProductDAO;

import org.jetbrains.annotations.NotNull;

import java.sql.Date;
import java.sql.DriverManager;
import java.sql.SQLException;

import java.text.ParseException;
import java.text.SimpleDateFormat;

public class Main {

    private static final @NotNull JDBCCredentials CREDS = JDBCCredentials.DEFAULT;

    public static void main(String[] args) {
        FlywayInitializer.initDB();

        try (var connection = DriverManager.getConnection(CREDS.getUrl(), CREDS.getLogin(), CREDS.getPassword())) {

            var organizationDAO = new OrganizationDAO(connection);
            var productDAO = new ProductDAO(connection);

            organizationDAO.selectTopTenByProductQuantity();
            System.out.println();

            organizationDAO.selectByProductAndTotalSumPriceLimit(1, 1);
            System.out.println();

            try {
                var startDate = getData("2021-11-01");
                var endData = getData("2021-12-01");
                productDAO.calculateProductQuantityAndCostFromPeriod(startDate, endData);
                System.out.println();
                productDAO.calculateAVGPriceForPeriod(startDate, endData);
                System.out.println();
            } catch (ParseException e) {
                System.err.println("Date parsing failure.");
            }


        } catch (SQLException e) {
            System.err.println("Connection failure.");
            e.printStackTrace();
        }
    }

    private static Date getData(String date) throws ParseException {
        var dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        return new java.sql.Date(dateFormat.parse(date).getTime());
    }
}

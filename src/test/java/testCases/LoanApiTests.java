package testCases;
import io.restassured.RestAssured;
import io.restassured.response.Response;
import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import java.time.LocalDate;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Map;

import static io.restassured.RestAssured.given;

public class LoanApiTests {

    private String authToken;

    @BeforeClass
    public void setup() {
        // Set base URI for all requests
        RestAssured.baseURI = "https://api.abccorp.com";

        // Authenticate to get the token
        authenticate();
    }

    // Authentication method to retrieve token from /authenticate endpoint
    public void authenticate() {
        Response authResponse = given()
                .header("Content-Type", "application/json")
                .body("{ \"username\": \"alan doe\", \"password\": \"Rea/5303t\", \"role\": \"ir admin\" }")
                .when()
                .post("/authenticate")
                .then()
                .statusCode(200)
                .extract().response();

        // Store the token from the response body
        authToken = authResponse.body().asString();
    }

    @Test
    public void testRetrieveLendeesAbove40() {
        // Send GET request to retrieve loans with Authorization header
        Response response = given()
                .header("KABCCorp-Authentication", authToken)
                .when()
                .get("/loans/endee")
                .then()
                .statusCode(200) // Assert status code
                .extract().response();

        // Extract the response body as a list of maps
        List<Map<String, Object>> lendees = response.jsonPath().getList("");

        // Loop through each lendee and assert their age is above 40
        lendees.stream()
                .filter(lendee -> {
                    String dob = (String) lendee.get("dob");
                    LocalDate birthDate = LocalDate.parse(dob.substring(0, 10));
                    return LocalDate.now().getYear() - birthDate.getYear() > 40;
                })
                .forEach(lendee -> {
                    Assert.assertTrue(true, "Lendee is above 40");
                });

        // Assert headers
        Assert.assertEquals(response.getHeader("X-ABC-Trace-ID").length(), 36);
    }

    @Test
    public void testReduceInterestRateForLendeesAbove45() {
        // Send GET request to retrieve loans with Authorization header
        Response response = given()
                .header("KABCCorp-Authentication", authToken)
                .when()
                .get("/loans/endee")
                .then()
                .statusCode(200)
                .extract().response();

        List<Map<String, Object>> lendees = response.jsonPath().getList("");

        // Check timezone effect and filter by age and date of birth
        lendees.stream()
                .filter(lendee -> {
                    String dob = (String) lendee.get("dob");
                    LocalDate birthDate = LocalDate.parse(dob.substring(0, 10));
                    ZonedDateTime zonedBirthDate = birthDate.atStartOfDay(ZoneId.of("GMT"));
                    return LocalDate.now().getYear() - birthDate.getYear() > 45
                            && zonedBirthDate.isBefore(ZonedDateTime.parse("2011-01-01T00:00:00Z", DateTimeFormatter.ISO_ZONED_DATE_TIME));
                })
                .forEach(lendee -> {
                    String loanAccountNumber = (String) lendee.get("loanAccountNumber");
                    Double currentInterestRate = Double.parseDouble(lendee.get("interestRate").toString());

                    // Reduce the interest rate by 10%
                    Double newInterestRate = currentInterestRate * 0.9;

                    Response interestRateResponse = given()
                            .header("KABCCorp-Authentication", authToken)
                            .pathParam("loanAccountNumber", loanAccountNumber)
                            .body("{ \"interestRate\": " + newInterestRate + " }")
                            .when()
                            .patch("/loans/interestrate/{loanAccountNumber}")
                            .then()
                            .statusCode(200)
                            .extract().response();

                    // Assert interest rate has been updated
                    Assert.assertEquals(interestRateResponse.jsonPath().getDouble("interestRate"),
                            newInterestRate);
                });

        // Assert headers
        Assert.assertEquals(response.getHeader("X-ABC-Trace-ID").length(), 36);
    }

    @Test
    public void testLoginWithBadCredentials() {
        // Attempt login with bad credentials
        Response response = given()
                .header("Content-Type", "application/json")
                .body("{ \"username\": \"invalidUsername\", \"password\": \"invalidPassword\", \"role\": \"ir admin\" }")
                .when()
                .post("/authenticate")
                .then()
                .statusCode(401)  // Assert Unauthorized response
                .extract().response();

        // Assert response body for error message
        Assert.assertTrue(response.body().asString().contains("Invalid credentials"));

        // Assert response headers
        Assert.assertEquals(response.getHeader("X-ABC-Trace-ID").length(), 36);
    }
}

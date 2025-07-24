import io.restassured.RestAssured;
import io.restassured.response.Response;
import org.testng.Assert;
import org.testng.annotations.*;

public class JuiceShopApiTests {
    private String baseUrl = "https://juice-shop.herokuapp.com";
    private String token;
    private int basketItemId;

    @BeforeClass
    public void setup() {
        RestAssured.baseURI = baseUrl;
    }

    @Test(priority = 1)
    public void loginValid() {
        Response resp = RestAssured.given().contentType("application/json")
            .body("{\"email\":\"test994@test.com\",\"password\":\"222888Mmm!\"}")
            .post("/rest/user/login");
        Assert.assertEquals(resp.statusCode(), 200);
        token = resp.jsonPath().getString("authentication.token");
        Assert.assertNotNull(token);
    }

    @Test(priority = 2)
    public void loginInvalid() {
        Response resp = RestAssured.given().contentType("application/json")
            .body("{\"email\":\"notexist@fake.com\",\"password\":\"WrongPass!\"}")
            .post("/rest/user/login");
        Assert.assertTrue(resp.statusCode() == 401 || resp.statusCode() == 400);
    }

    @Test(priority = 3, dependsOnMethods = "loginValid")
    public void getProducts() {
        Response resp = RestAssured.given()
            .header("Authorization", "Bearer " + token)
            .get("/api/Products");
        Assert.assertEquals(resp.statusCode(), 200);
        Assert.assertTrue(resp.jsonPath().getList("$").size() > 0);
    }

    @Test(priority = 4, dependsOnMethods = "loginValid")
    public void addBasketItemValid() {
        String payload = "{\"ProductId\":1,\"quantity\":1}";
        Response resp = RestAssured.given()
            .header("Authorization", "Bearer " + token)
            .contentType("application/json")
            .body(payload)
            .post("/api/BasketItems");
        Assert.assertEquals(resp.statusCode(), 201);
        basketItemId = resp.jsonPath().getInt("id");
    }

    @Test(priority = 5, dependsOnMethods = "loginValid")
    public void addBasketItemInvalid() {
        String payload = "{\"ProductId\":99999,\"quantity\":0}";
        Response resp = RestAssured.given()
            .header("Authorization", "Bearer " + token)
            .contentType("application/json")
            .body(payload)
            .post("/api/BasketItems");
        Assert.assertTrue(resp.statusCode() >= 400);
    }

    @Test(priority = 6, dependsOnMethods = "addBasketItemValid")
    public void updateBasketItemValid() {
        String payload = "{\"quantity\":5}";
        Response resp = RestAssured.given()
            .header("Authorization", "Bearer " + token)
            .contentType("application/json")
            .body(payload)
            .put("/api/BasketItems/" + basketItemId);
        Assert.assertEquals(resp.statusCode(), 200);
        Assert.assertEquals(resp.jsonPath().getInt("quantity"), 5);
    }

    @Test(priority = 7, dependsOnMethods = "loginValid")
    public void updateBasketItemInvalid() {
        String payload = "{\"quantity\":10}";
        Response resp = RestAssured.given()
            .header("Authorization", "Bearer " + token)
            .contentType("application/json")
            .body(payload)
            .put("/api/BasketItems/99999999");
        Assert.assertTrue(resp.statusCode() >= 400);
    }

    @Test(priority = 8, dependsOnMethods = "addBasketItemValid")
    public void deleteBasketItemValid() {
        Response resp = RestAssured.given()
            .header("Authorization", "Bearer " + token)
            .delete("/api/BasketItems/" + basketItemId);
        Assert.assertTrue(resp.statusCode() == 200 || resp.statusCode() == 204);
    }

    @Test(priority = 9, dependsOnMethods = "loginValid")
    public void deleteBasketItemInvalid() {
        Response resp = RestAssured.given()
            .header("Authorization", "Bearer " + token)
            .delete("/api/BasketItems/99999999");
        Assert.assertTrue(resp.statusCode() >= 400);
    }
}

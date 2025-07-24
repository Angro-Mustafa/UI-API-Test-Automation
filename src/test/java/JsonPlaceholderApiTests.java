import io.restassured.RestAssured;
import io.restassured.response.Response;
import org.testng.Assert;
import org.testng.annotations.*;

public class JsonPlaceholderApiTests {
    @BeforeClass
    public void setup() { RestAssured.baseURI = "https://jsonplaceholder.typicode.com"; }

    @Test
    public void getPosts() {
        Response resp = RestAssured.get("/posts");
        Assert.assertEquals(resp.statusCode(), 200);
        Assert.assertTrue(resp.jsonPath().getList("$").size() > 0);
    }

    @Test
    public void createPost() {
        String body = "{\"title\":\"foo\",\"body\":\"bar\",\"userId\":1}";
        Response resp = RestAssured.given()
            .header("Content-type", "application/json").body(body).post("/posts");
        Assert.assertEquals(resp.statusCode(), 201);
        Assert.assertNotNull(resp.jsonPath().get("id"));
    }

    @Test
    public void updatePost() {
        String body = "{\"id\":1,\"title\":\"updated\",\"body\":\"bar\",\"userId\":1}";
        Response resp = RestAssured.given()
            .header("Content-type", "application/json").body(body).put("/posts/1");
        Assert.assertEquals(resp.statusCode(), 200);
        Assert.assertEquals(resp.jsonPath().get("title"), "updated");
    }

    @Test
    public void deletePost() {
        Response resp = RestAssured.delete("/posts/1");
        Assert.assertEquals(resp.statusCode(), 200);
    }

    @Test
    public void getNonExistentPost() {
        Assert.assertEquals(RestAssured.get("/posts/999999").statusCode(), 404);
    }
}

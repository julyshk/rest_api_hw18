package tests;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static io.restassured.RestAssured.given;
import static io.restassured.http.ContentType.JSON;
import static org.hamcrest.Matchers.is;

public class ReqresInTests {

    @Test
    @DisplayName("Отправка запроса POST ..api/users с параметрами 'name' и 'job'")
    void createUserTest() {
        String data = "{ \"name\": \"morpheus\", \"job\": \"leader\" }";

        given()
                .log().uri()
                .contentType(JSON)
                .body(data)
                .when()
                .post("https://reqres.in/api/users")
                .then()
                .log().status()
                .log().body()
                .statusCode(201)
                .body("name", is("morpheus"))
                .body("job", is("leader"));
    }

    @Test
    @DisplayName("Отправка запроса POST ..api/users только с параметром 'job'")
    void createUserTestNotName() {
        String data = "{ \"job\": \"leader\" }";

        given()
                .log().uri()
                .contentType(JSON)
                .body(data)
                .when()
                .post("https://reqres.in/api/users")
                .then()
                .log().status()
                .log().body()
                .statusCode(201)
                .body("job", is("leader"));
    }

    @Test
    @DisplayName("Отправка запроса POST ..api/users только с параметром 'name'")
    void createUserTestNotJob() {
        String data = "{ \"name\": \"morpheus\" }";

        given()
                .log().uri()
                .contentType(JSON)
                .body(data)
                .when()
                .post("https://reqres.in/api/users")
                .then()
                .log().status()
                .log().body()
                .statusCode(201)
                .body("name", is("morpheus"));
    }

    @Test
    @DisplayName("Отправка запроса POST ..api/users без параметров")
    void createUserTestNotNameAndJob() {

        given()
                .log().uri()
                .when()
                .post("https://reqres.in/api/users")
                .then()
                .log().status()
                .log().body()
                .statusCode(415);
    }

    @Test
    @DisplayName("Отправка запроса POST ..api/users с указанным параметром 'id'")
    void createUserTestWithId() {
        String data = "{ \"name\": \"Julia\", \"id\": \"222\" }";

        given()
                .log().uri()
                .contentType(JSON)
                .body(data)
                .when()
                .post("https://reqres.in/api/users")
                .then()
                .log().status()
                .log().body()
                .statusCode(201)
                .body("name", is("Julia"))
                .body("id", is("222"));
    }
}

package tests;

import model.lombok.UserBodyLombokModel;
import model.lombok.UserResponseLombokModel;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static io.restassured.RestAssured.given;
import static org.assertj.core.api.Assertions.assertThat;
import static io.qameta.allure.Allure.step;
import static specs.UserSpec.*;
import static org.hamcrest.Matchers.hasItem;
import static org.hamcrest.CoreMatchers.*;

public class ReqresInTestsWithModel {

    @Test
    @DisplayName("POST ..api/users with 'name' and 'job'")
    void createUserTest() {
        step("POST ..api/users with 'name' and 'job'", () -> {

            UserBodyLombokModel userBody = new UserBodyLombokModel();
            userBody.setName("Julia");
            userBody.setJob("leader");

            UserResponseLombokModel userResponse = given(userRequestSpec)
                    .body(userBody)
                    .when()
                    .post("/users")
                    .then()
                    .spec(userResponseSpecSuccessful)
                    .extract().as(UserResponseLombokModel.class);

            assertThat(userResponse.getName()).isEqualTo("Julia");
            assertThat(userResponse.getJob()).isEqualTo("leader");
        });
    }

    @Test
    @DisplayName("POST ..api/users with only 'job'")
    void createUserTestNotName() {
        step("POST ..api/users with only 'job'", () -> {

            UserBodyLombokModel userBody = new UserBodyLombokModel();
            userBody.setJob("leader");

            UserResponseLombokModel userResponse = given(userRequestSpec)
                    .body(userBody)
                    .when()
                    .post("/users")
                    .then()
                    .spec(userResponseSpecSuccessful)
                    .extract().as(UserResponseLombokModel.class);

            assertThat(userResponse.getJob()).isEqualTo("leader");
        });
    }

    @Test
    @DisplayName("POST ..api/users with only 'name'")
    void createUserTestNotJob() {
        step("POST ..api/users with only 'name'", () -> {

            UserBodyLombokModel userBody = new UserBodyLombokModel();
            userBody.setName("Julia");

            UserResponseLombokModel userResponse = given(userRequestSpec)
                    .body(userBody)
                    .when()
                    .post("/users")
                    .then()
                    .spec(userResponseSpecSuccessful)
                    .extract().as(UserResponseLombokModel.class);

            assertThat(userResponse.getName()).isEqualTo("Julia");
        });
    }

    @Test
    @DisplayName("POST ..api/users without parameters")
    void createUserTestNotNameAndJob() {
        step("POST ..api/users without parameters", () -> {

            given(userRequestSpecError415)
                    .when()
                    .post("/users")
                    .then()
                    .spec(userResponseSpecError415);

        });
    }

    @Test
    @DisplayName("POST ..api/users with parameter 'id'")
    void createUserTestWithId() {
        step("POST ..api/users with parameter 'id'", () -> {

            UserBodyLombokModel userBody = new UserBodyLombokModel();
            userBody.setName("Julia");
            userBody.setId("222");

            UserResponseLombokModel userResponse = given(userRequestSpec)
                    .body(userBody)
                    .when()
                    .post("/users")
                    .then()
                    .spec(userResponseSpecSuccessful)
                    .extract().as(UserResponseLombokModel.class);

            assertThat(userResponse.getName()).isEqualTo("Julia");
            assertThat(userResponse.getId()).isEqualTo("222");
        });
    }

    @Test
    @DisplayName("GET ..users?page=2 search email with groovy")
    void searchEmailTestWithGroovy() {
        step("GET ..users?page=2 search email with groovy", () -> {

            given()
                    .spec(userRequestSpec)
                    .when()
                    .get("/users?page=2")
                    .then()
                    .spec(findUserResponseSpec)
                    .body("data.findAll{it.email =~/.*?@reqres.in/}.email.flatten()",
                    hasItem("michael.lawson@reqres.in"));
        });
    }

    @Test
    @DisplayName("GET ..users?page=2 search username by id with groovy")
    void searchUsernameTestWithGroovy() {
        step("GET ..users?page=2 search username by id with groovy", () -> {

            given()
                    .spec(userRequestSpec)
                    .when()
                    .get("/users?page=2")
                    .then()
                    .spec(findUserResponseSpec)
                    .body("data.find{it.id == 7}.first_name", is("Michael"));
        });
    }

    @Test
    @DisplayName("GET ..users?page=2 search email by first_name and last_name with groovy")
    void searchUserEmailByNameTestWithGroovy() {
        step("GET ..users?page=2 search email by first_name and last_name with groovy", () -> {

            given()
                    .spec(userRequestSpec)
                    .when()
                    .get("/users?page=2")
                    .then()
                    .spec(findUserResponseSpec)
                    .body("data.find{(it.first_name =~/Lindsay/)&&(it.last_name =~/Ferguson/)}.email", is("lindsay.ferguson@reqres.in"));
        });
    }



}

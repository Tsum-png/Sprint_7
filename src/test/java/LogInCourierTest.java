import courier.CreationCourier;
import courier.LoginCourier;
import io.restassured.RestAssured;
import org.hamcrest.MatcherAssert;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import static constant.Constants.*;
import static io.restassured.RestAssured.given;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.hamcrest.Matchers.equalTo;

public class LogInCourierTest {
    @Before
    public void setUp() {
        RestAssured.baseURI = URL;
    }
    @Before
    public void CreateCourierForTest() {
        CreationCourier courier = new CreationCourier("ninja", "password123", "Пуська");

        given()
                .contentType("application/json")
                .body(courier)
                .when()
                .post(COURIER_CREATE)
                .then()
                .statusCode(201)
                .assertThat().body("ok", equalTo(true));

    }

    @Test
    public void testLoginCourierSuccessfully() {
        LoginCourier courier1 = new LoginCourier("ninja", "password123" );

        given()
                .contentType("application/json")
                .body(courier1)
                .when()
                .post(COURIER_LOGIN)
                .then()
                .statusCode(200);
        MatcherAssert.assertThat("id", notNullValue());
    }

    @Test
    public void testLoginCourierWrongLogin() {
        LoginCourier courier1 = new LoginCourier("nomja", "password123" );

        given()
                .contentType("application/json")
                .body(courier1)
                .when()
                .post(COURIER_LOGIN)
                .then()
                .statusCode(404)
                .assertThat().body("message", equalTo("Учетная запись не найдена"));
    }

    @Test
    public void testLoginCourierWrongPassword() {
        LoginCourier courier1 = new LoginCourier("ninja", "passd123" );

        given()
                .contentType("application/json")
                .body(courier1)
                .when()
                .post(COURIER_LOGIN)
                .then()
                .statusCode(404)
                .assertThat().body("message", equalTo("Учетная запись не найдена"));
    }

    @Test
    public void testLoginCourierWithoutPassword() {
        LoginCourier courier1 = new LoginCourier("ninja", "" );

        given()
                .contentType("application/json")
                .body(courier1)
                .when()
                .post(COURIER_LOGIN)
                .then()
                .statusCode(400)
                .assertThat().body("message", equalTo("Недостаточно данных для входа"));
    }

    @After
    public void deleteCourier() {

        given()
                .contentType("application/json")
                .when()
                .delete("/api/v1/courier/:1") // Используем ID для удаления
                .then()
                .statusCode(200);  // Убедитесь, что удаление прошло успешно
    }
}

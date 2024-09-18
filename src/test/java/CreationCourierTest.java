import courier.CreationCourier;
import io.restassured.RestAssured;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import static constant.Constants.COURIER_CREATE;
import static constant.Constants.URL;
import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.equalTo;

public class CreationCourierTest {
    @Before
    public void setUp() {
        RestAssured.baseURI = URL;
    }

    @Test
    public void testCreateCourierSuccessfully() {
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
    public void testCannotCreateDuplicateCourier() {

        CreationCourier courier = new CreationCourier("ninja", "password123", "Пуська");


        given()
                .contentType("application/json")
                .body(courier)
                .when()
                .post(COURIER_CREATE)
                .then()
                .statusCode(201)
                .body("ok", equalTo(true));

        given()
                .contentType("application/json")
                .body(courier)
                .when()
                .post(COURIER_CREATE)
                .then()
                .statusCode(409)
                .assertThat().body("message", equalTo("Этот логин уже используется"));
    }

    @Test
    public void testCreateCourierWithoutLogin() {
        CreationCourier courier = new CreationCourier("", "password123", "Пуська");

        given()
                .contentType("application/json")
                .body(courier)
                .when()
                .post(COURIER_CREATE)
                .then()
                .statusCode(400)
                .assertThat().body("message", equalTo("Недостаточно данных для создания учетной записи"));;

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


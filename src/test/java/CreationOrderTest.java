import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import order.CreationOrder;
import org.junit.Before;
import org.junit.Test;

import java.util.Arrays;
import java.util.List;

import static constant.Constants.ORDER_CREATE;
import static constant.Constants.URL;
import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.notNullValue;

public class CreationOrderTest {


    @Before
    public void setUp() {
        RestAssured.baseURI = URL;
    }

    // Вспомогательный метод для создания тела запроса
    private CreationOrder createOrder(List<String> colors) {
        return new CreationOrder("Пуська", "Сглыпа", "Сикейроса 88к4", 4, "+7234567890",
                2, "2023-09-15", "Test comment", colors);
    }

    @Test
    public void createOrderWithBlackColor() {
        CreationOrder order = createOrder(Arrays.asList("BLACK"));

        given()
                .contentType("application/json")
                .body(order)
                .when()
                .post(ORDER_CREATE)
                .then()
                .statusCode(201)
                .assertThat().body("track", notNullValue());
    }

    @Test
    public void createOrderWithGreyColor() {
        CreationOrder order = createOrder(Arrays.asList("GREY"));

        given()
                .contentType("application/json")
                .body(order)
                .when()
                .post(ORDER_CREATE)
                .then()
                .statusCode(201)
                .assertThat().body("track", notNullValue());
    }

    @Test
    public void createOrderWithBothColors() {
        CreationOrder order = createOrder(Arrays.asList("BLACK", "GREY"));

        given()
                .contentType("application/json")
                .body(order)
                .when()
                .post(ORDER_CREATE)
                .then()
                .statusCode(201)
                .assertThat().body("track", notNullValue());
    }

    @Test
    public void createOrderWithoutColor() {
        CreationOrder order = createOrder(null);

        given()
                .contentType("application/json")
                .body(order)
                .when()
                .post(ORDER_CREATE)
                .then()
                .statusCode(201)
                .assertThat().body("track", notNullValue());
    }
}
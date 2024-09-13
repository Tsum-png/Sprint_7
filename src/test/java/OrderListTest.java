import io.restassured.RestAssured;
import io.restassured.response.ValidatableResponse;
import order.CreationOrder;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.util.List;

import static constant.Constants.ORDER_LIST;
import static constant.Constants.URL;
import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.*;

public class OrderListTest {

    private ValidatableResponse orderId;
    @Before
    public void setUp() {
        RestAssured.baseURI = URL;
    }
    @Before
    public void createOrder() {

        CreationOrder order = new CreationOrder("Пупырка", "Васильева", "ул Московская 11к2", 1, "+79892522635", 3, "2024-09-15", "Test comment", List.of("BLACK"));

        orderId = given()
                .contentType("application/json")
                .body(order)
                .when()
                .post(ORDER_LIST)
                .then()
                .statusCode(201);
    }

    @Test
    public void testGetOrdersReturnsList() {
        given()
                .contentType("application/json")
                .when()
                .get(ORDER_LIST)
                .then()
                .statusCode(200)
                .body("orders", notNullValue());
    }

    @After
    public void deleteOrder() {
        // Удаление созданного заказа
        given()
                .contentType("application/json")
                .when()
                .delete(ORDER_LIST + orderId)
                .then()
                .statusCode(200);

    }

}


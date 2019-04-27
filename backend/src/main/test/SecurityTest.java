import io.restassured.RestAssured;
import io.restassured.authentication.FormAuthConfig;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class SecurityTest {

    @Test
    public void givenUserWithReadPrivilegeAndHasPermission_whenGetFooById_thenOK() {
        Response response = givenAuth("Lara", "qwerty").get("http://localhost:8088/api/teams/absences/1/?username=Lara");
        assertEquals(200, response.getStatusCode());
    }

    private RequestSpecification givenAuth(String username, String password) {
        FormAuthConfig formAuthConfig =
                new FormAuthConfig("http://localhost:8088/login", "username", "password");

        return RestAssured.given().auth().form(username, password, formAuthConfig);
    }
}

import dto.UserDTO;
import io.restassured.module.jsv.JsonSchemaValidator;
import io.restassured.response.ValidatableResponse;
import org.apache.http.HttpStatus;
import org.junit.jupiter.api.Test;
import services.ServiceApi;

import static org.hamcrest.Matchers.equalTo;


public class CreateNewUserTest {
    ServiceApi userApi = new ServiceApi();
    @Test
    public void createUser(){
        UserDTO userDTO = UserDTO.builder()
                .id(4069l)
                .userStatus(5005l)
                .username("Jack")
                .email("jack@mail.com")
                .build();

       userApi.createUser(userDTO)
                .statusCode(HttpStatus.SC_OK)
                .body("code",equalTo(200))
                .body("type",equalTo("unknown"))
                .body("message",equalTo("4069"))
                .body(JsonSchemaValidator.matchesJsonSchemaInClasspath("schema/CreateUser.json"))
       ;

        ValidatableResponse response = userApi.createUser(userDTO);

        //String actualMessage=response.extract().jsonPath().get("message").toString();

       // Assertions.assertEquals("4069",actualMessage,"Incorrect message");

      /*  UserResponseDTO actualUser = response.extract().body().as(UserResponseDTO.class);
        Assertions.assertAll("Check create user response",
                () -> Assertions.assertEquals(userDTO.getId().toString(),actualUser.getMessage(),"Incorrect message"),
                () -> Assertions.assertEquals(200,actualUser.getCode(),"Incorrect code"),
                () -> Assertions.assertEquals("unknown",actualUser.getType())
        );*/



    }
}

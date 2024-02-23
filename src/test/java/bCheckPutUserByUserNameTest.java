import dto.getUserByName.GetUserByNameResponseDTO;
import dto.updateUser.UpdateUserDTO;
import dto.updateUser.UpdateUserResponseDTO;
import io.restassured.response.ValidatableResponse;
import org.junit.jupiter.api.*;
import services.ServiceApi;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class bCheckPutUserByUserNameTest {
    String userNameI="Jack";
    String firstNameI="Ivan1";
    String lastNameI="Ivanov1";
    ServiceApi userApi = new ServiceApi();

    @Test
    @Order(1)
    public void checkPutUser(){

        ValidatableResponse response = userApi.getUserByName(userNameI);
        GetUserByNameResponseDTO actualData =response.extract().body().as(GetUserByNameResponseDTO.class);

        UpdateUserDTO updateUser = UpdateUserDTO.builder()
         .id(actualData.getId())
         .username(userNameI)
         .firstName(firstNameI)
         .lastName(lastNameI)
         .email(actualData.getEmail())
         .password("")
         .phone("")
         .userStatus(actualData.getUserStatus())
         .build();
        ValidatableResponse responseUpdateUser=userApi.putUser(updateUser,userNameI);
        //Валидация json-схемы ответа
       // responseUpdateUser.body(JsonSchemaValidator.matchesJsonSchemaInClasspath("schema/PutUser.json"));
        //Проверка на код ответа http, проверка id пользователя и типа сообщения
        UpdateUserResponseDTO actualResponse = responseUpdateUser.extract().body().as(UpdateUserResponseDTO.class);
        Assertions.assertAll("Check put user response",
                () ->    Assertions.assertEquals(200,actualResponse.getCode(), "Incorrect http code"),
                () ->    Assertions.assertEquals("unknown",actualResponse.getType(),"Incorrect message type"),
                () ->    Assertions.assertEquals(updateUser.getId().toString(),actualResponse.getMessage(),"Incorrect message type")
                );


   /*     ValidatableResponse response1 = userApi.getUserByName(userNameI);
        GetUserByNameResponseDTO actualData1 =response.extract().body().as(GetUserByNameResponseDTO.class);*/

    }

}

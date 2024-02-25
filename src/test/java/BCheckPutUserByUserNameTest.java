import dto.getuserbyname.GetUserByNameResponseDTO;
import dto.updateuser.UpdateUserDTO;
import dto.updateuser.UpdateUserResponseDTO;
import io.restassured.module.jsv.JsonSchemaValidator;
import io.restassured.response.ValidatableResponse;
import org.junit.jupiter.api.*;


@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class BCheckPutUserByUserNameTest extends GeneralUser{
  String userNameI="Jack";
  String firstNameI="Ivan1";
  String lastNameI="Ivanov1";

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
    responseUpdateUser.body(JsonSchemaValidator.matchesJsonSchemaInClasspath("schema/PutUser.json"));
    //Проверка на код ответа http, проверка id пользователя и типа сообщения
    UpdateUserResponseDTO actualResponse = responseUpdateUser.extract().body().as(UpdateUserResponseDTO.class);
    Assertions.assertAll("Check put user response",
        () ->    Assertions.assertEquals(200,actualResponse.getCode(), "Incorrect http code"),
        () ->    Assertions.assertEquals("unknown",actualResponse.getType(),"Incorrect message type"),
        () ->    Assertions.assertEquals(updateUser.getId().toString(),actualResponse.getMessage(),"Incorrect message type")
    );
  }
  @Test
  @Order(2)
  public void checkPutUserGetUserByUserName(){
    //Проверка корректности обновления пользователя через запрос пользователя по имени
    ValidatableResponse response = userApi.getUserByName(userNameI);
    GetUserByNameResponseDTO actualData =response.extract().body().as(GetUserByNameResponseDTO.class);
    Assertions.assertAll("Check create user from getUserByName",
        () -> Assertions.assertEquals(firstNameI,actualData.getFirstName(),"Incorrect Firstname"),
        () -> Assertions.assertEquals(lastNameI,actualData.getLastName(), "Incorrect LastName")

    );
  }

  @Test
  @Order(3)
  public void checkPutUserNegative404(){
    //Проверка негативного сценария, если пользователь не найден, метод должен возвращать ответ 404
    UpdateUserDTO updateUser = UpdateUserDTO.builder()
                .id(123L)
                .username("")
                .firstName(firstNameI)
                .lastName(lastNameI)
                .email("")
                .password("")
                .phone("")
                .userStatus(111L)
                .build();

    ValidatableResponse responseUpdateUser=userApi.putUser(updateUser,userNameI);
    //Проверка на код ответа http
    UpdateUserResponseDTO actualResponse = responseUpdateUser.extract().body().as(UpdateUserResponseDTO.class);
    Assertions.assertEquals(404,actualResponse.getCode(), "Incorrect http code");
  }


}

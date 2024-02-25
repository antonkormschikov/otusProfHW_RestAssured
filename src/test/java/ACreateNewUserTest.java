import dto.createuser.UserDTO;
import dto.createuser.UserResponseDTO;
import dto.getuserbyname.GetUserByNameResponseDTO;
import io.restassured.module.jsv.JsonSchemaValidator;
import io.restassured.response.ValidatableResponse;
import org.junit.jupiter.api.*;
import services.ServiceApi;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class ACreateNewUserTest extends GeneralUser{
  long userIdI=4069;
  long userStatusI=5005;
  String userNameI="Jack";
  String emailI="jack@mail.com";

  @Test
  @Order(1)
  public void createUser() {
    UserDTO userDTO = UserDTO.builder()
                .id(userIdI)
                .userStatus(userStatusI)
                .username(userNameI)
                .email(emailI)
                .build();

    ValidatableResponse response = userApi.createUser(userDTO);
    //Валидация json-схемы ответа
    response.body(JsonSchemaValidator.matchesJsonSchemaInClasspath("schema/CreateUser.json"));

    UserResponseDTO actualUser = response.extract().body().as(UserResponseDTO.class);
    //Проверка на код ответа http, проверка id пользователя и типа сообщения
    Assertions.assertAll("Check create user response",
        () -> Assertions.assertEquals(userDTO.getId().toString(), actualUser.getMessage(), "Incorrect message"),
        () -> Assertions.assertEquals(200, actualUser.getCode(), "Incorrect code"),
        () -> Assertions.assertEquals("unknown", actualUser.getType(), "Incorrect type")//,
    );
  }

  @Test
  @Order(2)
  public void checkCreateUserGetUserByUserName(){
    //Проверка корректности регистрации пользователя через запрос пользователя по имени
    ValidatableResponse response = userApi.getUserByName(userNameI);
    GetUserByNameResponseDTO actualData =response.extract().body().as(GetUserByNameResponseDTO.class);
    Assertions.assertAll("Check create user from getUserByName",
        () -> Assertions.assertEquals(userNameI,actualData.getUsername(),"Incorrect name"),
        () -> Assertions.assertEquals(userIdI,actualData.getId(), "Incorrect id"),
        () -> Assertions.assertEquals(userStatusI,actualData.getUserStatus(), "Incorrect userStatus"),
        () -> Assertions.assertEquals(emailI,actualData.getEmail(), "incorrect email")
    );
  }

}

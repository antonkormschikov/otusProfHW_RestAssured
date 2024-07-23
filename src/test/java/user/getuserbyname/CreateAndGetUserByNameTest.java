package user.getuserbyname;

import dto.createuser.UserDTO;
import dto.getuserbyname.GetUserByNameResponseDTO;
import io.qameta.allure.Step;
import io.restassured.response.ValidatableResponse;
import org.junit.jupiter.api.*;
import services.ServiceApi;


public class CreateAndGetUserByNameTest {
  ServiceApi userApi = new ServiceApi();
  private long userIdI=4069;
  private long userStatusI=5005;
  private String userNameI="Jack";
  private String emailI="jack@mail.com";


  @BeforeEach
public void createUser() {
    UserDTO userDTO = UserDTO.builder()
          .id(userIdI)
          .userStatus(userStatusI)
          .username(userNameI)
          .email(emailI)
          .build();
    userApi.createUser(userDTO);
  }
  @AfterEach
public void deleteUser(){
    userApi.deleteUser(userNameI);
  }

  @Test
  @Step("checkCreateUserGetUserByUserName")
public void checkCreateUserGetUserByUserName(){
    //Запрос пользователя по имени
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

package user.putuser;

import dto.createuser.UserDTO;
import dto.getuserbyname.GetUserByNameResponseDTO;
import dto.updateuser.UpdateUserDTO;
import dto.updateuser.UpdateUserResponseDTO;
import io.qameta.allure.Step;
import io.restassured.response.ValidatableResponse;
import org.junit.jupiter.api.*;
import services.ServiceApi;



public class CheckPutUserTest {
  ServiceApi userApi = new ServiceApi();
  private String userNameI="Jack";
  private String firstNameI="Ivan1";
  private String lastNameI="Ivanov1";
  private long userIdI=4069;
  private long userStatusI=5005;
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
  @Step("checkPutUser")
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

    //Проверка на код ответа http, проверка id пользователя и типа сообщения
    UpdateUserResponseDTO actualResponse = responseUpdateUser.extract().body().as(UpdateUserResponseDTO.class);
    Assertions.assertAll("Check put user response",
        () ->    Assertions.assertEquals(200,actualResponse.getCode(), "Incorrect http code"),
        () ->    Assertions.assertEquals("unknown",actualResponse.getType(),"Incorrect message type"),
        () ->    Assertions.assertEquals(updateUser.getId().toString(),actualResponse.getMessage(),"Incorrect message type")
    );

    //Проверка корректности обновления пользователя через запрос пользователя по имени
    ValidatableResponse getResponse = userApi.getUserByName(userNameI);
    GetUserByNameResponseDTO getActualData =getResponse.extract().body().as(GetUserByNameResponseDTO.class);
    Assertions.assertAll("Check create user from getUserByName",
        () -> Assertions.assertEquals(firstNameI,getActualData.getFirstName(),"Incorrect Firstname"),
        () -> Assertions.assertEquals(lastNameI,getActualData.getLastName(), "Incorrect LastName")

    );
  }

}

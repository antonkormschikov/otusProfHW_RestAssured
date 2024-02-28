package user.getuserbyname;

import dto.createuser.UserDTO;
import dto.getuserbyname.GetUserByNameResponse404DTO;
import dto.getuserbyname.GetUserByNameResponseDTO;
import io.restassured.response.ValidatableResponse;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import services.ServiceApi;

public class CreateAndGetUserByNameNegativeTest {
  ServiceApi userApi = new ServiceApi();
  private long userIdI=4069;
  private long userStatusI=5005;
  private String userNameI="Jack";
  private String emailI="jack@mail.com";

  @Test
public void checkGetUserByUserName404(){
    //Проверка запроса пользователя по несуществующему имени
    ValidatableResponse response = userApi.getUserByName404(userNameI+"007");
    GetUserByNameResponse404DTO actualData =response.extract().body().as(GetUserByNameResponse404DTO.class);
    Assertions.assertAll("Check error 404 getUserByName",
        () -> Assertions.assertEquals(1,actualData.getCode(),"Incorrect code"),
        () -> Assertions.assertEquals("error",actualData.getType(),"Incorrect type"),
        () -> Assertions.assertEquals("User not found",actualData.getMessage(),"Incorrect message")
    );
  }
}

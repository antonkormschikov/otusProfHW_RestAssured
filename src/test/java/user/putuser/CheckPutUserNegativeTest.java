package user.putuser;

import dto.updateuser.UpdateUserDTO;
import dto.updateuser.UpdateUserResponseDTO;
import io.restassured.response.ValidatableResponse;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import services.ServiceApi;

public class CheckPutUserNegativeTest {
  ServiceApi userApi = new ServiceApi();
  @Test

  public void checkPutUserNegative404(){
    //Проверка негативного сценария, если пользователь не найден, метод должен возвращать ответ 404
    UpdateUserDTO updateUser = UpdateUserDTO.builder()
                .id(123L)
                .username("///")
                .firstName("firstName")
                .lastName("lastName")
                .email("")
                .password("")
                .phone("")
                .userStatus(111L)
                .build();

    ValidatableResponse responseUpdateUser=userApi.putUser(updateUser,"///");
    //Проверка на код ответа http
    UpdateUserResponseDTO actualResponse = responseUpdateUser.extract().body().as(UpdateUserResponseDTO.class);
    Assertions.assertEquals(404,actualResponse.getCode(), "Incorrect http code");
  }
}

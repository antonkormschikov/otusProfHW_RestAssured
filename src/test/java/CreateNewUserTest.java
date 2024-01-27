import dto.UserDTO;
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
                .body("message",equalTo("4069"));



    }
}

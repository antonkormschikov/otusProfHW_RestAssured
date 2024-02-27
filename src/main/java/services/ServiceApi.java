package services;

import static io.restassured.RestAssured.given;

import dto.createuser.UserDTO;
import dto.updateuser.UpdateUserDTO;
import io.restassured.http.ContentType;
import io.restassured.response.ValidatableResponse;
import io.restassured.specification.RequestSpecification;

public class ServiceApi {
  private static final String BASE_URL=System.getProperty("base.url");
  private static final String BASE_PATH="/user";
  private RequestSpecification spec;
  public ServiceApi(){
    spec=given()
                .baseUri(BASE_URL)
                .basePath(BASE_PATH)
                .contentType(ContentType.JSON)
                .log().all();
  }
  public ValidatableResponse createUser(UserDTO user){

    return  given(spec)
                .body(user)
                .when()
                .post()
                .then()
                .log().all();
  }
  public ValidatableResponse getUserByName(String userByName){
    return given(spec)
                .when()
                .get("/"+userByName)
                .then()
                .log().all();
  }

  public ValidatableResponse putUser(UpdateUserDTO updateUser, String userName){
    return given(spec)
                .body(updateUser)
                .when()
                .put("/"+userName)
                .then()
                .log().all();
  }

  public ValidatableResponse deleteUser(String userByName){
    return given(spec)
            .when()
            .delete("/"+userByName)
            .then()
            .log().all();
  }
}

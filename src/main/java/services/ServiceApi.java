package services;

import static io.restassured.RestAssured.given;

import dto.createuser.UserDTO;
import dto.updateuser.UpdateUserDTO;
import io.restassured.http.ContentType;
import io.restassured.response.ValidatableResponse;
import io.restassured.specification.RequestSpecification;

public class ServiceApi {
  private static final String BASE_URL="https://petstore.swagger.io/v2";
  private static final String BASE_PATH="/user";
  private RequestSpecification spec;
  public ServiceApi(){
    spec=given()
                .baseUri(BASE_URL)
                .contentType(ContentType.JSON)
                .log().all();
  }
  public ValidatableResponse createUser(UserDTO user){

    return  given(spec)
                .basePath(BASE_PATH)
                .body(user)
                .when()
                .post()
                .then()
                .log().all();
  }
  public ValidatableResponse getUserByName(String userByName){
    return given(spec)
                .basePath(BASE_PATH)
                .when()
                .get("/"+userByName)
                .then()
                .log().all();
  }

  public ValidatableResponse putUser(UpdateUserDTO updateUser, String userName){
    return given(spec)
                .basePath(BASE_PATH)
                .body(updateUser)
                .when()
                .put("/"+userName)
                .then()
                .log().all();
  }
}

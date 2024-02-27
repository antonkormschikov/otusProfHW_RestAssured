
package dto.getuserbyname;

import javax.annotation.Generated;

import com.fasterxml.jackson.annotation.JsonInclude;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@JsonInclude(JsonInclude.Include.NON_DEFAULT)
@AllArgsConstructor
@NoArgsConstructor
public class GetUserByNameResponse404DTO {

  private Long code;
  private String message;
  private String type;

}

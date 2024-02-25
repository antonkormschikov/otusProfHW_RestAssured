package dto.updateuser;

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
public class UpdateUserDTO {
  private String email;
  private String firstName;
  private Long id;
  private String lastName;
  private String password;
  private String phone;
  private Long userStatus;
  private String username;
}

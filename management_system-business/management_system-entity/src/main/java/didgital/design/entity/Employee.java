package didgital.design.entity;

import didgital.design.common.enumerate.StatusEmployee;
import lombok.*;


@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Builder
public class Employee implements Entity {

    private Long id;

    private String lastName;

    private String firstName;

    private String surname;

    private String jodTitle;

    private String username;

    private String email;

    private StatusEmployee status;
}

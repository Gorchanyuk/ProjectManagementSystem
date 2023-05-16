package didgital.design.management.system.entity;

import didgital.design.management.system.common.enumerate.StatusEmployee;
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

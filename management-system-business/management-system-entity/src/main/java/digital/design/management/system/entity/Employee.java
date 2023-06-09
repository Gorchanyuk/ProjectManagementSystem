package digital.design.management.system.entity;

import digital.design.management.system.common.enumerate.StatusEmployee;
import jakarta.persistence.*;
import lombok.*;

import java.util.UUID;

@Entity
@Builder
@Getter
@Setter
@EqualsAndHashCode
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "employee")
public class Employee {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "uid")
    private UUID uid;

    @Column(name = "password")
    private String password;        //Пароль

    @Column(name = "last_name", nullable = false)
    private String lastName;        //Фамилия

    @Column(name = "first_name", nullable = false)
    private String firstName;       //Имя

    @Column(name = "surname")
    private String surname;         //Отчество

    @Column(name = "job_title")
    private String jobTitle;        //Должность

    @Column(name = "username")
    private String username;         //Учетная запись

    @Column(name = "email")
    private String email;           //Адрес электронной почты

    @Column(name = "status", nullable = false)
    @Enumerated(EnumType.STRING)
    private StatusEmployee status;  //Статус сотрудника

}

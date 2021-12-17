package com.sweeftacceleration.model.system;

import com.sweeftacceleration.model.Server;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.Date;
import java.util.List;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class SystemUser {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private String firstName;

    private String lastName;

    @Column(unique = true)
    private String userName;

    private int age;

    private Date birthDay;

    private String password;

    private String email;

    private Boolean active;

    private String randomNumber;

    @ManyToMany
    private List<UserRole> userRole;

    @OneToMany(mappedBy = "systemUser")
    private List<Server> serverList;
}

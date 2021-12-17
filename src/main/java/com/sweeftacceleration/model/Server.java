package com.sweeftacceleration.model;

import com.sweeftacceleration.model.system.SystemUser;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.Date;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Server {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    private String serverName;

    private double volume;

    private boolean status;

    private Date serverStartDate;

    private Date serverEndDate;

    @ManyToOne()
    private SystemUser systemUser;

}

package com.example.demo.entity;

//import jakarta.persistence.GeneratedValue;
//import jakarta.persistence.GenerationType;
//import jakarta.persistence.Id;
import lombok.Builder;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.List;

@Setter
@Getter
@Data
@Builder
@Document(collection="user")
public class User {

    @Id
    public String id;

    public String username;
    public String password;
    @Getter
    @DBRef
    public List<Role> roles;

    //    public User(){}
//
//    public User(String username, String password) {
//        this.username = username;
//        this.password = password;
//    }

}

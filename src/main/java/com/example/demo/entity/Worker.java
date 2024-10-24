package com.example.demo.entity;

import com.fasterxml.jackson.databind.annotation.JsonPOJOBuilder;
//import jakarta.persistence.GeneratedValue;
//import jakarta.persistence.GenerationType;
//import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Document(collection = "workers")
public class Worker {

    @Id
    private String id;
    //    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private String name;
    private String surname;
    @DBRef
    private List<Job> jobs;

}

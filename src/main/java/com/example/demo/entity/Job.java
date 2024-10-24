package com.example.demo.entity;

import lombok.Builder;
import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Data
@Builder
@Document(collection="job")
public class Job {

    @Id
    private String id;
    private String jobName;
    private String jobDescription;

}

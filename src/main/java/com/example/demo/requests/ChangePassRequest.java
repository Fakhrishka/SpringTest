package com.example.demo.requests;

import jakarta.validation.constraints.NotBlank;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class ChangePassRequest {

    @NotBlank
    String oldPass;
    @NotBlank
    String newPass;
    @NotBlank
    String newPassReconfirm;
}

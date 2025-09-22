package com.example.springboot.Features.Auth.dtos;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class VerifiedOtp {
    public String email;
    public String otptCode;

    public VerifiedOtp(
            String email,
            String optCode
    )
    {
        this.email = email;
        this.otptCode = optCode;
    }
}

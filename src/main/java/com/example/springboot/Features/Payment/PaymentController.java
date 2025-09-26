package com.example.springboot.Features.Payment;

import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;

@RestController
@Tag(name = "Payment")
@RequestMapping("/api/v1/payment")
@RequiredArgsConstructor
public class PaymentController 
{
    private final VNPayConfig _config;
    private final PaymentService _service;
    
    @GetMapping(name = "/config")
    public ResponseEntity<?> config()
    {
        return ResponseEntity.status(HttpStatus.OK).body(_config);
    }
    
    @GetMapping("/vn-pay")
    public String createPayment(
            HttpServletRequest request,
            @RequestParam String amount, 
            @RequestParam String bankCode,
            HttpServletResponse response
    ) throws IOException {
        var url = _service.createPayment(request);
        return url;
    }
    
    @GetMapping("/vn-pay/return")
    public ResponseEntity<?> returnPayement(HttpServletRequest request)
    {
       String status = request.getParameter("vnp_ResponseCode");
       if(status.equals("00"))
       {
           return ResponseEntity.status(HttpStatus.OK).body("thanh toan ok");
       }
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("sth is wrong");
    }
}

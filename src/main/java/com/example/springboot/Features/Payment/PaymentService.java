package com.example.springboot.Features.Payment;

import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.io.UnsupportedEncodingException;
import java.text.SimpleDateFormat;
import java.util.*;

@Service
@RequiredArgsConstructor
@Slf4j
public class PaymentService 
{
    private final VNPayConfig _config;
    
    @SneakyThrows
    public String createPayment(HttpServletRequest request) 
    {
        long amount = Long.parseLong(request.getParameter("amount")) * 100;

        Map<String, String> vnp_Params = new HashMap<>();
        vnp_Params.put("vnp_Version"  , _config.getVnp_Version());
        vnp_Params.put("vnp_Command"  , _config.getVnp_Command());
        vnp_Params.put("vnp_TmnCode"  , _config.getVnp_TmnCode());
        vnp_Params.put("vnp_Amount"   ,  String.valueOf(amount));
        vnp_Params.put("vnp_CurrCode" , "VND");

        vnp_Params.put("vnp_BankCode"  , request.getParameter("bankCode"));
        vnp_Params.put("vnp_TxnRef"    , VNPayUtil.getRandomNumber(8));
        vnp_Params.put("vnp_OrderInfo" , "Thanh toan don hang:" + VNPayUtil.getRandomNumber(8));
        vnp_Params.put("vnp_OrderType" , "other");
        vnp_Params.put("vnp_Locale"    , "vn");
        vnp_Params.put("vnp_ReturnUrl" , _config.getVnp_ReturnUrl());
        vnp_Params.put("vnp_IpAddr"    , VNPayUtil.getIpAddress(request));

        Calendar cld = Calendar.getInstance(TimeZone.getTimeZone("Etc/GMT+7"));
        SimpleDateFormat formatter = new SimpleDateFormat("yyyyMMddHHmmss");
        String vnp_CreateDate = formatter.format(cld.getTime());
        vnp_Params.put("vnp_CreateDate", vnp_CreateDate);

        cld.add(Calendar.MINUTE, 15);
        String vnp_ExpireDate = formatter.format(cld.getTime());
        vnp_Params.put("vnp_ExpireDate", vnp_ExpireDate);
        
        String queryUrl = VNPayUtil.getPaymentURL(vnp_Params, true);
        String hashData = VNPayUtil.getPaymentURL(vnp_Params, false);

        String vnp_SecureHash = VNPayUtil.hmacSHA512("336P5KG6WRPHNKGCN4KY4J6GCKMTX9T6", hashData.toString());

        queryUrl += "&vnp_SecureHash=" + vnp_SecureHash;
        String paymentUrl = _config.getVnp_PayUrl() + "?" + queryUrl;
        
        log.info(paymentUrl);
        
        return paymentUrl;
    }
}

package com.example.springboot.AuthAPI.Auth;

import com.example.springboot.AuthAPI.Account.Account;
import com.example.springboot.AuthAPI.Account.AccountRepository;
import com.example.springboot.AuthAPI.Auth.dtos.LoginDTO;
import com.example.springboot.AuthAPI.Auth.dtos.RegisterDTO;
import com.example.springboot.AuthAPI.Auth.helper.PasswordHasherBcrypt;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class AuthSerivce {
    private final AccountRepository _accountRepository;
    private final PasswordHasherBcrypt _passwordHasher;
    
    public void register(RegisterDTO request)
    {
        Account account = new Account();
        account.setUsername(request.username);
        account.setPassword(_passwordHasher.encodePassword(request.password));
        _accountRepository.save(account);
    }

    public void login(LoginDTO request) throws Exception {
        var account = _accountRepository.findByUsername(request.username);
        if(account == null) throw new Exception("Can't find username");
        
        boolean isPassword = _passwordHasher.validatePassword(request.password, account.getPassword());
        if(!isPassword) throw new Exception("Password is not correct");
    }
}


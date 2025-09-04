//package com.example.springboot.AuthAPI.Auth;
//
//import com.example.springboot.AuthAPI.Account.AccountRepository;
//import com.example.springboot.Core.ConvertUtils;
//import com.example.springboot.Core.CustomJson;
//import com.example.springboot.Core.CustomJsonOptions;
//import lombok.RequiredArgsConstructor;
//import lombok.extern.slf4j.Slf4j;
//import org.springframework.security.core.GrantedAuthority;
//import org.springframework.security.core.authority.SimpleGrantedAuthority;
//import org.springframework.security.core.userdetails.UserDetails;
//import org.springframework.security.core.userdetails.UserDetailsService;
//import org.springframework.security.core.userdetails.UsernameNotFoundException;
//import org.springframework.stereotype.Service;
//
//import java.util.ArrayList;
//
//@Slf4j
//@Service
//@RequiredArgsConstructor
//public class CustomAccountService implements UserDetailsService {
//    private final AccountRepository _accountRepo;
//
//    @Override
//    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
//        var account = _accountRepo.find(username).orElseThrow(() -> new RuntimeException("Wtf is this")).getFirst();
//        log.info(CustomJson.json(account, CustomJsonOptions.WRITE_INDENTED));
//        if(account == null) throw new UsernameNotFoundException("account is not found");
//
//        var authorities = new ArrayList<GrantedAuthority>();
//        authorities.add(new SimpleGrantedAuthority(account.get("roleName").toString()));
//        return new org.springframework.security.core.userdetails.User(
//                account.get("username").toString(),
//                account.get("password").toString(),
//                authorities
//        );
//    }
//}

package com.i1nfo.icb.controller;

import com.i1nfo.icb.component.JWTUtils;
import com.i1nfo.icb.model.User;
import com.i1nfo.icb.service.LoginService;
import com.i1nfo.icb.validate.UserLogin;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.security.NoSuchAlgorithmException;

@RestController
@RequestMapping("/api/auth")
public class LoginController {

    private final LoginService loginService;

    private final JWTUtils jwt;

    @Autowired
    public LoginController(LoginService loginService, JWTUtils jwt) {
        this.loginService = loginService;
        this.jwt = jwt;
    }


    @PostMapping("/login")
    public ResponseEntity<Object> login(@RequestBody @Validated(UserLogin.class) User user) throws NoSuchAlgorithmException {
        Long id = loginService.getIdByNameAndPass(user.getName(), user.getPasswd());
        if (id == 0)
            return ResponseEntity.notFound().build();
        return ResponseEntity.ok().header("Authorization", jwt.createToken(String.valueOf(id))).build();
    }

}

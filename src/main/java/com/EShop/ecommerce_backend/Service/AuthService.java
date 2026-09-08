package com.EShop.ecommerce_backend.Service;

import com.EShop.ecommerce_backend.Security.Login.LoginRequest;
import com.EShop.ecommerce_backend.Security.Login.SignupRequest;
import jakarta.validation.Valid;
import org.springframework.http.ResponseCookie;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;

public interface AuthService {

    ResponseEntity<?> login(LoginRequest loginRequest);

    ResponseEntity<?> register(@Valid SignupRequest signUpRequest);

    ResponseEntity<?> getUserDetail(Authentication authentication);

    ResponseCookie logoutUser();
}

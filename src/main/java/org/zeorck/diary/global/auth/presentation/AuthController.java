package org.zeorck.diary.global.auth.presentation;

import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.zeorck.diary.global.auth.application.AuthService;
import org.zeorck.diary.global.auth.dto.request.LoginRequest;
import org.zeorck.diary.global.auth.dto.response.LoginResultResponse;

@RequiredArgsConstructor
@RestController
public class AuthController {

    private final AuthService authService;

    @PostMapping("/login")
    public ResponseEntity<LoginResultResponse> login(
            @Valid @RequestBody LoginRequest loginRequest,
            HttpServletResponse response
    ) {
        LoginResultResponse result = authService.login(
                loginRequest.email(),
                loginRequest.password(),
                response
        );

        return new ResponseEntity<>(result, HttpStatus.OK);
    }
}

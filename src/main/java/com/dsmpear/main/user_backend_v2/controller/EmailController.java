package com.dsmpear.main.user_backend_v2.controller;

import com.dsmpear.main.user_backend_v2.config.validation.ValidateEmail;
import com.dsmpear.main.user_backend_v2.payload.request.EmailVerifyRequest;
import com.dsmpear.main.user_backend_v2.payload.request.NotificationRequest;
import com.dsmpear.main.user_backend_v2.service.email.EmailService;
import com.dsmpear.main.user_backend_v2.service.user.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.ConstraintViolationException;
import javax.validation.Valid;
import javax.validation.constraints.Email;

@RequestMapping("/email")
@Validated
@RequiredArgsConstructor
@RestController
public class EmailController {

    private final UserService userService;
    private final EmailService emailService;

    @GetMapping("/auth")
    public void authNumEmail(@RequestParam("email") @Email @ValidateEmail String email) {
        emailService.sendAuthNumEmail(email);
    }

    @PutMapping("/auth")
    public void verifyAccount(@RequestBody @Valid EmailVerifyRequest request) {
        userService.verify(request);
    }

    @PostMapping("/notification")
    public void notification(@RequestBody @Valid NotificationRequest request, @RequestHeader("Authorization") String secretKey) {
        emailService.sendNotification(request, secretKey);
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(ConstraintViolationException.class)
    public Object exception(Exception e) {
        return e.getMessage();
    }

}

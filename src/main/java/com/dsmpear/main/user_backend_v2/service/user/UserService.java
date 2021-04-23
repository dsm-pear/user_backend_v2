package com.dsmpear.main.user_backend_v2.service.user;

import com.dsmpear.main.user_backend_v2.payload.request.EmailVerifyRequest;
import com.dsmpear.main.user_backend_v2.payload.request.RegisterRequest;

public interface UserService {
    void register(RegisterRequest request);
    void verify(EmailVerifyRequest request);
}

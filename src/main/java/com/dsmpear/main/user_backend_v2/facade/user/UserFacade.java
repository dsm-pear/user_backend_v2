package com.dsmpear.main.user_backend_v2.facade.user;

import com.dsmpear.main.user_backend_v2.entity.user.User;

public interface UserFacade {
    User createAuthUser();
    User createUser(String email);
}

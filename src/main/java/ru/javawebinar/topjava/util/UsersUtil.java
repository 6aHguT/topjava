package ru.javawebinar.topjava.util;

import ru.javawebinar.topjava.model.Role;
import ru.javawebinar.topjava.model.User;

import java.util.Arrays;
import java.util.List;

public class UsersUtil {
    public static final List<User> USERS = Arrays.asList(
            new User(null, "userName_1", "email_1", "password", Role.ROLE_ADMIN),
            new User(null, "userName_2", "email_2", "password", Role.ROLE_USER)
    );
}

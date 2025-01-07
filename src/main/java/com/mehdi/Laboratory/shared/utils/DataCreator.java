package com.mehdi.Laboratory.shared.utils;

import com.mehdi.Laboratory.domain.JpaUser;
import com.mehdi.Laboratory.repository.UserRepository;
import com.mehdi.Laboratory.service.UserServiceImpl;
import org.apache.catalina.User;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

@Component
public class DataCreator {

    private final UserRepository userRepository;

    public DataCreator(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public void createData() {
        List<JpaUser> users = IntStream.rangeClosed(1, 10).mapToObj(this::createUser).toList();
        userRepository.saveAll(users);
    }

    public void createTestData() {
        JpaUser user = new JpaUser("firstname", "lastname", "dupusername", new Date(), "pass");
        userRepository.save(user);
    }


    private JpaUser createUser(int i) {
        return new JpaUser("FirstName" + i, "LastName" + i, "user" + i, new Date(), "password");
    }
}

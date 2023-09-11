package com.cardmonix.cardmonix.domain.repository;

import com.cardmonix.cardmonix.domain.constant.Role;
import com.cardmonix.cardmonix.domain.entity.userModel.User;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;


import java.util.Optional;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
public class UserRepositoryTest {

    @Autowired
    private UserRepository userRepository;

    @Test
    public void saveEntityTest() {
        User savedUser = saveUser();
        Assertions.assertThat(savedUser).isNotNull();
        Assertions.assertThat(savedUser.getId()).isNotNull();
    }

    @Test
    public void findByEmailTest() {
        Optional<User> find = userRepository.findByEmail("chiorlujack2gmail.com");
        System.out.println(find);
        Assertions.assertThat(find).isEmpty();
    }

    @Test
    public void existsByEmailTest(){
        User test1 = saveUser();
        boolean exist =  userRepository.existsByEmail(test1.getEmail());
        Assertions.assertThat(exist).isEqualTo(true);
    }


    @Test
    public void findUserByTestTest(){
        User test1 = saveUser();
        User list = userRepository.findById(test1.getId()).get();
        Assertions.assertThat(list).isEqualTo(test1);

    }

    @Test
    public void DeleteById(){
        User test1 = saveUser();
        userRepository.deleteById(test1.getId());
        Optional<User> getUser = userRepository.findById(test1.getId());
        Assertions.assertThat(getUser.isPresent()).isEqualTo(false);
        Assertions.assertThat(getUser.isEmpty());
    }
    public User saveUser(){
        return userRepository.save( User.builder()
                .user_name("jwsven")
                .activate(false)
                .email("chiorlujack@gmail.com")
                .role(Role.USER)
                .password("1234")
                .build());
    }
}

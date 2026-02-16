package com.stockinsight.domain.user.repository;

import com.stockinsight.domain.user.entity.Provider;
import com.stockinsight.domain.user.entity.User;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
class UserRepositoryTest {

    @Autowired
    private UserRepository userRepository;

    @Test
    @DisplayName("회원가입 테스트")
    void saveUser() {
        // given
        User newUser = User.builder()
                .email("test@test.com")
                .name("테스트")
                .provider(Provider.KAKAO)
                .providerId("123456789")
                .build();

        // when
        User savedUser = userRepository.save(newUser);

        // then
        assertThat(savedUser.getId()).isNotNull();

        User foundUser = userRepository.findByEmail("test@test.com")
                .orElseThrow(() -> new IllegalArgumentException("가입되지 않은 사용자입니다."));

        assertThat(foundUser.getName()).isEqualTo("테스트");
        assertThat(foundUser.getProvider()).isEqualTo(Provider.KAKAO);
    }

    @Test
    @DisplayName("중복 가입 방지 테스트")
    void findUser() {
        // given
        User existingUser = User.builder()
                .email("test2@test.com")
                .name("테스트2")
                .provider(Provider.KAKAO)
                .providerId("987654321")
                .build();

        userRepository.save(existingUser);

        // when
        boolean exists = userRepository.findByEmail("test2@test.com").isPresent();

        // then
        assertThat(exists).isTrue();
    }
}
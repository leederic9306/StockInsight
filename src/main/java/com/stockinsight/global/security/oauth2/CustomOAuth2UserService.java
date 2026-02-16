package com.stockinsight.global.security.oauth2;

import com.stockinsight.domain.user.entity.Provider;
import com.stockinsight.domain.user.entity.User;
import com.stockinsight.domain.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;

import java.util.Map;

@Slf4j
@Service
@RequiredArgsConstructor
public class CustomOAuth2UserService extends DefaultOAuth2UserService {

    private final UserRepository userRepository;

    @Override
    public OAuth2User loadUser(OAuth2UserRequest userRequest) throws OAuth2AuthenticationException {
        OAuth2User oAuth2User = super.loadUser(userRequest);
        //차후, 구글 로그인 추가할 때 registrationId로 구분하여 처리할 수 있도록 함
        String registrationId = userRequest.getClientRegistration().getRegistrationId();

        Map<String, Object> attributes = oAuth2User.getAttributes();

        String providerId = String.valueOf(attributes.get("id")); // 카카오 고유 ID
        Map<String, Object> kakaoAccount = (Map<String, Object>) attributes.get("kakao_account");
        Map<String, Object> profile = (Map<String, Object>) kakaoAccount.get("profile");

        String email = (String) kakaoAccount.get("email");
        String name = (String) profile.get("nickname");

        log.info("카카오 로그인 시도: email={}, name={}", email, name);

        User user = userRepository.findByEmail(email)
                .orElseGet(() -> saveNewUser(email, name, providerId));

        return oAuth2User;
    }

    private User saveNewUser(String email, String name, String providerId) {
        User newUser = User.builder()
                .email(email)
                .name(name)
                .provider(Provider.KAKAO)
                .providerId(providerId)
                .build();
        return userRepository.save(newUser);
    }
}
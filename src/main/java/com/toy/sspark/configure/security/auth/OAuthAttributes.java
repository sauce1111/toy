package com.toy.sspark.configure.security.auth;

import com.toy.sspark.domain.user.Role;
import com.toy.sspark.domain.user.User;
import lombok.Builder;
import lombok.Getter;

import java.util.Map;

@Getter
public class OAuthAttributes {

    private Map<String, Object> attributes;
    private String nameAttributesKey;
    private String name;
    private String email;
    private String picture;

    @Builder
    public OAuthAttributes(Map<String, Object> attributes,
                           String nameAttributesKey, String name,
                           String email, String picture) {
        this.attributes = attributes;
        this.nameAttributesKey = nameAttributesKey;
        this.name = name;
        this.email = email;
        this.picture = picture;
    }

    public static OAuthAttributes of(String registrationId,
                                     String userNameAttributeName,
                                     Map<String, Object> attributes) {

        if ("naver".equals(registrationId)) {
            return ofNaver("id", attributes);
        }
        return ofGoogle(userNameAttributeName, attributes);
    }

    private static OAuthAttributes ofNaver(String userNameAttributeName, Map<String, Object> attributes) {
        Map<String, Object> response = (Map<String, Object>) attributes.get("response");

        return OAuthAttributes.builder()
                .name((String) response.get("name"))
                .email((String) response.get("email"))
                .picture((String) response.get("profile_image"))
                .attributes(response)
                .nameAttributesKey(userNameAttributeName)
                .build();
    }

    private static OAuthAttributes ofGoogle(String userNameAttributeName, Map<String, Object> attributes) {
        return OAuthAttributes.builder()
                .name((String) attributes.get("name"))
                .email((String) attributes.get("email"))
                .picture((String) attributes.get("picture"))
                .attributes(attributes)
                .nameAttributesKey(userNameAttributeName)
                .build();
    }

    public User toEntity() {
        return User.builder()
                .name(name)
                .email(email)
                .picture(picture)
                .role(Role.USER)
                .build();
    }
}

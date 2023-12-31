package ru.niu.itmo.feedback.security.provider;


import org.springframework.security.authentication.AbstractAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import ru.niu.itmo.feedback.security.details.CustomUserDetails;

import java.util.Collection;
import java.util.Collections;

public class JwtAuthenticationToken extends AbstractAuthenticationToken {
    private final String jwt;

    public JwtAuthenticationToken(String jwt) {
        super(Collections.emptyList());
        this.jwt = jwt;
    }

    public JwtAuthenticationToken(String jwt, Collection<? extends GrantedAuthority> authorities) {
        super(authorities);
        this.jwt = jwt;
    }

    @Override
    public Object getCredentials() {
        return jwt;
    }

    @Override
    public Object getPrincipal() {
        return jwt;
    }

    @Override
    public CustomUserDetails getDetails() {
        return (CustomUserDetails) super.getDetails();
    }
}

package ru.niu.itmo.feedback.security.provider;



import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetails;
import ru.niu.itmo.feedback.security.details.UserDetailsServiceImpl;
import ru.niu.itmo.feedback.security.exceptions.JwtAuthenticationException;
import ru.niu.itmo.feedback.service.JwtHelper;


@RequiredArgsConstructor
public class JwtAuthenticationProvider implements AuthenticationProvider {
    private final JwtHelper jwtHelper;
    private final UserDetailsServiceImpl userService;

    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        try {
            JwtAuthenticationToken authToken = (JwtAuthenticationToken) authentication;
            String token = (String) authToken.getCredentials();
            jwtHelper.validateAccessToken(token);
            String userIdFromAccessToken = jwtHelper.getUserIdFromAccessToken(token);
            UserDetails user = userService.loadById(Long.valueOf(userIdFromAccessToken));
            authToken = new JwtAuthenticationToken(token, user.getAuthorities());
            authToken.setDetails(user);
            authToken.setAuthenticated(true);
            return authToken;
        } catch (Exception e) {
            throw new JwtAuthenticationException("Invalid access token");
        }
    }

    @Override
    public boolean supports(Class<?> authentication) {
        return JwtAuthenticationToken.class.isAssignableFrom(authentication);
    }

}

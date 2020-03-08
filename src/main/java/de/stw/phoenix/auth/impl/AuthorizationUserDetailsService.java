package de.stw.phoenix.auth.impl;

import de.stw.phoenix.auth.api.AuthService;
import de.stw.phoenix.user.api.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.userdetails.AuthenticationUserDetailsService;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.web.authentication.preauth.PreAuthenticatedAuthenticationToken;

import java.util.Optional;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class AuthorizationUserDetailsService implements AuthenticationUserDetailsService<PreAuthenticatedAuthenticationToken> {

    private static final Pattern AUTHORIZATION_HEADER_PATTERN = Pattern.compile("Bearer (\\S+)");

    @Autowired
    private AuthService authService;

    private static final Logger LOG = LoggerFactory.getLogger(AuthorizationUserDetailsService.class);

    @Override
    public UserDetails loadUserDetails(PreAuthenticatedAuthenticationToken token) throws UsernameNotFoundException {
        final String authorizationHeader = token.getCredentials().toString();
        LOG.info("Loading user for Authorization header: " + authorizationHeader);

        // Check authentication scheme
        if (!authorizationHeader.startsWith("Bearer")) {
            throw new AuthorizationHeaderException("Invalid authentication scheme found in Authorization header");
        }

        // Check that the Authorization header matches the pattern
        Matcher matcher = AUTHORIZATION_HEADER_PATTERN.matcher(authorizationHeader);
        if (!matcher.matches()) {
            throw new AuthorizationHeaderException("Unable to parse token from Authorization header");
        }

        return loadUserDetails(matcher.group());
    }

    private UserDetails loadUserDetails(String token) {
        final User user = Optional.ofNullable(token)
                .map(String::valueOf)
                .map(t -> t.replaceFirst("Bearer", "").trim())
                .flatMap(authService::findAuthenticatedUser)
                .orElseThrow(() -> new UsernameNotFoundException(String.format("Cannot find user with authentication token = '%s'", token)));
        final org.springframework.security.core.userdetails.User userDetails = new org.springframework.security.core.userdetails.User(
                user.getUsername(), "password", true, true, true, true,
                AuthorityUtils.createAuthorityList("USER"));
        return userDetails;
    }

}

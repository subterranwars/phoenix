package de.stw.phoenix.auth.impl;

import de.stw.phoenix.user.api.UserRepository;

class DefaultAuthServiceTest {

    private UserRepository userRepository;
    private DefaultAuthService authService;

//    @BeforeEach
//    public void before() {
//        final User user = User.builder().id(1).username("test").password("test").email("test@subterranwars.de").build();
//        userRepository = new DefaultUserRepository(new BCryptPasswordEncoder(10));
//        userRepository.save(user);
//        authService = new DefaultAuthService(userRepository, Duration.ofSeconds(10));
//    }
//
//    @Test
//    public void verifyAuth() {
//        final Token token = authService.authenticate("test", "test");
//        assertThat(token, notNullValue());
//        assertThat(token.isValid(Instant.now()), is(true));
//        assertThat(token.getToken(), notNullValue());
//
//        // Verify lookup
//        final Optional<User> user = authService.findAuthenticatedUser(token.getToken());
//        assertThat(user.isPresent(), is(true));
//        assertThat(user.get().getUsername(), is("test"));
//
//        // Ensure that the token is not valid anymore
//        assertThat(token.isValid(Instant.now().plus(Duration.ofSeconds(10))), is(false));
//    }
//
//    @Test
//    public void verifyLookupInvalidatesProperly() throws InterruptedException {
//        // Authenticate and verify user is actually authenticated
//        final Token token = authService.authenticate("test", "test");
//        final Optional<User> user = authService.findAuthenticatedUser(token.getToken());
//        assertThat(user.isPresent(), is(true));
//
//        // Wait for token to be expired
//        Thread.sleep(12 * 1000);
//
//        // Verify lookup now does not work as token is expired
//        assertThat(authService.findAuthenticatedUser(token.getToken()).isPresent(), is(false));
//    }
//
//    @Test
//    public void verifyInvalidCredentials() {
//        assertThrows(BadCredentialsException.class, () -> authService.authenticate("test", "test1234"));
//    }
//
//    @Test
//    public void verifyInvalidate() {
//        final Token token = authService.authenticate("test", "test");
//        authService.invalidate(token);
//        assertThat(authService.findAuthenticatedUser(token.getToken()).isPresent(), is(false));
//        assertThat(authService.findToken("test").isPresent(), is(false));
//    }
//
//    @Test
//    public void verifyInvalidateOnReauth() {
//        final Token token = authService.authenticate("test", "test");
//        final Token anotherToken = authService.authenticate("test", "test");
//        assertThat(token, notNullValue());
//        assertThat(anotherToken, notNullValue());
//        assertThat(token.getToken(), not(is(anotherToken.getToken())));
//
//        // Ensure it was invalidated
//        assertThat(authService.findAuthenticatedUser(token.getToken()).isPresent(), is(false));
//        assertThat(authService.findAuthenticatedUser(anotherToken.getToken()).isPresent(), is(true));
//
//        // Ensure mapping for user updates properly
//        assertThat(authService.findToken("test").get(), is(anotherToken));
//    }

}
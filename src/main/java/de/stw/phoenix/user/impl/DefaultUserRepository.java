package de.stw.phoenix.user.impl;

//@Service
public class DefaultUserRepository  {

//    private final PasswordEncoder passwordEncoder;
//    private final List<User> users = Lists.newArrayList();
//
//    @Autowired
//    public DefaultUserRepository(PasswordEncoder passwordEncoder) {
//        this.passwordEncoder = Objects.requireNonNull(passwordEncoder);
//    }
//
//    @Override
//    public List<User> findAll() {
//        return ImmutableList.copyOf(users);
//    }
//
//    @Override
//    public Optional<User> lookup(String username, String password) {
//        final Optional<User> user = find(username)
//                .filter(u -> passwordEncoder.matches(password, u.getPassword().getValue()));
//        return user;
//    }
//
//    @Override
//    public Optional<User> find(String userName) {
//        Objects.requireNonNull(userName);
//        return users.stream().filter(u -> userName.equalsIgnoreCase(u.getUsername())).findAny();
//    }
//
//    @Override
//    public User save(User newUser) {
//        if (find(newUser.getUsername()).isPresent()) {
//            throw new IllegalArgumentException("User already exists");
//        }
//        if (!newUser.getPassword().isEncoded()) {
//            final String passwordHash = passwordEncoder.encode(newUser.getPassword().getValue());
//            User copiedUser = User.builder(newUser).password(new HashedPassword(passwordHash)).build();
//            users.add(copiedUser);
//        } else {
//            users.add(newUser);
//        }
//        return find(newUser.getUsername()).get();
//    }
//
//    @Override
//    public long count() {
//        return users.size();
//    }
}

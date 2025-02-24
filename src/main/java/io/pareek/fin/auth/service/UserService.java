package io.pareek.fin.auth.service;

import io.pareek.fin.auth.constant.enums.AuthProvider;
import io.pareek.fin.auth.model.User;
import io.pareek.fin.auth.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;

    /**
     * Finds a user by email.
     * @param email the user's email
     * @return an Optional containing the user if found
     */
    public Optional<User> findByEmail(String email) {
        return userRepository.findByEmail(email);
    }

    /**
     * Registers a new user if not already present.
     * @param email user's email
     * @param name user's name
     * @param picture profile picture URL
     * @return the registered or existing user
     */
    public User registerUser(String email, String name, String picture) {
        return userRepository.findByEmail(email)
                .orElseGet(() -> userRepository.save(
                        User.builder()
                                .email(email)
                                .name(name)
                                .picture(picture)
                                .provider(AuthProvider.GOOGLE)
                                .build()
                ));
    }
}
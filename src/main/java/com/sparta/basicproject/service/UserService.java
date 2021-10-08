package com.sparta.basicproject.service;

import com.sparta.basicproject.dto.SignupRequestDto;
import com.sparta.basicproject.model.User;
import com.sparta.basicproject.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import java.util.regex.Pattern;

import java.util.Optional;

@Service
public class UserService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    @Autowired
    public UserService(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    public Boolean registerUser(SignupRequestDto requestDto) {
        String error = "";
        String username = requestDto.getUsername();
        String password = passwordEncoder.encode(requestDto.getPassword());
// 회원 ID 중복 확인
        Optional<User> found = userRepository.findByUsername(username);
        String pattern = "^[a-zA-Z0-9]*$";
        if (found.isPresent()) {
            return false;
        }

        String email = requestDto.getEmail();

        if (username.length() < 3) {
            return false;

        } else if (!Pattern.matches(pattern, username)) {
            return false;

        }  else if (password.length() < 4) {
            return false;

        } else if (password.contains(username)) {
            return false;

        } else if (email.length() < 1) {
            return false;
        }

        User user = new User(username, password, email);
        userRepository.save(user);
        return true;
    }
}

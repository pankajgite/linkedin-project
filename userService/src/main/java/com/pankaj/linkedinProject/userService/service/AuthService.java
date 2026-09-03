package com.pankaj.linkedinProject.userService.service;

import com.pankaj.linkedinProject.userService.dto.LoginRequestDto;
import com.pankaj.linkedinProject.userService.dto.SignupRequestDto;
import com.pankaj.linkedinProject.userService.dto.UserDto;
import com.pankaj.linkedinProject.userService.entity.User;
import com.pankaj.linkedinProject.userService.exception.BadRequestException;
import com.pankaj.linkedinProject.userService.exception.ResourceNotFoundException;
import com.pankaj.linkedinProject.userService.repository.UserRepository;
import com.pankaj.linkedinProject.userService.util.BCrypt;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class AuthService {
    private final UserRepository userRepository;
    private final ModelMapper modelMapper;
    private final JwtService jwtService;


    public UserDto signUp(SignupRequestDto signupRequestDto) {
        log.info("Signup a User with email: {}"+signupRequestDto.getEmail());
        boolean exists = userRepository.existsByEmail(signupRequestDto.getEmail());
        if (exists) {
            throw new BadRequestException("User with Email " + signupRequestDto.getEmail() + " already exists");
        }

        User user = modelMapper.map(signupRequestDto, User.class);
        user.setPassword(BCrypt.hash(signupRequestDto.getPassword()));

         user = userRepository.save(user);
         return modelMapper.map(user, UserDto.class);

    }

    public String login(LoginRequestDto loginRequestDto) {
        log.info("Login request for user with email: {}",loginRequestDto.getEmail());

        User user =  userRepository.findByEmail(loginRequestDto.getEmail()).orElseThrow(()
                -> new BadRequestException("Invalid Details"));

        boolean isPasswordMatch = BCrypt.match(loginRequestDto.getPassword(), user.getPassword());

        if (!isPasswordMatch) {
            throw new BadRequestException("Invalid Details");
        }

        return jwtService.generateAccessToken(user);

    }
}

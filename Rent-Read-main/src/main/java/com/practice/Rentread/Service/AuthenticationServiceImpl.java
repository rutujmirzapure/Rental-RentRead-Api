package com.practice.Rentread.Service;

import com.practice.Rentread.DataModels.RentalUser;
import com.practice.Rentread.DataModels.Role;
import com.practice.Rentread.Repository.RentalUserRepository;
import com.practice.Rentread.dto.JwtAuthenticationResponse;
import com.practice.Rentread.dto.SignInRequest;
import com.practice.Rentread.dto.SignUpRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.HashMap;

@Service
@RequiredArgsConstructor
public class AuthenticationServiceImpl implements  AuthenticationService{

    private final RentalUserRepository userRepository;

    private final PasswordEncoder passwordEncoder;

    private final AuthenticationManager authenticationManager;

    private final JWTService jwtService;

    public RentalUser signUp(SignUpRequest signUpRequest){

        RentalUser rentalUser = new RentalUser();

        rentalUser.setFirstName(signUpRequest.getFirstname());
        rentalUser.setLastName(signUpRequest.getLastname());
        rentalUser.setEmail(signUpRequest.getEmail());
        rentalUser.setRole(Role.USER);
        rentalUser.setPassword(passwordEncoder.encode(signUpRequest.getPassword()));

        return userRepository.save(rentalUser);
    }

    public JwtAuthenticationResponse signin(SignInRequest signInRequest){
        authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(signInRequest.getEmail(),
                signInRequest.getPassword()));

        RentalUser rentalUser = userRepository.findByEmail(signInRequest.getEmail()).orElseThrow(() -> new IllegalArgumentException("Invalid email or password"));
        String jwt = jwtService.generateToken(rentalUser);
        String refreshToken = jwtService.generateRefreshToken(new HashMap<>(), rentalUser);

        JwtAuthenticationResponse jwtAuthenticationResponse = new JwtAuthenticationResponse();
        jwtAuthenticationResponse.setToken(jwt);
        jwtAuthenticationResponse.setRefreshToken(refreshToken);
        return jwtAuthenticationResponse;
    }
}

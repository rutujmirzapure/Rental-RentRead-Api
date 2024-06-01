package com.practice.Rentread.Service;

import com.practice.Rentread.DataModels.RentalUser;
import com.practice.Rentread.dto.JwtAuthenticationResponse;
import com.practice.Rentread.dto.SignInRequest;
import com.practice.Rentread.dto.SignUpRequest;

public interface AuthenticationService {
    RentalUser signUp(SignUpRequest signUpRequest);
    JwtAuthenticationResponse signin(SignInRequest signInRequest);
}

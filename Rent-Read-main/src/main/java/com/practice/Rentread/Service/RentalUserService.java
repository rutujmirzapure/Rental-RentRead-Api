package com.practice.Rentread.Service;

import com.practice.Rentread.DataModels.RentalUser;
import org.springframework.security.core.userdetails.UserDetailsService;

import java.util.Optional;

public interface RentalUserService {
    RentalUser registerNewUser(RentalUser rentalUser);
    UserDetailsService userDetailsService();
    Optional<RentalUser> findUserByEmail(String email);
    RentalUser save(RentalUser rentalUser);
}

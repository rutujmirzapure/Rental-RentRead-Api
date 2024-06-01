package com.practice.Rentread.Service;

import com.practice.Rentread.DataModels.RentalUser;
//import com.practice.Rentread.Repository.RoleRepository;
import com.practice.Rentread.Repository.RentalUserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class RentalUserServiceImpl implements RentalUserService {

    private final RentalUserRepository userRepository;


    @Override
    public UserDetailsService userDetailsService(){
        return new UserDetailsService() {
            @Override
            public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
                return userRepository.findByEmail(username)
                    .orElseThrow(() -> new UsernameNotFoundException("RentalUser not found"));
            }
        };
    }

    public RentalUser registerNewUser(RentalUser rentalUser){
        return userRepository.save(rentalUser);
    }

    public Optional<RentalUser> findUserByEmail(String email){
        return userRepository.findByEmail(email);
    }

    public RentalUser save(RentalUser rentalUser){
        return userRepository.save(rentalUser);
    }

}

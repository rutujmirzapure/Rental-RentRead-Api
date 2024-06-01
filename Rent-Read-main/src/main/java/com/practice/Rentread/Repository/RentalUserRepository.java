package com.practice.Rentread.Repository;

import com.practice.Rentread.DataModels.RentalUser;
import com.practice.Rentread.DataModels.Role;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

public interface RentalUserRepository extends CrudRepository<RentalUser, String> {

    Optional<RentalUser> findByEmail(String email);

    RentalUser findByRole(Role role);
}

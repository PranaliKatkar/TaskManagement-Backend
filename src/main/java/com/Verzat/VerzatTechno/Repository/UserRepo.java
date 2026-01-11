package com.Verzat.VerzatTechno.Repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.Verzat.VerzatTechno.Entity.User;

@Repository
public interface UserRepo extends JpaRepository<User, Long> {

    Optional<User> findByEmail(String email);

	Optional<User> findByUsername(String username);

	Optional<User> findByContactNumber(String formattedNumber);

	List<User> findByAlertsEnabledTrue();
}

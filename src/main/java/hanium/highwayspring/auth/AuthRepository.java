package hanium.highwayspring.auth;

import hanium.highwayspring.auth.Auth;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface AuthRepository extends JpaRepository<Auth, Long> {
    Optional<Auth> findByUserId(Long userId);
    Optional<Auth> findById(Long authId);
}

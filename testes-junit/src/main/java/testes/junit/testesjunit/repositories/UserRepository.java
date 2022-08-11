package testes.junit.testesjunit.repositories;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import testes.junit.testesjunit.model.User;

public interface UserRepository extends JpaRepository<User, Long>{

	Optional<User> findByEmail(String email);
	
	

}

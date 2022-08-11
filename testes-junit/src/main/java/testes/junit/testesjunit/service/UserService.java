package testes.junit.testesjunit.service;

import java.util.List;
import java.util.Optional;

import javax.transaction.Transactional;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


import testes.junit.testesjunit.model.User;
import testes.junit.testesjunit.model.dto.UserDto;
import testes.junit.testesjunit.repositories.UserRepository;
import testes.junit.testesjunit.service.exception.DataIntegratyViolationException;
import testes.junit.testesjunit.service.exception.UserNotFoundException;
import testes.junit.testesjunit.service.util.UserUtil;

@Service
public class UserService {

	@Autowired
	private UserRepository repository;
	@Autowired
	private ModelMapper mapper;
	
	private UserUtil userUtil;
	
	
	
	public User findById(Long id){
		return repository.findById(id).orElseThrow(
				() -> new UserNotFoundException("Objeto não encontrado"));
	}
	
	public List<User> findAll(){
		return repository.findAll();
	}
	
	@Transactional
	public User create(UserDto dto) {
		findByEmail(dto);
		return repository.save(mapper.map(dto, User.class));
	}
	
	@Transactional
	public User update(UserDto dto, Long id) {
		findByEmail(dto);
		User user = repository.findById(id).get();
		return userUtil.atualizar(user , dto );
	}
	
	@Transactional
	public void delete(Long id){
		this.findById(id);
		repository.deleteById(id);
	}


	private void findByEmail(UserDto dto) {
		Optional<User> optionalUser = repository.findByEmail(dto.getEmail());
		if(optionalUser.isPresent() && !optionalUser.get().getId().equals(dto.getId())) {
			throw new DataIntegratyViolationException("E-mail já cadastrado no sistema");
		}
		
	}
	
	
}

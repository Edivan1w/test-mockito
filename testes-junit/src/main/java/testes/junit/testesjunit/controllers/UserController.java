package testes.junit.testesjunit.controllers;

import java.net.URI;
import java.util.List;
import java.util.stream.Collectors;


import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import testes.junit.testesjunit.model.User;
import testes.junit.testesjunit.model.dto.UserDto;
import testes.junit.testesjunit.service.UserService;
import testes.junit.testesjunit.service.exception.UserNotFoundException;

@RestController
@RequestMapping("/user")
public class UserController {
	
	 private static final String ID = "/{id}";
	
	@Autowired
	private ModelMapper mapper;
	@Autowired
	private UserService userService;
	

	@GetMapping("/{id}")
	public ResponseEntity<UserDto> findById(@PathVariable Long id) {
		User user = userService.findById(id);
		return ResponseEntity.ok(mapper.map(user, UserDto.class));
	}
	
	@GetMapping
	public ResponseEntity<List<UserDto>> findAll(){
		List<UserDto> list = userService.findAll()
				.stream().map(u -> mapper.map(u, UserDto.class))
				.collect(Collectors.toList());
		return ResponseEntity.ok(list);
	}
	

	@PostMapping
	public ResponseEntity<UserDto> create(@RequestBody UserDto dto) {
		
		URI uri = ServletUriComponentsBuilder
				.fromCurrentRequest().path(ID)
				.buildAndExpand(userService.create(dto).getId()).toUri();
				
		return ResponseEntity.created(uri).build();
	}
	

	@PutMapping("/{id}")
	public ResponseEntity<UserDto> update(@PathVariable Long id, @RequestBody UserDto dto)  {
		User user = userService.update(dto, id);
		return ResponseEntity.ok(mapper.map(user, UserDto.class));
	}
	
	@DeleteMapping("/{id}")
	public ResponseEntity<?> delete(@PathVariable Long id){
		userService.delete(id);
		return ResponseEntity.noContent().build();
	}
	
	
	
	
}

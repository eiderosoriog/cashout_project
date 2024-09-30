package com.example.chashout;

import com.example.chashout.entity.UserEntity;
import com.example.chashout.model.UserDto;
import com.example.chashout.repository.UserRepository;
import com.example.chashout.service.UserService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

import java.time.LocalDateTime;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.*;

class UserServiceTest {

	@InjectMocks
	private UserService userService;

	@Mock
	private UserRepository userRepository;

	private UserEntity userEntity;
	private UserDto userDto;

	@BeforeEach
	void setUp() {
		MockitoAnnotations.openMocks(this);
		// Inicializamos los objetos de prueba
		userEntity = new UserEntity();
		userEntity.setId(1L);
		userEntity.setBalance(100.0);
		userEntity.setCreationDate(LocalDateTime.now());

		userDto = new UserDto();
		userDto.setId(1L);
		userDto.setBalance(100.0);
		userDto.setCreationDate(LocalDateTime.now());
	}

	@Test
	void testGetUserById() {
		when(userRepository.findById(anyLong())).thenReturn(Mono.just(userEntity));

		Mono<UserDto> result = userService.getUserById(1L);

		StepVerifier.create(result)
				.expectNextMatches(user -> user.getId().equals(1L))
				.verifyComplete();

		verify(userRepository, times(1)).findById(anyLong());
	}

	@Test
	void testCreateUser() {
		when(userRepository.save(any(UserEntity.class))).thenReturn(Mono.just(userEntity));

		Mono<UserDto> result = userService.createUser(Mono.just(userDto));

		StepVerifier.create(result)
				.expectNextMatches(user -> user.getId().equals(1L) && user.getBalance().equals(100.0))
				.verifyComplete();

		verify(userRepository, times(1)).save(any(UserEntity.class));
	}

	@Test
	void testUpdateBalance() {
		// Configuramos el comportamiento del repositorio
		when(userRepository.findById(anyLong())).thenReturn(Mono.just(userEntity));
		when(userRepository.save(any(UserEntity.class))).thenReturn(Mono.just(userEntity));


		userDto.setAmount(100.0);
		Mono<UserDto> result = userService.updateBalance(Mono.just(userDto), 1L);


		StepVerifier.create(result)
				.expectNextMatches(user -> user.getBalance().equals(200.0)) // balance actualizado
				.verifyComplete();

		verify(userRepository, times(1)).findById(anyLong());
		verify(userRepository, times(1)).save(any(UserEntity.class));
	}
}

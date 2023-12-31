package hr.assecosee.services;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import hr.assecosee.shorty.User;
import hr.assecosee.shorty.UserRepository;


@ExtendWith(MockitoExtension.class)
class RegistrationServiceTest {
	
	@Mock
	private UserRepository userRepository;
	private RegistrationService registrationService;
	
	@BeforeEach
	void setUp() {
		
		registrationService = new RegistrationService(userRepository);
	}
	
	
	@Test
	void testAddUser() {
		
		User user = new User("Marko", "");
		
		//User doesn't exist:
		
		registrationService.addUser(user);
		
		ArgumentCaptor<User> userArgumentCaptor = ArgumentCaptor.forClass(User.class);
		
		verify(userRepository).existsById(user.getUserName());
		verify(userRepository).save(userArgumentCaptor.capture());
		
		User capturedUser = userArgumentCaptor.getValue();
		
		assertEquals(10, capturedUser.getPassword().length());
		assertEquals("Marko", capturedUser.getUserName());
		
		//User exists in database:
		
		when(userRepository.existsById(user.getUserName())).thenReturn(true);
		
		Optional<User> optional = registrationService.addUser(user);
		
		assertTrue(optional.isEmpty());
		
	}

}

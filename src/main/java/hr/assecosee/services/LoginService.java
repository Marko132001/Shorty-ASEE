package hr.assecosee.services;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCrypt;
import org.springframework.stereotype.Service;

import hr.assecosee.shorty.User;
import hr.assecosee.shorty.UserRepository;

@Service
public class LoginService {
	
	private UserRepository userRepository;
	
	@Autowired
	public LoginService(UserRepository userRepository) {
		
		this.userRepository = userRepository;
	}
	
	
	public boolean authenticate (User user) {
			
		Optional<User> existingUser = userRepository.findById(user.getUserName());
		
		if(existingUser.isPresent()) {
			
			User existUser = existingUser.get();
			
			if(BCrypt.checkpw(user.getPassword(), existUser.getPassword())) {
				
				return true;
			}		
			
		}
		
		return false;
		
	}

}

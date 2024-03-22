package com.snm.techcraft.serviceImpl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.snm.techcraft.dto.LoginRequest;
import com.snm.techcraft.jwtService.JwtService;
import com.snm.techcraft.model.UserInfo;
import com.snm.techcraft.repository.UserRepository;
import com.snm.techcraft.service.UserService;
import com.snm.techcraft.util.SNMResponse;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class UserServiceImpl implements UserService {

	@Autowired
	private UserRepository userRepository;

	@Autowired
	private PasswordEncoder passwordEncoder;

	@Autowired
	private JwtService jwtService;

	@Autowired
	private UserDetailsService userDetailsService;

	@Override
	@PreAuthorize("hasRole('ROLE_ADMIN') or hasRole('ROLE_SUBADMIN')")
	public SNMResponse addUser(UserInfo user) {
		log.info("Inside addUser Service Method");
		UserInfo existingUser = userRepository.findByEmail(user.getEmail());
		if (existingUser != null) {
			return new SNMResponse("Email already registered", HttpStatus.BAD_REQUEST.value(), null);
		}

		String encodedPassword = passwordEncoder.encode(user.getPassword());
		user.setPassword(encodedPassword);

		user.setRole(user.getRole());
		userRepository.save(user);

		return new SNMResponse("User added successfully", HttpStatus.OK.value(), user);
	}

	@Override
	@PreAuthorize("hasRole('ROLE_ADMIN') or hasRole('ROLE_SUBADMIN')")
	public SNMResponse getAllUsers() {
		log.info("Inside allUser Service Method");
		List<UserInfo> userList = userRepository.findAll();
		return new SNMResponse("All Users", HttpStatus.OK.value(), userList);
	}

	@Override
	@PreAuthorize("hasRole('ROLE_ADMIN') or hasRole('ROLE_SUBADMIN')")
	public SNMResponse editUser(int id, UserInfo updatedUserInfo) {
		log.info("Inside editUser Service Method");
		Optional<UserInfo> optionalUser = userRepository.findById(id);
		if (optionalUser.isEmpty()) {
			return new SNMResponse("User with ID " + id + " not found", HttpStatus.NOT_FOUND.value(), null);
		}

		UserInfo user = optionalUser.get();
		updateUserFields(user, updatedUserInfo);
		userRepository.save(user);

		return new SNMResponse("User with ID " + id + " updated successfully", HttpStatus.OK.value(), null);
	}

	@Override
	@PreAuthorize("hasRole('ROLE_ADMIN')")
	public SNMResponse deleteUser(int id) {
		log.info("Inside deleteUser Service Method");
		Optional<UserInfo> optionalUser = userRepository.findById(id);
		if (optionalUser.isEmpty()) {
			return new SNMResponse("User with ID " + id + " not found", HttpStatus.NOT_FOUND.value(), null);
		}

		userRepository.deleteById(id);
		return new SNMResponse("User with ID " + id + " deleted successfully", HttpStatus.OK.value(), null);
	}

	private void updateUserFields(UserInfo user, UserInfo updatedUserInfo) {
		if (updatedUserInfo.getName() != null) {
			user.setName(updatedUserInfo.getName());
		}
		if (updatedUserInfo.getEmail() != null) {
			user.setEmail(updatedUserInfo.getEmail());
		}
		if (updatedUserInfo.getMobile() != 0) {
			user.setMobile(updatedUserInfo.getMobile());
		}
		if (updatedUserInfo.getPassword() != null && !updatedUserInfo.getPassword().isEmpty()) {
			String encodedPassword = passwordEncoder.encode(updatedUserInfo.getPassword());
			user.setPassword(encodedPassword);
		}
	}

	@Override
	public SNMResponse login(LoginRequest loginRequest) {
		log.info("Inside Login Service Method");
		if (loginRequest == null || loginRequest.getEmail() == null || loginRequest.getPassword() == null) {
			return new SNMResponse("Email and password are required", HttpStatus.BAD_REQUEST.value(), null);
		}

		UserInfo user = userRepository.findByEmail(loginRequest.getEmail());

		if (user == null || !passwordEncoder.matches(loginRequest.getPassword(), user.getPassword())) {
			return new SNMResponse("Invalid email or password", HttpStatus.UNAUTHORIZED.value(), null);
		}

		String role = user.getRole().getName();

		UserDetails userDetails = userDetailsService.loadUserByUsername(user.getName());
		String token = jwtService.generateToken(user.getName(), userDetails.getAuthorities());

		Map<String, Object> responseData = new HashMap<>();
		responseData.put("token", token);
//		responseData.put("role", role);
		responseData.put("USERInfo", user);

		return new SNMResponse("Login successful", HttpStatus.OK.value(), responseData);
	}

	@Override
	public SNMResponse getUserById(int id) {
		UserInfo user = userRepository.findById(id).orElse(null);
		if (user != null) {
			userRepository.findById(id);
			return new SNMResponse("User found", HttpStatus.OK.value(), user);
		} else {
			return new SNMResponse("User not found", HttpStatus.NOT_FOUND.value(), null);
		}
	}
}
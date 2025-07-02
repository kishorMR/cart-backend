package com.tcs.user.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.tcs.user.entity.User;
import com.tcs.user.repository.UserRepository;
import com.tcs.user.security.AuthRequest;
import com.tcs.user.security.JwtUtil;
import com.tcs.user.service.UserService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/user")
public class UserController {

	@Autowired
	UserService service;

	@Autowired
	private JwtUtil jwtUtil;

	@Autowired
	private UserRepository repo;

	@Autowired
	private PasswordEncoder passwordEncoder;

	@PostMapping("/register")
	public User registerUser(@RequestBody User user) {
		return service.registerUser(user);
	}

	@PostMapping("/authenticate")
	public ResponseEntity<String> authenticate(@RequestBody AuthRequest request) {

		User user = repo.findByEmail(request.getEmail());

		if (user.getEmail().equals(request.getEmail())
				&& passwordEncoder.matches(request.getPassword(), user.getPassword())) {
			String token = jwtUtil.generateToken(request.getEmail(), user.getRole());
			return ResponseEntity.ok(token);
		}
		return ResponseEntity.status(401).body("Invalid Credentials");
	}

	@GetMapping("/user")
	public String welcomeUser(@RequestHeader("Authorization") String authHeader) {
		if (authHeader != null && authHeader.startsWith("Bearer ")) {
	        String token = authHeader.substring(7);
//	        service.blackList(token);
	        return jwtUtil.extractUsername(token);
	    }
		return authHeader;
	}

	@GetMapping("/welcome")
	public String welcome() {
		return "Welcome! This is a secured endpoint.";
	}
	

	@GetMapping("/users")
	public Iterable<User> welcomeAdmin() {
		return service.getallUsers();
	}

	@PutMapping("/update")
	public ResponseEntity<String> updateUser(@RequestHeader("Authorization") String authHeader,
			@Valid @RequestBody User user) {
		User org_User = service.updateUser(authHeader, user);
		if (org_User != null) {
			return ResponseEntity.ok("User updated!");
		}
		return ResponseEntity.ok("User updation failed!");
	}

	@GetMapping("/lgt")
	public ResponseEntity<?> logout(@RequestHeader("Authorization") String authHeader) {
	    if (authHeader != null && authHeader.startsWith("Bearer ")) {
	        String token = authHeader.substring(7);
	        service.blackList(token);
	        return ResponseEntity.ok("Logged out successfully.");
	    }
	    return ResponseEntity.badRequest().body("No valid token provided.");
}
	@PutMapping("/authenticate/forgot")
	public ResponseEntity<String> updatePassword(@RequestBody AuthRequest user) {
		Boolean org_User = service.updatePassword(user);
		if (org_User) {
			return ResponseEntity.ok("Password updated!");
		}
		return ResponseEntity.ok("Password updation failed!");
	}
}
	

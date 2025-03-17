package com.neoapps.library_management_system.services;

import com.neoapps.library_management_system.dto.UserCreateDTO;
import com.neoapps.library_management_system.dto.UserResponseDTO;
import com.neoapps.library_management_system.entities.User;
import com.neoapps.library_management_system.repositories.UserRepository;
import com.neoapps.library_management_system.utils.ResourceNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserService implements UserDetailsService {


    private final UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        User user = userRepository.findByEmail(email).orElseThrow(() -> new ResourceNotFoundException("User not found"));
        return new org.springframework.security.core.userdetails.User(
                user.getEmail(),
                user.getPassword(),
                Collections.singleton(new SimpleGrantedAuthority(user.getRole().toString())));
    }

    @Transactional(readOnly = true)
    public List<UserResponseDTO> getAllUsers() {
        List<User> usersDAO = userRepository.findAll();
        return usersDAO.stream().map(UserResponseDTO::new).toList();
    }

    public Optional<UserResponseDTO> getUserById(Long userId) {
        Optional<User> optionalUser = userRepository.findById(userId);
        return optionalUser.map(UserResponseDTO::new);
    }

    @Transactional
    public UserResponseDTO saveUser(UserCreateDTO userCreateDTO) {
        User user = userRepository.save(userCreateDTO.toDAO());
        return new UserResponseDTO(user);
    }

    @Transactional
    public UserResponseDTO updateUser(UserResponseDTO userResponseDTO) {
        Optional<User> optionalUser = userRepository.findById(userResponseDTO.getId());
        if (optionalUser.isPresent()) {
            User userDB = optionalUser.get();
            userDB.setFullName(userResponseDTO.getFullName());
            userDB.setEmail(userResponseDTO.getEmail());
            userDB.setAddress(userResponseDTO.getAddress());
            return new UserResponseDTO(userRepository.save(userDB));
        } else {
            throw new ResourceNotFoundException("User with id: " + userResponseDTO.getId() + " not found ");
        }
    }

    @Transactional
    public Optional<UserResponseDTO> deleteUser(Long userId) {

        Optional<User> userOptional = userRepository.findById(userId);

        if (userOptional.isPresent()) {
            userRepository.deleteById(userId);
        }
        return userOptional.map(UserResponseDTO::new);
    }
}

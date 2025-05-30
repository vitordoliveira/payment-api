package com.vitoroliveira.paymentapi.service;

import com.vitoroliveira.paymentapi.dto.UserDTO;
import com.vitoroliveira.paymentapi.dto.UserRegistrationDTO;
import com.vitoroliveira.paymentapi.model.User;
import com.vitoroliveira.paymentapi.repository.UserRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class UserService {
    
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    
    @Autowired
    public UserService(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }
    
    @Transactional
    public UserDTO registerUser(UserRegistrationDTO registrationDTO) {
        // Verificar se o e-mail já está em uso
        if (userRepository.existsByEmail(registrationDTO.getEmail())) {
            throw new IllegalStateException("Email já está em uso");
        }
        
        // Verificar se o CPF já está em uso
        if (userRepository.existsByCpf(registrationDTO.getCpf())) {
            throw new IllegalStateException("CPF já está em uso");
        }
        
        // Criar novo usuário
        User user = new User();
        user.setName(registrationDTO.getName());
        user.setEmail(registrationDTO.getEmail());
        user.setCpf(registrationDTO.getCpf());
        // Criptografar a senha antes de salvar
        user.setPassword(passwordEncoder.encode(registrationDTO.getPassword()));
        
        // Salvar o usuário
        User savedUser = userRepository.save(user);
        
        // Retornar DTO do usuário
        return convertToDTO(savedUser);
    }
    
    public UserDTO getUserById(Long id) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Usuário não encontrado com ID: " + id));
        
        return convertToDTO(user);
    }
    
    public UserDTO getUserByEmail(String email) {
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new EntityNotFoundException("Usuário não encontrado com email: " + email));
        
        return convertToDTO(user);
    }
    
    private UserDTO convertToDTO(User user) {
        UserDTO dto = new UserDTO();
        dto.setId(user.getId());
        dto.setName(user.getName());
        dto.setEmail(user.getEmail());
        dto.setCpf(user.getCpf());
        return dto;
    }
}
package com.example.pdv.service;

import com.example.pdv.dto.UserDTO;
import com.example.pdv.entity.User;
import com.example.pdv.exceptions.NoItemException;
import com.example.pdv.repository.UserRepository;
import com.example.pdv.security.SecurityConfig;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;
    private ModelMapper mapper = new ModelMapper();


    public List<UserDTO> findAll(){
        return userRepository.findAll().stream().map(user ->
                new UserDTO(
                        user.getId(),
                        user.getName(),
                        user.getUserName(),
                        user.getPassword(),
                        user.isEnable())
        ).collect(Collectors.toList());
    }

    public UserDTO save(UserDTO user){
        user.setPassword(SecurityConfig.passwordEncoder().encode(user.getPassword()));
        User userToSave = mapper.map(user, User.class);

        userRepository.save(userToSave);
        return new UserDTO(
                userToSave.getId(),
                userToSave.getName(),
                userToSave.getUserName(),
                userToSave.getPassword(),
                userToSave.isEnable()

        );
    }

    public UserDTO findById(long id){
        Optional<User> optional = userRepository.findById(id);

        if(!optional.isPresent()){
            throw new NoItemException("Usuário não encontrado!");
        }
        User user = optional.get();
        return new UserDTO(
                user.getId(),
                user.getName(),
                user.getUserName(),
                user.getPassword(),
                user.isEnable()
        );
    }

    public UserDTO update(UserDTO user){
        user.setPassword(SecurityConfig.passwordEncoder().encode(user.getPassword()));
        User userToSave = mapper.map(user, User.class);

        Optional<User> userToEdit = userRepository.findById(userToSave.getId());
        if(!userToEdit.isPresent()){
            throw new NoItemException("Usuário não encontrado.");
        }

        userRepository.save(userToSave);
        return new UserDTO(
                userToSave.getId(),
                userToSave.getName(),
                userToSave.getUserName(),
                userToSave.getPassword(),
                userToSave.isEnable()
        );
    }

    public void deleteById(long id){
        userRepository.deleteById(id);
    }
}

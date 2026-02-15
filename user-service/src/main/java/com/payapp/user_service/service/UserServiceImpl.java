package com.payapp.user_service.service;

import com.payapp.user_service.client.WalletClient;
import com.payapp.user_service.dto.CreateWalletRequest;
import com.payapp.user_service.entity.User;
import com.payapp.user_service.repository.UserRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UserServiceImpl implements UserService{

    private final UserRepository userRepo;
    private final WalletClient walletClient;
    public UserServiceImpl(UserRepository userRepository, WalletClient walletClient){
        this.userRepo = userRepository;
        this.walletClient = walletClient;
    }

    @Override
    public User createUser(User user) {
        User savedUser = userRepo.save(user);
        try {
            CreateWalletRequest request = new CreateWalletRequest();
            request.setUserId(savedUser.getId());
            request.setCurrency("INR");
            walletClient.createWallet(request);
        } catch (Exception ex) {
            userRepo.deleteById(savedUser.getId());
            throw new RuntimeException("Wallet creation failed, user rolled back", ex);
        }
        return savedUser;
    }

    @Override
    public Optional<User> getUserById(Long id) {
        return userRepo.findById(id);
    }

    @Override
    public List<User> getAllUsers() {
        return userRepo.findAll();
    }

    @Override
    public Optional<User> deleteUser(Long id) {
        Optional<User> user = userRepo.findById(id);
        if(user.isPresent()){
            userRepo.deleteById(id);
        }
        return user;
    }
}

package com.example.DigitalWallet.service.Impl;

import com.example.DigitalWallet.dto.response.WalletResponse;
import com.example.DigitalWallet.model.User;
import com.example.DigitalWallet.model.Wallet;
import com.example.DigitalWallet.repository.UserRepository;
import com.example.DigitalWallet.repository.WalletRepository;
import com.example.DigitalWallet.service.WalletService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;

@Service
public class WalletServiceImpl implements WalletService {

    private final WalletRepository walletRepository;
    private  final UserRepository userRepository;
    public WalletServiceImpl(WalletRepository walletRepository, UserRepository userRepository){
        this.walletRepository = walletRepository;
        this.userRepository = userRepository;
    }

    @Override
    public WalletResponse createWallet(Long userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(()-> new RuntimeException("User Not Found"));

        if(walletRepository.findByUser(user) != null){
            return walletResponse(userId,walletRepository.findByUser(user).getId());
        }else{

            Wallet wallet = new Wallet();
            wallet.setCurrency("INR");
            wallet.setUser(user);
            Wallet saveWallet = walletRepository.save(wallet);

            return walletResponse(user.getId(),saveWallet.getId());
        }

    }

    @Override
    public WalletResponse getWalletById(Long walletId){
        Wallet wallet = walletRepository.findById(walletId).orElseThrow(()-> new RuntimeException("Wallet not found "));
        return walletResponse(wallet.getUser().getId(),walletId) ;
    }

    @Override
    public WalletResponse getWalletByUserId(Long userId) {
        User user = userRepository.findById(userId).orElseThrow(()->  new RuntimeException("User id not found"));
        Wallet wallet = walletRepository.findByUser(user);

        return walletResponse(userId, wallet.getId());

    }

    @Override
    public BigDecimal getBalance(Long walletId) {
        Wallet wallet = walletRepository.findById(walletId).orElseThrow(()-> new RuntimeException("Wallet not found "));
        return wallet.getBalance();
    }

    @Override
    public Page<WalletResponse> getAllWallets(Pageable pageable) {

        Page<Wallet> walletList = walletRepository.findAll(pageable);

        return walletList.map(w-> walletResponse(w.getUser().getId() , w.getId()));
    }

    public WalletResponse walletResponse(Long userId, Long walletId){

        Wallet wallet = walletRepository.findById(walletId).orElseThrow(()-> new RuntimeException("Wallet not found"));
        WalletResponse walletResponse= new WalletResponse();
        walletResponse.setWalletId(walletId);
        walletResponse.setUserID(userId);
        walletResponse.setCurrency(wallet.getCurrency());
        return  walletResponse;
    }


}

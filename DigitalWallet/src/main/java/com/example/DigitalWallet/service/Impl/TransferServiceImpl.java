package com.example.DigitalWallet.service.Impl;

import com.example.DigitalWallet.dto.request.TransferRequest;
import com.example.DigitalWallet.dto.response.TransferResponse;
import com.example.DigitalWallet.model.LadgerType;
import com.example.DigitalWallet.model.Transfer;
import com.example.DigitalWallet.model.User;
import com.example.DigitalWallet.model.Wallet;
import com.example.DigitalWallet.repository.TransferRepository;
import com.example.DigitalWallet.repository.UserRepository;
import com.example.DigitalWallet.repository.WalletRepository;
import com.example.DigitalWallet.service.TransferService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;


@Service
public class TransferServiceImpl implements TransferService {

    private final TransferRepository transferRepository;
    private  final UserRepository userRepository;
    private final WalletRepository walletRepository;
    private final LedgerService ledgerService;

    public TransferServiceImpl(TransferRepository transferRepository, UserRepository userRepository, WalletRepository walletRepository, LedgerService ledgerService) {
        this.transferRepository = transferRepository;
        this.userRepository = userRepository;
        this.walletRepository = walletRepository;
        this.ledgerService = ledgerService;
    }

    public Transfer createTransfer(TransferRequest request , User user){
        Wallet wallet = walletRepository.findByUser(user);
        Wallet receiverWallet =  walletRepository.findById(request.getReceiverId()).orElseThrow(
                ()-> new RuntimeException("Received id not exist")
        );
        if(wallet.getId().equals(receiverWallet.getId())){
            throw  new RuntimeException("Both wallet id are same");
        }
        if(wallet.getBalance().compareTo(request.getAmount()) < 0){
            throw new RuntimeException("request amount is greater than balance");
        }

        Transfer transfer = new Transfer();
        transfer.setAmount(request.getAmount());
        transfer.setReceiverId(request.getReceiverId());
        transfer.setSenderId(wallet.getId());
        Transfer saveTrns =  transferRepository.save(transfer);

        wallet.getBalance().subtract(request.getAmount());
        Wallet saveSenderWallet = walletRepository.save(wallet);


        receiverWallet.getBalance().add(request.getAmount());
        Wallet saveReceiverWallet = walletRepository.save(receiverWallet);

        ledgerService.createLedgerEntry(saveSenderWallet,transfer, LadgerType.DEBIT,transfer.getAmount());
        ledgerService.createLedgerEntry(saveReceiverWallet, transfer, LadgerType.CREDIT, transfer.getAmount());

        return saveTrns;
    }

    public TransferResponse transferResponse(Transfer transfer){
        TransferResponse transferResponse =  new TransferResponse();
        Wallet sender = walletRepository.findById(transfer.getSenderId()).orElseThrow(
                ()-> new RuntimeException("Send not exists")
        );
        Wallet receiver =walletRepository.findById(transfer.getReceiverId()).orElseThrow(
                ()-> new RuntimeException("Receiver not exists")
        );

        transferResponse.setId(transfer.getId());
        transferResponse.setAmount(transfer.getAmount());
        transferResponse.setSendAt(transfer.getSendAt());
        transferResponse.setSendName(sender.getUser().getName());
        transferResponse.setReceiverName(receiver.getUser().getName());
        transferResponse.setReceiverId(transfer.getReceiverId());

        return transferResponse;
    }


    @Override
    @Transactional
    public TransferResponse transferMoney(TransferRequest request) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        assert authentication != null;
        String email = authentication.getName();
        User user = userRepository.findByEmail(email);

        return transferResponse(createTransfer(request,user));

    }

    @Override
    public TransferResponse getTransferById(Long transferId) {
        Transfer transfer =  transferRepository.findById(transferId).orElseThrow(
                ()-> new RuntimeException("Transfer id is invalid")
        );
        return transferResponse(transfer);
    }

    @Override
    public List<TransferResponse> getTransfersByWalletId(Long walletId) {
        List<Transfer> transfers =  transferRepository.findBySenderId(walletId);
        List<TransferResponse> transferResponseList = new ArrayList<>();
        return transfers.stream().map(this::transferResponse).collect(Collectors.toList());
    }

    @Override
    public List<TransferResponse> getTransfersByUserId(Long userId) {
        User user = userRepository.findById(userId).orElseThrow(
                ()-> new RuntimeException("User not exist")
        );
        Wallet wallet =  walletRepository.findByUser(user);

        List<Transfer> transfer =  transferRepository.findBySenderId(wallet.getId());
        List<Transfer> transfers = transferRepository.findByReceiverId(wallet.getId());

        transfer.addAll(transfers);

        List<TransferResponse> transferResponseList =  new ArrayList<>();

        return transfer.stream().map(this::transferResponse).toList() ;
    }

    @Override
    public Page<TransferResponse> getAllTransfers(Pageable pageable) {

        Page<Transfer> allTransfer = transferRepository.findAll(pageable);

        return allTransfer.map(this::transferResponse);
    }
}

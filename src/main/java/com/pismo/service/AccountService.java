package com.pismo.service;

import com.pismo.resource.dto.AccountDTO;
import com.pismo.model.Account;
import com.pismo.resource.exception.BusinessError;
import com.pismo.resource.exception.PismoException;
import com.pismo.service.mapper.AccountMapper;
import com.pismo.repository.AccountRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.UnexpectedRollbackException;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@RequiredArgsConstructor
@Service
public class AccountService {

    private final AccountRepository accountRepository;
    private final AccountMapper accountMapper;

    @Transactional(rollbackFor = Exception.class)
    public void save(AccountDTO accountDTO) throws PismoException {
        try {
            accountRepository.save(accountMapper.toAccount(accountDTO));
        } catch (DataIntegrityViolationException | UnexpectedRollbackException ex) {
            throw new PismoException(BusinessError.NAO_FOI_POSSIVEL_SALVAR_A_CONTA);
        }
    }

    public Optional<Account> getAccount(int id) {
        return accountRepository.findById(id);
    }
}

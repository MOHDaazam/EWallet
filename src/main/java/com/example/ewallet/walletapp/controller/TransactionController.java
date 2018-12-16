package com.example.ewallet.walletapp.controller;

import java.util.logging.Level;
import java.util.logging.Logger;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.ewallet.datatransferobject.TransactionDTO;
import com.example.ewallet.datatransferobject.mapper.TransactionMapper;
import com.example.ewallet.models.Transaction;
import com.example.ewallet.service.TransactionService;
import com.example.ewallet.service.UserAccountService;

@RestController
@RequestMapping("v1/transferTo")
public class TransactionController {
	
	@Autowired
	private UserAccountService userAccountService;

	@Autowired
	private TransactionService transactionService;
	
	@PostMapping("/{id}")
	public ResponseEntity createTransaction(@PathVariable("id")Long userAccountId,@RequestBody TransactionDTO walletDTO) {
		Transaction saved;
		try {
			walletDTO.setUserAccountId(userAccountId);
			saved = transactionService.createTransaction(TransactionMapper.dtoToDO(walletDTO));
		} catch (Exception ex) {
			Logger.getLogger(UserAccountController.class.getName()).log(Level.SEVERE, null, ex);
			return new ResponseEntity<String>(ex.getMessage(), HttpStatus.BAD_REQUEST);
		} 
		return new ResponseEntity<TransactionDTO>(TransactionMapper.doToDTO(saved), HttpStatus.CREATED);
	}
}

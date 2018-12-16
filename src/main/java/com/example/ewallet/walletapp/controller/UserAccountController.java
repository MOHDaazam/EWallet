package com.example.ewallet.walletapp.controller;

import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.ewallet.datatransferobject.TransactionDTO;
import com.example.ewallet.datatransferobject.UserAccountDTO;
import com.example.ewallet.datatransferobject.mapper.TransactionMapper;
import com.example.ewallet.datatransferobject.mapper.UserAccountMapper;
import com.example.ewallet.models.Transaction;
import com.example.ewallet.models.UserAccount;
import com.example.ewallet.service.TransactionService;
import com.example.ewallet.service.UserAccountService;

@RestController
@RequestMapping("v1/users")
public class UserAccountController {

	@Autowired
	private UserAccountService userAccountService;

	@Autowired
	private TransactionService transactionService;

	@GetMapping
	public ResponseEntity getUsers() {
		List<UserAccount> userAccounts = userAccountService.getList();
		return new ResponseEntity<List<UserAccountDTO>>(UserAccountMapper.doToDTOList(userAccounts), HttpStatus.OK);
	}

	@GetMapping("/{id}")
	public ResponseEntity getUser(@PathVariable("id") Long id) {
		UserAccount userAccount;
		try {
			userAccount = userAccountService.userAccountByPK(id);
		} catch (Exception ex) {
			Logger.getLogger(UserAccountController.class.getName()).log(Level.SEVERE, null, ex);
			return new ResponseEntity<String>(ex.getMessage(), HttpStatus.NOT_FOUND);
		}
		return new ResponseEntity<UserAccountDTO>(UserAccountMapper.doToDTO(userAccount), HttpStatus.OK);

	}

	@PostMapping
	public ResponseEntity createUser(@RequestBody UserAccountDTO userAccountDTO) {
		UserAccount saved;
		try {
			saved = userAccountService.save(UserAccountMapper.dtoToDO(userAccountDTO));
		} catch (Exception ex) {
			Logger.getLogger(UserAccountController.class.getName()).log(Level.SEVERE, null, ex);
			return new ResponseEntity<String>(ex.getMessage(), HttpStatus.BAD_REQUEST);
		}
		return new ResponseEntity<UserAccountDTO>(UserAccountMapper.doToDTO(saved), HttpStatus.CREATED);
	}

	@PutMapping("/{id}")
	public ResponseEntity updateUser(@PathVariable("id") Long userAccountId, @RequestBody UserAccountDTO userAccountDTO) {
		UserAccount saved;
		try {
			saved = userAccountService.update(UserAccountMapper.dtoToDO(userAccountDTO), userAccountId);
		} catch (Exception ex) {
			Logger.getLogger(UserAccountController.class.getName()).log(Level.SEVERE, null, ex);
			return new ResponseEntity<String>(ex.getMessage(), HttpStatus.BAD_REQUEST);
		}
		return new ResponseEntity<UserAccountDTO>(UserAccountMapper.doToDTO(saved), HttpStatus.OK);
	}

}
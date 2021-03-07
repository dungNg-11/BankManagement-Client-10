package BankManagement.model;

import java.io.Serializable;

import lombok.Data;
import lombok.RequiredArgsConstructor;


@Data
@RequiredArgsConstructor()
public class AccountCredit implements Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private int id;
	private String idAccount;
	private String typeAccount;
	private double balance;
	private double debt;
	private double hanMucTinDung;
	private Customer customer;
	private User user;
}

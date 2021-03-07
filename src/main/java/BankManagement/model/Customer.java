package BankManagement.model;

import java.io.Serializable;
import java.util.Date;

import lombok.Data;
import lombok.RequiredArgsConstructor;

@Data
@RequiredArgsConstructor
public class Customer implements Serializable{

	private static final long serialVersionUID = 1L;
	private  int id;
	private  String name;
	private  String cmt;
	private  Date dateOfBirth;
	private  String address;




}

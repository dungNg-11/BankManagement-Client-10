package BankManagement.model;

import java.io.Serializable;

import lombok.Data;
import lombok.RequiredArgsConstructor;

@Data
@RequiredArgsConstructor
public class ChuyenKhoan implements Serializable{
	private static final long serialVersionUID = 1L;
	private int id;
	private String noidungck;
	private AccountCredit accountCredit;
	private String nguoinhan;
	private double moneysend;
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getNoidungck() {
		return noidungck;
	}
	public void setNoidungck(String noidungck) {
		this.noidungck = noidungck;
	}
	public AccountCredit getAccountCredit() {
		return accountCredit;
	}
	public void setAccountCredit(AccountCredit accountCredit) {
		this.accountCredit = accountCredit;
	}
	public String getNguoinhan() {
		return nguoinhan;
	}
	public void setNguoinhan(String nguoinhan) {
		this.nguoinhan = nguoinhan;
	}
	public double getMoneysend() {
		return moneysend;
	}
	public void setMoneysend(double moneysend) {
		this.moneysend = moneysend;
	}
	
	
}

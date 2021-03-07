package BankManagement.controller;
import java.util.Arrays;
import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.client.RestTemplate;

import BankManagement.model.AccountCredit;
import BankManagement.model.ChuyenKhoan;
@Controller
@RequestMapping("/chuyenkhoans")
public class ChuyenController {
	private RestTemplate rest = new RestTemplate();
	@GetMapping("/infor")
	public String chuyenKhoanForm(Model model) {
		model.addAttribute("chuyenkhoan", new AccountCredit());
		return "chuyenkhoan/creditForm";
	}
	@GetMapping("/search-id")
	public String searchId(Model model, @RequestParam("id") int id)
	{
		ChuyenKhoan credit = 
				rest.getForObject("http://localhost:8080/chuyenkhoans/show-chuyenkhoan-by-id/"+id, ChuyenKhoan.class);
		model.addAttribute("listChuyenKhoan", credit);
		return "chuyenkhoan/showAllCredit";
	}
	@GetMapping("/show-all-giao-dich")
	public String showGiaoDich(Model model)
	{
		List<ChuyenKhoan> creditList = Arrays.asList(
				rest.getForObject("http://localhost:8080/chuyenkhoans/show-all-chuyenkhoan",ChuyenKhoan[].class));
		model.addAttribute("listChuyenKhoan", creditList);
		return "chuyenkhoan/showAllCredit";
	}
	@PostMapping //save credit
	public String saveChuyenKhoan(@RequestParam("nguoinhan") String nguoinhan,
			@RequestParam("noidungck") String noidungck,
			@RequestParam("moneysend") double moneysend,
			@RequestParam("idaccountCredit") String idaccountCredit) 
	{
		ChuyenKhoan chuyenKhoan =  new ChuyenKhoan();
		AccountCredit accountCredit = rest.getForObject("http://localhost:8080/credits/show-credit-by-id/"+idaccountCredit, AccountCredit.class);
		double k = accountCredit.getBalance() - moneysend;
		accountCredit.setBalance(k);
		chuyenKhoan.setAccountCredit(accountCredit);
		chuyenKhoan.setNguoinhan(nguoinhan);
		chuyenKhoan.setNoidungck(noidungck);
		chuyenKhoan.setMoneysend(moneysend);
		rest.postForObject("http://localhost:8080/chuyenkhoans", chuyenKhoan, ChuyenKhoan.class);
		rest.put("http://localhost:8080/credits/edit-credit/"+accountCredit.getId(), accountCredit, accountCredit.getId());
		return "redirect:/chuyenkhoans/show-all-giao-dich";
		
	}
}

package BankManagement.controller;

import java.util.Arrays;
import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.client.RestTemplate;

import BankManagement.model.AccountDeposit;
import BankManagement.model.Customer;
import BankManagement.model.User;

@Controller
@RequestMapping("/deposits")
public class DepositController {
private RestTemplate rest = new RestTemplate();
	
	@GetMapping("/infor")
	public String depositForm(Model model) {
		model.addAttribute("deposit", new AccountDeposit());
		return "dep/depositForm";
	}

	
	@GetMapping("/search-id")
	public String searchId(Model model, @RequestParam("id") int id)
	{
		AccountDeposit deposit = 
				rest.getForObject("http://localhost:8080/deposits/show-deposit-by-id/"+id, AccountDeposit.class);
		model.addAttribute("listDeposit", deposit);
		return "dep/showAllDeposit";
	}
	
	@GetMapping("/show-deposit-by-id")
	public String showDepositById(Model model, @RequestParam("id") int id)
	{
		AccountDeposit deposit = 
				rest.getForObject("http://localhost:8080/deposits/show-deposit-by-id/"+id, AccountDeposit.class);
		model.addAttribute("depositById", deposit);
		return "dep/showDepositById";
	}
	
	
	@GetMapping("/show-all-deposit")
	public String showAllDeposit(Model model)
	{
		List<AccountDeposit> depositList = Arrays.asList(
				rest.getForObject("http://localhost:8080/deposits/show-all-deposit",AccountDeposit[].class));
		model.addAttribute("listDeposit", depositList);
		return "dep/showAllDeposit";
	}
	
	@PostMapping //save credit
	public String saveDeposit(@RequestParam("idAccount") String idAccount,
			@RequestParam("typeAccount") String typeAccount,
			@RequestParam("balance") double balance,
			@RequestParam("interestRate") double interestRate,
			@RequestParam("balanceMin") double balanceMin,
			@RequestParam("idCustomer") String idCustomer,
			@RequestParam("idUser") String idUser) 
	{
		Customer customer = rest.getForObject("http://localhost:8080/customers/show-customer-by-id/"+idCustomer, Customer.class);
		User user = rest.getForObject("http://localhost:8080/users/show-user-by-id/" + idUser, User.class);
		AccountDeposit deposit = new AccountDeposit();
		deposit.setIdAccount(idAccount);
		deposit.setTypeAccount(typeAccount);
		deposit.setBalance(balance);
		deposit.setInterestRate(interestRate);
		deposit.setBalanceMin(balanceMin);
		deposit.setUser(user);
		deposit.setCustomer(customer);
		
		System.out.println(deposit);
		
		rest.postForObject("http://localhost:8080/deposits", deposit, AccountDeposit.class);
		return "redirect:/deposits/show-all-deposit";
		
	}
	
	@PostMapping("/edit-deposit")
	public String editCredit(@RequestParam("put_id") int id,
			@RequestParam("idAccount") String idAccount,
			@RequestParam("typeAccount") String typeAccount,
			@RequestParam("balance") double balance,
			@RequestParam("interestRate") double interestRate,
			@RequestParam("balanceMin") double balanceMin,
			@RequestParam("idCustomer") String idCustomer,
			@RequestParam("idUser") String idUser)
	{
		Customer customer = rest.getForObject("http://localhost:8080/customers/show-customer-by-id/"+idCustomer, Customer.class);
		User user = rest.getForObject("http://localhost:8080/users/show-user-by-id/" + idUser, User.class);
		AccountDeposit deposit = new AccountDeposit();
		deposit.setIdAccount(idAccount);
		deposit.setTypeAccount(typeAccount);
		deposit.setBalance(balance);
		deposit.setInterestRate(interestRate);
		deposit.setBalanceMin(balanceMin);
		deposit.setUser(user);
		deposit.setCustomer(customer);
		rest.put("http://localhost:8080/deposits/edit-deposit/"+id, deposit, deposit.getId());
		
		return "redirect:/deposits/show-all-deposit";
	}
	
	@GetMapping("/delete-deposit/{id}")
	public String deleteDeposit(@PathVariable("id") int id)
	{
		rest.delete("http://localhost:8080/deposits/delete-deposit/"+id, id);
		System.out.println("id delete "+id);
		return "redirect:/deposits/show-all-deposit";
	}

}

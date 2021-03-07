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

import BankManagement.model.AccountCredit;
import BankManagement.model.Customer;
import BankManagement.model.User;

@Controller
@RequestMapping("/credits")
public class CreditController {
private RestTemplate rest = new RestTemplate();
	
	@GetMapping("/infor")
	public String creditForm(Model model) {
		model.addAttribute("credit", new AccountCredit());
		return "cre/creditForm";
	}

	
	@GetMapping("/search-id")
	public String searchId(Model model, @RequestParam("id") int id)
	{
		AccountCredit credit = 
				rest.getForObject("http://localhost:8080/credits/show-credit-by-id/"+id, AccountCredit.class);
		model.addAttribute("listCredit", credit);
		return "cre/showAllCredit";
	}
	
	@GetMapping("/show-credit-by-id")
	public String showCreditById(Model model, @RequestParam("id") int id)
	{
		AccountCredit credit = 
				rest.getForObject("http://localhost:8080/credits/show-credit-by-id/"+id, AccountCredit.class);
		model.addAttribute("creditById", credit);
		return "cre/showCreditById";
	}
	
	
	@GetMapping("/show-all-credit")
	public String showAllCredit(Model model)
	{
		List<AccountCredit> creditList = Arrays.asList(
				rest.getForObject("http://localhost:8080/credits/show-all-credit",AccountCredit[].class));
		model.addAttribute("listCredit", creditList);
		return "cre/showAllCredit";
	}
	
	@PostMapping //save credit
	public String saveCredit(@RequestParam("idAccount") String idAccount,
			@RequestParam("typeAccount") String typeAccount,
			@RequestParam("balance") double balance,
			@RequestParam("debt") double debt,
			@RequestParam("hanMucTinDung") double hanMucTinDung,
			@RequestParam("idCustomer") String idCustomer,
			@RequestParam("idUser") String idUser) 
	{
		Customer customer = rest.getForObject("http://localhost:8080/customers/show-customer-by-id/"+idCustomer, Customer.class);
		User user = rest.getForObject("http://localhost:8080/users/show-user-by-id/" + idUser, User.class);
		AccountCredit credit = new AccountCredit();
		credit.setIdAccount(idAccount);
		credit.setTypeAccount(typeAccount);
		credit.setBalance(balance);
		credit.setDebt(debt);
		credit.setHanMucTinDung(hanMucTinDung);
		credit.setUser(user);
		credit.setCustomer(customer);
		
		System.out.println(credit);
		
		rest.postForObject("http://localhost:8080/credits", credit, AccountCredit.class);
		return "redirect:/credits/show-all-credit";
		
	}
	
	@PostMapping("/edit-credit")
	public String editCredit(@RequestParam("put_id") int id,
			@RequestParam("idAccount") String idAccount,
			@RequestParam("typeAccount") String typeAccount,
			@RequestParam("balance") double balance,
			@RequestParam("debt") double debt,
			@RequestParam("hanMucTinDung") double hanMucTinDung,
			@RequestParam("idCustomer") String idCustomer,
			@RequestParam("idUser") String idUser)
	{
		Customer customer = rest.getForObject("http://localhost:8080/customers/show-customer-by-id/"+idCustomer, Customer.class);
		User user = rest.getForObject("http://localhost:8080/users/show-user-by-id/" + idUser, User.class);
		
		AccountCredit credit = new AccountCredit();
		credit.setId(id);
		credit.setIdAccount(idAccount);
		credit.setTypeAccount(typeAccount);
		credit.setBalance(balance);
		credit.setDebt(debt);
		credit.setHanMucTinDung(hanMucTinDung);
		credit.setUser(user);
		credit.setCustomer(customer);
		rest.put("http://localhost:8080/credits/edit-credit/"+id, credit, credit.getId());
		
		return "redirect:/credits/show-all-credit";
	}
	
	@GetMapping("/delete-credit/{id}")
	public String deleteCredit(@PathVariable("id") int id)
	{
		rest.delete("http://localhost:8080/credits/delete-credit/"+id, id);
		System.out.println("id delete "+id);
		return "redirect:/credits/show-all-credit";
	}

}

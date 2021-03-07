package BankManagement.controller;

import java.util.Arrays;
import java.util.Date;
import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.client.RestTemplate;

import BankManagement.model.Customer;

@Controller
@RequestMapping("/customers")
public class CustomerController {

	private RestTemplate rest = new RestTemplate();
	
	@GetMapping("/infor")
	public String customerForm(Model model) {
		model.addAttribute("customer", new Customer());
		return "cus/customerForm";
	}

	
	@GetMapping("/search-id")
	public String searchId(Model model, @RequestParam("id") int id)
	{
		Customer customer = 
				rest.getForObject("http://localhost:8080/customers/show-customer-by-id/"+id, Customer.class);
		model.addAttribute("listCustomer", customer);
		return "cus/showAllCustomer";
	}
	
	@GetMapping("/show-customer-by-id")
	public String showCustomerById(Model model, @RequestParam("id") int id)
	{
		Customer customer = 
				rest.getForObject("http://localhost:8080/customers/show-customer-by-id/"+id, Customer.class);
		model.addAttribute("customerById", customer);
		return "cus/showCustomerById";
	}
	
	
	@GetMapping("/show-all-customer")
	public String showAllCustomer(Model model)
	{
		List<Customer> customerList = Arrays.asList(
				rest.getForObject("http://localhost:8080/customers/show-all-customers",Customer[].class));
		model.addAttribute("listCustomer", customerList);
	
		return "cus/showAllCustomer";
	}
	
	@PostMapping //save customer
	public String saveCustomer(@RequestParam("name") String name,
			@RequestParam("cmt") String cmt,
			@RequestParam("dateOfBirth") Date date,
			@RequestParam("address") String address) 
	{
		Customer customer = new Customer();
		customer.setName(name);
		customer.setCmt(cmt);
		customer.setDateOfBirth(date);
		customer.setAddress(address);
		System.out.println(customer);
		rest.postForObject("http://localhost:8080/customers", customer, Customer.class);
		return "redirect:/customers/show-all-customer";
		
	}
	
	@PostMapping("/edit-customer")
	public String editCustomer(@RequestParam("put_id") int id, 
			@RequestParam("name") String name,
			@RequestParam("cmt") String cmt,
			@RequestParam("dateOfBirth") Date date,
			@RequestParam("address") String address)
	{
		Customer customer = new Customer();
		customer.setId(id);
		customer.setName(name);
		customer.setCmt(cmt);
		customer.setDateOfBirth(date);
		customer.setAddress(address);
		rest.put("http://localhost:8080/customers/edit-customer/"+id, customer, customer.getId());
		
		return "redirect:/customers/show-all-customer";
	}
	
	@GetMapping("/delete-customer/{id}")
	public String deleteCustomer(@PathVariable("id") int id)
	{
		rest.delete("http://localhost:8080/customers/delete-customer/"+id, id);
		System.out.println("id delete "+id);
		return "redirect:/customers/show-all-customer";
	}
	
}

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

import BankManagement.model.User;

@Controller
@RequestMapping("/users")
public class UserController {
	private RestTemplate rest = new RestTemplate();

	@GetMapping
	public String userManagement() {
		return "user/user-Management";
	}

	@GetMapping("/infor")
	public String userForm(Model model) {
		model.addAttribute("user", new User());
		return "user/userForm";
	}

	@GetMapping("/search-id")
	public String searchId(Model model, @RequestParam("id") int id) {
		User user = rest.getForObject("http://localhost:8080/users/show-user-by-id/" + id, User.class);
		model.addAttribute("listUser", user);
		return "user/showAllUser";
	}

	@GetMapping("/show-user-by-id")
	public String showUserById(Model model, @RequestParam("id") int id) {
		User user = rest.getForObject("http://localhost:8080/users/show-user-by-id/" + id,
				User.class);
		model.addAttribute("userById", user);
		return "user/showUserById";
	}

	@GetMapping("/show-all-user")
	public String showAllUser(Model model) {
		List<User> userList = 
				Arrays .asList(rest.getForObject("http://localhost:8080/users/show-all-user", User[].class));
		model.addAttribute("listUser", userList);

		return "user/showAllUser";
	}

	@PostMapping // save user
	public String saveUser(@RequestParam("name") String name, @RequestParam("cmt") String cmt,
			@RequestParam("dateOfBirth") Date date, @RequestParam("address") String address,
			@RequestParam("bacNghe") String bacNghe, @RequestParam("thamNien") String thamNien, 
			@RequestParam("position") String position) {
		User user = new User();
		user.setName(name);
		user.setCmt(cmt);
		user.setDateOfBirth(date);
		user.setAddress(address);
		user.setBacNghe(bacNghe);
		user.setThamNien(thamNien);
		user.setPosition(position);
		System.out.println(user);
		rest.postForObject("http://localhost:8080/users", user, User.class);
		return "redirect:/users/show-all-user";

	}

	@PostMapping("/edit-user")
	public String editUser(@RequestParam("put_id") int id, @RequestParam("name") String name,
			@RequestParam("cmt") String cmt, @RequestParam("dateOfBirth") Date date,
			@RequestParam("address") String address,
			@RequestParam("bacNghe") String bacNghe, @RequestParam("thamNien") String thamNien, 
			@RequestParam("position") String position) {
		User user = new User();
		user.setId(id);
		user.setName(name);
		user.setCmt(cmt);
		user.setDateOfBirth(date);
		user.setAddress(address);
		user.setBacNghe(bacNghe);
		user.setThamNien(thamNien);
		user.setPosition(position);
		rest.put("http://localhost:8080/users/edit-user/" + id, user, user.getId());

		return "redirect:/users/show-all-user";
	}

	@GetMapping("/delete-user/{id}")
	public String deleteUser(@PathVariable("id") int id) {
		rest.delete("http://localhost:8080/users/delete-user/" + id, id);
		System.out.println("id delete " + id);
		return "redirect:/users/show-all-user";
	}
}

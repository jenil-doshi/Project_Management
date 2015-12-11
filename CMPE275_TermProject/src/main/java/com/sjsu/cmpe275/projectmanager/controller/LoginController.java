package com.sjsu.cmpe275.projectmanager.controller;

import java.util.Collection;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.sjsu.cmpe275.projectmanager.configuration.Constants;
import com.sjsu.cmpe275.projectmanager.model.Project;
import com.sjsu.cmpe275.projectmanager.model.User;
import com.sjsu.cmpe275.projectmanager.model.UserRoles;
import com.sjsu.cmpe275.projectmanager.model.Users;
import com.sjsu.cmpe275.projectmanager.service.ProjectService;
import com.sjsu.cmpe275.projectmanager.service.ReportService;
import com.sjsu.cmpe275.projectmanager.service.UserService;

@Controller
@ComponentScan(basePackages = "com.sjsu.cmpe275.projectmanager.service")
public class LoginController {

	@Autowired
	UserService userService;

	@Autowired
	ProjectService projectService;

	@Autowired
	ReportService reportService;

	@RequestMapping(value = { "/create" }, method = RequestMethod.POST, produces = "application/json")
	public String createUser(@ModelAttribute("regForm") User user, RedirectAttributes attributes) {
		ModelAndView mv = new ModelAndView();

		try {
			Users users = new Users();

			UserRoles roles = new UserRoles();
			roles.setRole(Constants.ROLE_ADMIN);
			roles.setUsername(user.getEmail());

			users.setUsername(user.getEmail());
			users.setPassword(user.getPassword());
			users.setEnabled(Constants.ENABLED);

			mv.setViewName("createUser");

			if (userService.createUser(user, roles, users)) {
				mv.addObject("createUser", user);
				// throw new RuntimeException();
				attributes.addAttribute("success", "Registration Success");
				return "redirect:/home";
			}
			attributes.addAttribute("error", "Registration Failure");
			return "redirect:/getRegForm";
		}

		catch (RuntimeException e) {
			user = null;
			mv.addObject("createUser", user);
			e.printStackTrace();
			attributes.addAttribute("error", "Registration Failure");
			return "redirect:/getRegForm";

		}

	}

	@RequestMapping(value = { "/", "/home" }, method = RequestMethod.GET)
	public String defaultPage(HttpServletRequest request, Model model) {
		// System.out.println("User is: " + getPrincipal());
		try {
			User user = getPrincipal();
			String role = null;
			request.getSession().setAttribute("USER", user);
			Collection<GrantedAuthority> authorities = (Collection<GrantedAuthority>) SecurityContextHolder.getContext()
					.getAuthentication().getAuthorities();
			for (GrantedAuthority authority : authorities) {
				if (authority.getAuthority().equalsIgnoreCase(Constants.ROLE_ADMIN)) {
					role = "role_admin";
					break;
				} else if (authority.getAuthority().equalsIgnoreCase(Constants.ROLE_USER)) {
					role = "role_user";
					break;
				}
			}
			List<Project> projectList = projectService.getProjectsForUser(user.getUserId(), role);

			model.addAttribute("report", reportService.getReport(projectList.get(0).getPid()));
			model.addAttribute("projectList", projectList);

		} catch (Exception e) {

			e.printStackTrace();
		}
		// model.addAttribute("user", getPrincipal());
		return "index";

	}

	public User getPrincipal() {
		String userName = null;
		User user = null;
		Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();

		if (principal instanceof UserDetails) {
			userName = ((UserDetails) principal).getUsername();
		} else {
			userName = principal.toString();
		}
		user = userService.getUserByUserName(userName);

		return user;
	}

	@RequestMapping(value = "/admin**", method = RequestMethod.GET)
	public ModelAndView adminPage() {

		ModelAndView model = new ModelAndView();
		model.addObject("title", "Spring Security Login Form - Database Authentication");
		model.addObject("message", "This page is for ROLE_ADMIN only!");
		model.setViewName("admin");

		return model;

	}

	@RequestMapping(value = "/login", method = RequestMethod.GET)
	public String loginPage() {
		return "login";
	}

	@RequestMapping(value = "/logout", method = RequestMethod.GET)
	public String logoutPage(HttpServletRequest request, HttpServletResponse response) {
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		if (auth != null) {
			new SecurityContextLogoutHandler().logout(request, response, auth);
		}
		return "redirect:/login?logout";// You can redirect wherever you want,
										// but generally it's a good idea to
										// show login screen again.
	}

	// for 403 access denied page

	@RequestMapping(value = "/403", method = RequestMethod.GET)
	public ModelAndView accesssDenied() {

		ModelAndView model = new ModelAndView();

		// check if user is login
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		if (!(auth instanceof AnonymousAuthenticationToken)) {
			UserDetails userDetail = (UserDetails) auth.getPrincipal();
			System.out.println(userDetail);

			model.addObject("username", userDetail.getUsername());

		}

		model.setViewName("403");
		return model;

	}

	@RequestMapping(value = "/getRegForm", method = RequestMethod.GET)
	public String getRegForm(Map<String, Object> model) {
		model.put("regForm", new User());

		return "registration";
	}

}

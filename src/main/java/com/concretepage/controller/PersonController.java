package com.concretepage.controller;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.LocaleResolver;
import org.springframework.web.servlet.ModelAndView;

import com.concretepage.entity.Person;
import com.concretepage.service.IPersonService;

@Controller
public class PersonController {

	@Autowired
	private IPersonService personService;

	@Autowired
	private MessageSource messageSource;

	@Autowired
	private LocaleResolver localeResolver;

	@RequestMapping(value = "personform")
	public ModelAndView user() {
		ModelAndView mv = new ModelAndView("personform", "person", new Person());
		setPageData(mv.getModelMap());
		return mv;
	}

	@RequestMapping(value = "addPerson", method = RequestMethod.POST)
	public String addPerson(@ModelAttribute("person") @Valid Person person, BindingResult result, ModelMap model,
			HttpServletRequest request) {
		if (!result.hasErrors()) {
			boolean flag = personService.addPerson(person);
			if (flag) {
				model.addAttribute(new Person());
				model.addAttribute("msg", getMsg("person.added", request));
			} else {
				model.addAttribute("msg", getMsg("person.duplicate", request));
			}
		}
		setPageData(model);
		return "personform";
	}

	@RequestMapping(value = "personById")
	public String getPersonById(ModelMap model, HttpServletRequest request) {
		int pid = Integer.parseInt(request.getParameter("pid"));
		Person person = personService.getPersonById(pid);
		setPageData(model);
		model.addAttribute(person);
		return "personform";
	}

	@RequestMapping(value = "updatePerson", method = RequestMethod.POST)
	public String updatePerson(@ModelAttribute("person") @Valid Person person, BindingResult result, ModelMap model,
			HttpServletRequest request) {
		if (!result.hasErrors()) {
			personService.updatePerson(person);
			model.addAttribute(new Person());
			model.addAttribute("msg", getMsg("person.updated", request));
		}
		setPageData(model);
		return "personform";
	}

	@RequestMapping(value = "deletePerson")
	public String deletePerson(ModelMap model, HttpServletRequest request) {
		int pid = Integer.parseInt(request.getParameter("pid"));
		personService.deletePerson(pid);
		model.addAttribute(new Person());
		model.addAttribute("msg", getMsg("person.deleted", request));
		setPageData(model);
		return "personform";
	}

	private void setPageData(ModelMap model) {
		model.addAttribute("allData", personService.getAllPersons());
		model.addAttribute("genderOptions", Person.getGenderOptions());
		model.addAttribute("cityMap", Person.getPersonMap());
	}

	private String getMsg(String key, HttpServletRequest request) {
		return messageSource.getMessage(key, null, localeResolver.resolveLocale(request));
	}
}
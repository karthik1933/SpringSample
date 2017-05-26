package com.concretepage.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.concretepage.dao.IPersonDAO;
import com.concretepage.entity.Person;

@Service
public class PersonService implements IPersonService {

	@Autowired
	private IPersonDAO personDAO;

	public Person getPersonById(int pid) {
		Person obj = personDAO.getPersonById(pid);
		return obj;
	}

	public List<?> getAllPersons() {
		return personDAO.getAllPersons();
	}

	public synchronized boolean addPerson(Person person) {
		if (personDAO.personExists(person.getUsername())) {
			return false;
		} else {
			personDAO.addPerson(person);
			return true;
		}
	}

	public void updatePerson(Person person) {
		personDAO.updatePerson(person);
	}

	public void deletePerson(int pid) {
		personDAO.deletePerson(pid);
	}

	@Override
	public List<?> newDistrictWiseVillagesDetails() {
		System.out.println("enter into service");
		List<?> a=personDAO.newDistrictWiseVillagesDetails();
		
		return a;
	}
}

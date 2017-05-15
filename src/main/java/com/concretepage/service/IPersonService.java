package com.concretepage.service;

import java.util.List;

import com.concretepage.entity.Person;

public interface IPersonService {

	List<?> getAllPersons();

	Person getPersonById(int pid);

	boolean addPerson(Person person);

	void updatePerson(Person person);

	void deletePerson(int pid);
}

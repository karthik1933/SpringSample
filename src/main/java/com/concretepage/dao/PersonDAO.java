package com.concretepage.dao;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.orm.hibernate4.HibernateTemplate;
import org.springframework.stereotype.Repository;

import com.concretepage.entity.Person;

@Transactional
@Repository
public class PersonDAO implements IPersonDAO {

	@Autowired
	private HibernateTemplate hibernateTemplate;

	public Person getPersonById(int pid) {
		return hibernateTemplate.get(Person.class, pid);
		
		
	}

	public List<?> getAllPersons() {
		String hql = "FROM Person as p ORDER BY p.pid";
		return (List<?>) hibernateTemplate.find(hql);
	}

	public void addPerson(Person person) {
		hibernateTemplate.save(person);
	}

	public void updatePerson(Person person) {
		Person p = getPersonById(person.getPid());
		p.setUsername(person.getUsername());
		p.setPassword(person.getPassword());
		p.setAge(person.getAge());
		p.setGender(person.getGender());
		p.setCity(person.getCity());
		hibernateTemplate.update(p);
	}

	public void deletePerson(int pid) {
		hibernateTemplate.delete(getPersonById(pid));
	}

	public boolean personExists(String username) {
		String hql = "FROM Person as p WHERE p.username = ?";
		List<?> persons = (List<?>) hibernateTemplate.find(hql, username);
		return persons.size() > 0 ? true : false;
	}
	public List<?> newDistrictWiseVillagesDetails() {
		System.out.println("enter into dao");
		String hql = "select pm.primarykeys.dist_code as new_dist_code,dm.dname as new_dist_name,count(*) as total from PanchayathMaster pm "
				+ "join CggMasterDistricts dm on pm.primarykeys.dist_code=dm.code where code not in (100) group by dist_code,dname "
				+ "order by dist_code";
		
		String sql="select pm.dist_code as new_dist_code,dm.dname as new_dist_name,count(*) as total from panchayat_master pm join cgg_master_districts dm on pm.dist_code=dm.code where code not in (100) group by dist_code,dname order by dist_code";
		System.out.println(sql);
		return (List<?>) hibernateTemplate.find(sql);
	}
}

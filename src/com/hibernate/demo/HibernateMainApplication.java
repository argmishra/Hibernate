package com.hibernate.demo;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.boot.MetadataSources;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;

import com.hibernate.demo.entity.Employee;
import com.hibernate.demo.entity.User;
import com.hibernate.demo.entity.tph.BasicSalary;
import com.hibernate.demo.entity.tph.BonusSalary;
import com.hibernate.demo.entity.tph.TotalSalary;

public class HibernateMainApplication {

	private SessionFactory factory;
	private Session session;
	private Transaction transaction;

	public static void main(String[] args) {
		HibernateMainApplication app = new HibernateMainApplication();
		app.openConnection();
		app.saveByXML();
		app.saveByAnnotation();
		app.saveByTablePerHierarchy();
		app.closeConnection();
	}

	public void saveByTablePerHierarchy() {
		TotalSalary totalSalary = new TotalSalary();
		totalSalary.setName("Sonu");
		BasicSalary basicSalary = BasicSalary.builder().amount(15000L).medical(true).build();
		BonusSalary bonusSalary = BonusSalary.builder().tax("20").travel(false).build();
		session.persist(totalSalary);
		session.persist(basicSalary);
		session.persist(bonusSalary);
	}

	public void saveByXML() {
		Employee employee = Employee.builder().firstName("Anurag").lastName("Mishra").build();
		session.save(employee);
	}

	public void saveByAnnotation() {
		User user = User.builder().firstName("Optimus").lastName("Prime").build();
		session.save(user);
	}

	public void openConnection() {
		factory = new MetadataSources(
				new StandardServiceRegistryBuilder().configure("config/hibernate.cfg.xml").build()).getMetadataBuilder()
						.build().getSessionFactoryBuilder().build();
		session = factory.openSession();
		transaction = session.beginTransaction();
	}

	public void closeConnection() {
		transaction.commit();
		System.out.println("Successfully Saved");
		factory.close();
		session.close();
	}

}

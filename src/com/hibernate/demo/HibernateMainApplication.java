package com.hibernate.demo;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.boot.MetadataSources;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;

import com.hibernate.demo.entity.Employee;
import com.hibernate.demo.entity.User;

public class HibernateMainApplication {

	private SessionFactory factory;
	private Session session;
	private Transaction transaction;

	public static void main(String[] args) {
		HibernateMainApplication app = new HibernateMainApplication();
		app.openConnection();
		app.save();
		app.closeConnection();
	}

	public void save() {
		Employee employee = Employee.builder().firstName("Anurag").lastName("Mishra").build();
		session.save(employee);
		User user = User.builder().firstName("Optimus").lastName("Prime").build();
		session.save(user);
		transaction.commit();
		System.out.println("Successfully Saved");
	}

	public void openConnection() {
		factory = new MetadataSources(
				new StandardServiceRegistryBuilder().configure("config/hibernate.cfg.xml").build()).getMetadataBuilder()
						.build().getSessionFactoryBuilder().build();
		session = factory.openSession();
		transaction = session.beginTransaction();
	}

	public void closeConnection() {
		factory.close();
		session.close();
	}

}

package com.hibernate.demo;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.boot.MetadataSources;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;

import com.hibernate.demo.entity.Employee;

public class HibernateWithXMLMainApplication {

	private SessionFactory factory;
	private Session session;
	private Transaction transaction;

	public static void main(String[] args) {
		HibernateWithXMLMainApplication app = new HibernateWithXMLMainApplication();
		app.openConnection();
		app.save();
		app.closeConnection();
	}

	public void save() {
		Employee employee = Employee.builder().id(1L).firstName("Anurag").lastName("Mishra").build();
		session.save(employee);
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

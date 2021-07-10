package com.hibernate.demo;

import java.time.Instant;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.boot.MetadataSources;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.criterion.Projections;
import org.hibernate.query.Query;

import com.hibernate.demo.entity.Country;
import com.hibernate.demo.entity.Employee;
import com.hibernate.demo.entity.User;
import com.hibernate.demo.entity.collection.Fruit;
import com.hibernate.demo.entity.collection.Password;
import com.hibernate.demo.entity.collection.Vegetable;
import com.hibernate.demo.entity.component.Hardware;
import com.hibernate.demo.entity.component.Laptop;
import com.hibernate.demo.entity.tpcc.Bollywood;
import com.hibernate.demo.entity.tpcc.Hollywood;
import com.hibernate.demo.entity.tpcc.Movie;
import com.hibernate.demo.entity.tph.BasicSalary;
import com.hibernate.demo.entity.tph.BonusSalary;
import com.hibernate.demo.entity.tph.TotalSalary;
import com.hibernate.demo.entity.tpsc.Exterior;
import com.hibernate.demo.entity.tpsc.Interior;
import com.hibernate.demo.entity.tpsc.Vehicle;

public class HibernateMainApplication {

	private SessionFactory factory;
	private Session session;
	private Transaction transaction;
	private Session anotherSession;
	private Transaction anotherTransaction;

	public static void main(String[] args) {
		HibernateMainApplication app = new HibernateMainApplication();
		app.openConnection();
		// app.all(app);
		app.closeConnection();
	}

	public void all(HibernateMainApplication app) {
		app.saveByXML();
		app.saveByAnnotation();
		app.saveByTablePerHierarchy();
		app.saveByTablePerConcreteClass();
		app.saveByTablePerSubClass();
		app.HQLDemo();
		app.HCQLDemo();
		app.cacheDemo();
		app.collectionDemo();
		app.componentDemo();
	}

	public void collectionDemo() {
		// List
		Fruit fruit1 = new Fruit();
		fruit1.setName("Mango");
		fruit1.setLocations(List.of("Mumbai", "Gujrat"));
		session.save(fruit1);

		Fruit fruit2 = new Fruit();
		fruit2.setName("Coconut");
		fruit2.setLocations(List.of("Kerla", "AP"));
		session.save(fruit2);

		// Set
		Vegetable vegetable1 = new Vegetable();
		vegetable1.setName("Capsicum");
		vegetable1.setColours(Set.of("Red", "Yellow", "Green"));
		session.save(vegetable1);

		Vegetable vegetable2 = new Vegetable();
		vegetable2.setName("Onion");
		vegetable2.setColours(Set.of("Red", "Green"));
		session.save(vegetable2);

		// Map
		Password password1 = new Password();
		password1.setWebsite("google");
		password1.setHint(Map.of("mail", "argmishra.ece@Gmail.com", "number", "1234"));
		session.save(password1);

		Password password2 = new Password();
		password2.setWebsite("facebook");
		password2.setHint(Map.of("user", "argmishra", "number", "1234"));
		session.save(password2);
	}

	public void componentDemo() {
		// Component
		Hardware hardware1 = new Hardware();
		hardware1.setName("CPU");
		hardware1.setWorking(true);

		Hardware hardware2 = new Hardware();
		hardware2.setName("RAM");
		hardware2.setWorking(true);

		Hardware hardware3 = new Hardware();
		hardware3.setName("RAM");
		hardware3.setWorking(false);

		Hardware hardware4 = new Hardware();
		hardware4.setName("Speaker");
		hardware4.setWorking(false);

		Laptop laptop1 = new Laptop();
		laptop1.setBrand("HP");
		laptop1.setHardware(hardware1);

		Laptop laptop2 = new Laptop();
		laptop2.setBrand("Dell");
		laptop2.setHardware(hardware2);

		Laptop laptop3 = new Laptop();
		laptop3.setBrand("Lenovo");
		laptop3.setHardware(hardware3);

		Laptop laptop4 = new Laptop();
		laptop4.setBrand("Mac");
		laptop4.setHardware(hardware4);

		session.save(laptop1);
		session.save(laptop2);
		session.save(laptop3);
		session.save(laptop4);

	}

	public void cacheDemo() {
		System.out.println("First Time Result = " + getResult(session).getName());
		System.out.println("First Level Cache Result = " + getResult(session).getName());

		this.openAnotherConnection();
		System.out.println("Second Level Cache Result = " + getResult(anotherSession).getName());
		this.closeAnotherConnection();
	}

	private static Country getResult(Session session) {
		return session.load(Country.class, 1L);
	}

	public void HQLDemo() {
		Query<?> query = session.createQuery("from User");
		query.list().forEach(user -> {
			System.out.println("HQL Demo = " + user);
		});
	}

	public void HCQLDemo() {
		Criteria criteria = session.createCriteria(Employee.class);
		criteria.setProjection(Projections.property("firstName"));
		criteria.list().forEach(user -> {
			System.out.println("HCQL Demo = " + user);
		});
	}

	public void saveByTablePerSubClass() {
		Vehicle vehicle = new Vehicle();
		vehicle.setModel("Getz");
		Interior interior1 = Interior.builder().rating("4").model("Swift").build();
		Interior interior2 = Interior.builder().rating("4.5").model("Honda").build();
		Exterior exterior = Exterior.builder().rating("3.5").model("Alto").build();
		session.persist(vehicle);
		session.persist(interior1);
		session.persist(exterior);
		session.persist(interior2);
	}

	public void saveByTablePerConcreteClass() {
		Movie movie = new Movie();
		movie.setName("Transformer");
		Hollywood hollywood = new Hollywood();
		hollywood.setName("Mission Impossible");
		hollywood.setHero("Tom");
		hollywood.setDate(Instant.now());
		Bollywood bollywood = new Bollywood();
		bollywood.setName("Loveshhuda");
		bollywood.setHero("Girish");
		bollywood.setDate(Instant.now());
		session.persist(movie);
		session.persist(hollywood);
		session.persist(bollywood);
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

		Country country = Country.builder().name("Ireland").population("10000").build();
		session.save(country);
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
		session.close();
		factory.close();
	}

	public void openAnotherConnection() {
		anotherSession = factory.openSession();
		anotherTransaction = anotherSession.beginTransaction();
	}

	public void closeAnotherConnection() {
		anotherTransaction.commit();
		anotherSession.close();
	}

}

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
import com.hibernate.demo.entity.mappings.Car;
import com.hibernate.demo.entity.mappings.Customer;
import com.hibernate.demo.entity.mappings.Mobile;
import com.hibernate.demo.entity.mappings.Number;
import com.hibernate.demo.entity.mappings.Office;
import com.hibernate.demo.entity.mappings.Reader;
import com.hibernate.demo.entity.mappings.Software;
import com.hibernate.demo.entity.mappings.Subscription;
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
		app.all(app);
		app.closeConnection();
		app.secondLevelCacheDemo();
	}

	public void all(HibernateMainApplication app) {
		app.saveByXML();
		app.saveByAnnotation();
		app.HQLDemo();
		app.HCQLDemo();
		app.saveByTablePerHierarchy();
		app.saveByTablePerConcreteClass();
		app.saveByTablePerSubClass();
		app.collectionDemo();
		app.componentDemo();
		app.firstLevelCacheDemo();
		app.oneToManyMapping();
		app.oneToOneMapping();
		app.manyToOneMapping();
		app.manyToManyMapping();
	}

	public void manyToOneMapping() {
		Customer customer = Customer.builder().userName("Anu").build();
		session.save(Car.builder().customer(customer).vehicleName("Getz").build());
		session.save(Car.builder().customer(customer).vehicleName("Dezire").build());
		session.save(customer);

		session.createQuery("from Customer").list().forEach(result -> {
			System.out.println("Customer = " + result);
		});

		session.createQuery("from Car").list().forEach(result -> {
			System.out.println("Car = " + result);
		});
	}

	public void manyToManyMapping() {
		Subscription subOne = Subscription.builder().subscriptionName("Entertainment").build();
		Subscription subTwo = Subscription.builder().subscriptionName("Horror").build();

		session.save(Reader.builder().firstName("Anu").subscriptions(Set.of(subOne, subTwo)).build());
		session.save(Reader.builder().firstName("Dipu").subscriptions(Set.of(subOne, subTwo)).build());

		session.createQuery("from Subscription").list().forEach(result -> {
			System.out.println("Subscription = " + result);
		});

		session.createQuery("from Reader").list().forEach(result -> {
			System.out.println("Reader = " + result);
		});
	}

	public void oneToManyMapping() {
		session.save(Office.builder().email("a@gmail.com")
				.numbers(Set.of(Number.builder().type("mobile").build(), Number.builder().type("phone").build()))
				.build());
		session.save(
				Office.builder().email("b@gmail.com").numbers(Set.of(Number.builder().type("fax").build())).build());

		session.createQuery("from Office").list().forEach(result -> {
			System.out.println("Office = " + result);
		});

		session.createQuery("from Number").list().forEach(result -> {
			System.out.println("Number = " + result);
		});
	}

	public void oneToOneMapping() {
		Software software = Software.builder().ram("2.3GB").version(3).build();
		session.save(software);
		session.save(Mobile.builder().name("Iphone").software(software).build());

		session.createQuery("from Mobile").list().forEach(result -> {
			System.out.println("Mobile = " + result);
		});

		session.createQuery("from Software").list().forEach(result -> {
			System.out.println("Software = " + result);
		});
	}

	public void collectionDemo() {
		// List
		session.save(Fruit.builder().name("Mango").locations(List.of("Mumbai", "Gujrat")).build());
		session.save(Fruit.builder().name("Coconut").locations(List.of("Kerla", "AP")).build());

		// Set
		session.save(Vegetable.builder().name("Capsicum").colours(Set.of("Red", "Yellow", "Green")).build());
		session.save(Vegetable.builder().name("Onion").colours(Set.of("Red", "Green")).build());

		// Map
		session.save(Password.builder().website("google")
				.hint(Map.of("mail", "argmishra.ece@Gmail.com", "number", "1234")).build());
		session.save(
				Password.builder().website("facebook").hint(Map.of("user", "argmishra", "number", "1234")).build());
	}

	public void componentDemo() {
		session.save(
				Laptop.builder().brand("HP").hardware(Hardware.builder().name("CPU").working(true).build()).build());
		session.save(
				Laptop.builder().brand("Dell").hardware(Hardware.builder().name("RAM").working(true).build()).build());
		session.save(Laptop.builder().brand("Lenovo").hardware(Hardware.builder().name("RAM").working(false).build())
				.build());
		session.save(Laptop.builder().brand("Mac").hardware(Hardware.builder().name("Speaker").working(false).build())
				.build());
	}

	public void firstLevelCacheDemo() {
		session.save(Country.builder().name("Ireland").population("10000").build());
		System.out.println("First Time Result = " + getResult(session).getName());
		System.out.println("First Level Cache Result = " + getResult(session).getName());
	}

	public void secondLevelCacheDemo() {
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
		session.persist(vehicle);
		session.persist(Interior.builder().rating("4").model("Swift").build());
		session.persist(Exterior.builder().rating("3.5").model("Alto").build());
		session.persist(Interior.builder().rating("4.5").model("Honda").build());
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
		session.persist(totalSalary);
		session.persist(BasicSalary.builder().amount(15000L).medical(true).build());
		session.persist(BonusSalary.builder().tax("20").travel(false).build());
	}

	public void saveByXML() {
		session.save(Employee.builder().firstName("Anurag").lastName("Mishra").build());
	}

	public void saveByAnnotation() {
		session.save(User.builder().firstName("Optimus").lastName("Prime").build());
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
	}

	public void openAnotherConnection() {
		anotherSession = factory.openSession();
		anotherTransaction = anotherSession.beginTransaction();
	}

	public void closeAnotherConnection() {
		anotherTransaction.commit();
		anotherSession.close();
		factory.close();
	}

}
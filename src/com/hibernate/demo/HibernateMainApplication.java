package com.hibernate.demo;

import java.time.Instant;

import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.boot.MetadataSources;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.criterion.Projections;
import org.hibernate.query.Query;

import com.hibernate.demo.entity.Employee;
import com.hibernate.demo.entity.User;
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

	public static void main(String[] args) {
		HibernateMainApplication app = new HibernateMainApplication();
		app.openConnection();
		app.saveByXML();
		app.saveByAnnotation();
		app.saveByTablePerHierarchy();
		app.saveByTablePerConcreteClass();
		app.saveByTablePerSubClass();
		app.HQLDemo();
		app.HCQLDemo();
		app.closeConnection();
	}

	public void HQLDemo() {
		Query<?> query = session.createQuery("from User");
		query.list().forEach(user -> {
			System.out.println("HQL = " + user);
		});
	}

	public void HCQLDemo() {
		Criteria criteria = session.createCriteria(Employee.class);
		criteria.setProjection(Projections.property("firstName"));
		criteria.list().forEach(user -> {
			System.out.println("HCQL = " + user);
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
		factory.close();
		session.close();
	}

}

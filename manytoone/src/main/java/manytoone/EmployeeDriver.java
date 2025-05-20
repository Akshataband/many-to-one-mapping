package manytoone;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;
import javax.persistence.Query;

public class EmployeeDriver {
	public static void main(String[] args) {
//		addEmp();
//		addDept();
//		allocateDept(3,105);
//		deallocateDept(4);
//		updateEmp(5,"Shaili",50000) ;
//		deleteEmployeeById(4);
//		findDeptById(103);
//		findAllEmployees(6);
		deleteDeptById(104);
	}

	public static void addEmp() {
		EntityManagerFactory emf = Persistence.createEntityManagerFactory("postgres");
		EntityManager em = emf.createEntityManager();
		EntityTransaction et = em.getTransaction();

//		Department d = new Department();
//		d.setName("Testing");

		Employee e1 = new Employee();
		e1.setName("Guri");
		e1.setSalary(30000);
//		e1.setD(d); // assign department

		Employee e2 = new Employee();
		e2.setName("Abhilash");
		e2.setSalary(80000);
//		e2.setD(d);

		et.begin();
//		em.persist(d);
		em.persist(e1);
		em.persist(e2);
		et.commit();
	}

	public static void addDept() {
		EntityManagerFactory emf = Persistence.createEntityManagerFactory("postgres");
		EntityManager em = emf.createEntityManager();
		EntityTransaction et = em.getTransaction();

		Department d = new Department();
		d.setName("Data Analyst");

		et.begin();
		em.persist(d);
		et.commit();
	}

	public static void allocateDept(int eid, int did) {
		EntityManagerFactory emf = Persistence.createEntityManagerFactory("postgres");
		EntityManager em = emf.createEntityManager();
		EntityTransaction et = em.getTransaction();

		et.begin();
		Department d = em.find(Department.class, did);
		Employee e = em.find(Employee.class, eid);
		e.setD(d);
		em.merge(e);
		et.commit();
	}

	public static void deallocateDept(int eid) {
		EntityManagerFactory emf = Persistence.createEntityManagerFactory("postgres");
		EntityManager em = emf.createEntityManager();
		EntityTransaction et = em.getTransaction();

		et.begin();
		Employee e = em.find(Employee.class, eid);
		e.setD(null);
		em.merge(e);
		et.commit();
	}

	public static void updateEmp(int eid, String newName, double newSalary) {
		EntityManagerFactory emf = Persistence.createEntityManagerFactory("postgres");
		EntityManager em = emf.createEntityManager();
		EntityTransaction et = em.getTransaction();

		et.begin();
		Employee e = em.find(Employee.class, eid);
		e.setName(newName);
		e.setSalary(newSalary);
		em.merge(e);
		et.commit();
	}

	public static void deleteEmployeeById(int eid) {
		EntityManagerFactory emf = Persistence.createEntityManagerFactory("postgres");
		EntityManager em = emf.createEntityManager();
		EntityTransaction et = em.getTransaction();

		et.begin();
		Employee e = em.find(Employee.class, eid);
		em.remove(e);
		et.commit();
	}

	public static void deleteDeptById(int did) {
		EntityManagerFactory emf = Persistence.createEntityManagerFactory("postgres");
		EntityManager em = emf.createEntityManager();
		EntityTransaction et = em.getTransaction();

		et.begin();
		String s = "UPDATE Employee e SET e.d = null WHERE e.d.id = :did";
		Query q = em.createQuery(s);
		q.setParameter("did", did);
		q.executeUpdate();

		String s2 = "DELETE FROM Department d WHERE d.id = :did";
		Query q1 = em.createQuery(s2);
		q1.setParameter("did", did);
		q1.executeUpdate();
		et.commit();

	}

	public static void findDeptById(int did) {
		EntityManagerFactory emf = Persistence.createEntityManagerFactory("postgres");
		EntityManager em = emf.createEntityManager();
		EntityTransaction et = em.getTransaction();

		et.begin();
		Department d = em.find(Department.class, did);
		et.commit();
		System.out.println("Department ID: " + d.getName());

	}

	public static void findAllEmployees(int eid) {
		EntityManagerFactory emf = Persistence.createEntityManagerFactory("postgres");
		EntityManager em = emf.createEntityManager();
		EntityTransaction et = em.getTransaction();

		String s = "SELECT e FROM Employee e WHERE e.id = :eid";
		et.begin();
		Query q = em.createQuery(s);
		q.setParameter("eid", eid);
		List<Employee> e = q.getResultList();
		et.commit();

		e.forEach(al -> System.out
				.println(al.getName() + " " + al.getId() + " " + al.getSalary() + " " + al.getD().getName()));
	}
}
package fr.excilys.db.daoImp;
import java.util.List;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.query.Query;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import fr.excilys.db.dao.DaoComputer;
import fr.excilys.db.model.Computer;
@Repository
public class ComputerDaoImpl implements DaoComputer {
	private static final Logger LOGGER = LoggerFactory.getLogger(ComputerDaoImpl.class);
	@Autowired
	SessionFactory sessionFactory;
	@Override
	public List<Computer> getAllComputers() {
		Session session = sessionFactory.openSession();
		Query<Computer> query=session.createQuery("from Computer", Computer.class);
		List<Computer> computers =query.getResultList();
		return computers;
	}

	@Override
	public Computer getComputerDetails(int id) {
		Session session=sessionFactory.openSession();
		Computer computer=session.get(Computer.class, id);
		return computer;
	}

	@Override
	public void createComputer(Computer computer) {
		Session session = sessionFactory.openSession();
		session.saveOrUpdate(computer);
	}

	@Override
	public void updateComputer(Computer computer) {
		Session session= sessionFactory.openSession();
		Transaction transaction=session.beginTransaction();
		session.update(computer);
		transaction.commit();

	}

	@Override
	public void deleteComputer(int id) {
		Session session = sessionFactory.openSession();
		Transaction transaction=session.beginTransaction();
		Computer computer=getComputerDetails(id);
		session.delete(computer);
		transaction.commit();
	}

	@Override
	public List<Computer> getComputersByPageNumber(int pageId, int pageSize) {
		Session session = sessionFactory.openSession();

		List<Computer> computers = session.createQuery("from Computer", Computer.class).setFirstResult(pageId)
				.setMaxResults(pageSize).getResultList();
		return computers;
	}

	@Override
	public int getNumberOfPages(int pageSize) {
		int numberOflines = getAllComputers().size();
		int numberOfPages = (numberOflines % pageSize == 0) ? numberOflines / pageSize : numberOflines / pageSize + 1;
		return numberOfPages;
	}

	@Override
	public List<Computer> getComputersByName(String name, int size, int numPage) {
		LOGGER.info("getting computer by name is running");
		Session session = sessionFactory.openSession();
		Query<Computer> query = session.createQuery("from Computer where name like concat('%',:name,'%')", Computer.class)
				.setFirstResult(numPage).setMaxResults(size);
		query.setParameter("name", name);
		return query.getResultList();
	}

	@Override
	public int getPagesNumberByName(int pageSize, String name) {
		Session session=sessionFactory.openSession();
		Query<Computer> query=session.createQuery("from Computer where name like concat('%',:name,'%'", Computer.class);
		int numberOfPages=query.getResultList().size();
		return numberOfPages;
	}

	@Override
	public List<Computer> getComputersByOrder(String order, int sizePage, int numPage) {
		Session session = sessionFactory.openSession();
		Query<Computer> query = session.createQuery("from Computer order by name " + order, Computer.class);
		return query.setFirstResult(numPage).setMaxResults(sizePage).getResultList();
	}

}
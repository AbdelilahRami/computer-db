package fr.excilys.db.dao;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import fr.excilys.db.model.User;

@Repository
public class UserDao {
	private static final Logger LOGGER = LoggerFactory.getLogger(UserDao.class);

	@Autowired
	private SessionFactory sessionFactory;
	
	
	public boolean addUser(User user) {
		boolean success = false;
		try {
			Session session = sessionFactory.openSession();
			session.saveOrUpdate(user);
			success = true;
		} catch (Exception e) {
			LOGGER.error("Error in adding operation");
		}
		return success;
	}
	public boolean delete(User user) {
		boolean success = false;
		try {
			Session session = sessionFactory.openSession();
			session.delete(user);
			success = true;
		} catch (Exception e) {
			LOGGER.error("Error in deleting operation");
		}
		return success;
	}
	

}

package fr.excilys.db.dao;
import java.util.List;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import fr.excilys.db.model.Company;
@Repository
public class CompanyDaoImpl {
	private static final String GET_ALL_COMPANIES = "from Company";
	private static final Logger LOGGER = LoggerFactory.getLogger(ComputerDaoImpl.class);
	
	SessionFactory sessionFactory;
	@Autowired
	public CompanyDaoImpl(SessionFactory sessionFactory) {
		super();
		this.sessionFactory = sessionFactory;
	}

	public List<Company> getAllyCompanies() {
		Session session=sessionFactory.openSession();
		return session.createQuery( GET_ALL_COMPANIES, Company.class).getResultList();
	}
	
	public Company getCompanyById(int idCompany) {
		Session session=sessionFactory.openSession();
		Company company=session.get(Company.class, idCompany);
		return company;
	}


}

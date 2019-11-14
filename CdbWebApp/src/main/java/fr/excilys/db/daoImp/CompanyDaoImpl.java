package fr.excilys.db.daoImp;
import java.util.List;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import fr.excilys.db.dao.DaoCompany;
import fr.excilys.db.model.Company;
@Repository
public class CompanyDaoImpl implements DaoCompany {
	private static final String GET_ALL_COMPANIES = "from Company";
	//private static final String GET_COMPANY_BY_ID = "select * from company where id = ?";
	private static final Logger LOGGER = LoggerFactory.getLogger(ComputerDaoImpl.class);
	@Autowired
	SessionFactory sessionFactory;
	@Override
	public List<Company> getAllyCompanies() {
		Session session=sessionFactory.openSession();
		return session.createQuery( GET_ALL_COMPANIES, Company.class).getResultList();
	}
	@Override
	public Company getCompanyById(int idCompany) {
		Session session=sessionFactory.openSession();
		Company company=session.get(Company.class, idCompany);
		 return company;
	}


}

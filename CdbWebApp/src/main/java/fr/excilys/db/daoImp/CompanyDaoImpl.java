package fr.excilys.db.daoImp;
import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import fr.excilys.db.dao.DaoCompany;
import fr.excilys.db.mapper.CompanMapper;
import fr.excilys.db.model.Company;
@Repository
public class CompanyDaoImpl implements DaoCompany {
	private static final String GET_ALL_COMPANIES = "select company.id, company.name from company";
	private static final String GET_COMPANY_BY_ID = "select * from company where id = ?";
	private static final Logger LOGGER = LoggerFactory.getLogger(ComputerDaoImpl.class);
	@Autowired
	private JdbcTemplate jdbcTemplate;
	@Autowired
	private CompanMapper compannMapper;
	@Override
	public List<Company> getAllyCompanies() {
		List<Company> companies=null;
		try {
		companies=jdbcTemplate.query(GET_ALL_COMPANIES, compannMapper);
		}catch (DataAccessException e){
			LOGGER.error("Data acces exception "+e.getMessage());
		}
		return companies;		
	}
	@Override
	public Company getCompanyById(int idCompany) {
		Company company=null;
		try {
			company=jdbcTemplate.queryForObject(GET_COMPANY_BY_ID, compannMapper,idCompany);
		}catch (DataAccessException e){
			LOGGER.error("Data acces exception "+e.getMessage());
		}
		 return company;
	}


}

package fr.excilys.db.daoImp;
import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import fr.excilys.db.dao.DaoComputer;
import fr.excilys.db.mapper.CompanMapper;
import fr.excilys.db.mapper.ComputerMapper;
import fr.excilys.db.mapper.PageMappper;
import fr.excilys.db.model.Computer;
@Repository
public class ComputerDaoImpl implements DaoComputer {
	private static final String GET_ALL_COMPUTERS = "select computer.id, computer.name, computer.introduced, "
			+ "computer.discontinued, computer.company_id, company.name from computer "
			+ "left join company on computer.company_id=company.id";
	private static final String GET_COMPUTERS_DETAILS = "select computer.id, computer.name, computer.introduced, "
			+"computer.discontinued, computer.company_id, company.name from computer "
			+"left join company on computer.company_id=company.id where computer.id=?";
	private static final String CREATE_COMPUTER = "INSERT INTO computer (name,introduced,discontinued,company_id) VALUES (?,?,?,?)";
	private static final String UPDATE_COMPUTER = "UPDATE computer SET name = ?, introduced = ?, discontinued = ?, company_id = ? WHERE id = ?";
	private static final String DELETE_COMPUTER = "delete from computer where id = ?";
	private static final String GET_COMPUTERS_BY_PAGE = "select computer.id, computer.name, computer.introduced, computer.discontinued, company.name, computer.company_id"
			+ " from computer left join company on computer.company_id=company.id LIMIT ?, ?";
	private static final String GET_COMPUTERS_BY_NAME = "select computer.id, computer.name, computer.introduced,"
			+ " computer.discontinued,computer.company_id, company.name "
			+ "from computer left join company on computer.company_id=company.id"
			+ " where computer.name like ? or company.name like ? order by computer.name limit ? offset ?";
	private static final String GET_NUMBER_OF_COMPUTERS_BY_NAME = "select count(*) as number from computer left  join"
			+ " company on computer.company_id = company.id " + "where computer.name like ? or company.name like ? ";
	private static final String DELETE_COMPUTERS_BY_COMPANY = "delete from computer where company_id = ?";
	private static final String GET_COMPUTERS_BY_ORDER = "select computer.id, computer.name, computer.introduced, computer.discontinued,"
			+ "computer.company_id, company.name from computer left join company on computer.company_id=company.id order by computer.name ";
	private static final String LIMIT = " limit ? offset ?";
	private static final Logger LOGGER = LoggerFactory.getLogger(ComputerDaoImpl.class);
	@Autowired
	JdbcTemplate jdbcTemplate;
	
	@Autowired
	ComputerMapper computerMapper;
	@Autowired
	PageMappper pageMapper;
	@Autowired
	CompanMapper compannMapper;

	@Override
	public List<Computer> getAllComputers() {
		List<Computer> computers=null;
		try {
		computers=jdbcTemplate.query(GET_ALL_COMPUTERS, computerMapper);
		}catch (DataAccessException e){
			LOGGER.error("Data acces exception"+e.getMessage());
		}
		return computers;
	}

	@Override
	public Computer getComputerDetails(int id) {
		Computer computer=null;
		try {
			computer= jdbcTemplate.queryForObject(GET_COMPUTERS_DETAILS,computerMapper, id );
		}catch (DataAccessException e) {
			LOGGER.error("Data acces exception "+e.getMessage());
		}
		return computer;
	}

	@Override
	public int createComputer(Computer computer) {
		LOGGER.info("creation of a computer is running");
		int isCreated=0;
		try {
			isCreated=jdbcTemplate.update(CREATE_COMPUTER, 
				computer.getName(),computer.getIntroducedDate(),computer.getDiscountedDate(),
				computer.getCompany().getIdCompany());
		}catch (DataAccessException e){
			LOGGER.error("Data acces exception "+e.getMessage());
		}
		 return isCreated;
	}

		
	@Override
	public int updateComputer(Computer computer) {
		LOGGER.info("updating a computer is running");
		int isUpdated=0;
		try {
			isUpdated= jdbcTemplate.update(UPDATE_COMPUTER, 
				computer.getName(),computer.getIntroducedDate(),computer.getDiscountedDate(),
				computer.getCompany().getIdCompany(), computer.getId());
		}catch (DataAccessException e){
			LOGGER.error("Data acces exception "+e.getMessage());
		}
		return isUpdated;
	}

	@Override
	public int deleteComputer(int id) throws DataAccessException {
		LOGGER.info("deleting a computer is running");
		int isDeleted=0;
		try {
			isDeleted=jdbcTemplate.update(DELETE_COMPUTER,id);
		}catch (DataAccessException e){
			LOGGER.error("Data acces exception "+e.getMessage());
		}
		return isDeleted;
	}



	@Override
	public List<Computer> getComputersByPageNumber(int pageId, int pageSize) {
		List<Computer> computers=null;
		try {
			computers = jdbcTemplate.query(GET_COMPUTERS_BY_PAGE, computerMapper,pageId,pageSize);
		}catch (DataAccessException e){
			LOGGER.error("Data acces exception "+e.getMessage());
		}
		return computers;
	}


	@Override
	public int getNumberOfPages(int pageSize) {
		int numberOflines = jdbcTemplate.queryForObject("select count(*) as number from computer", Integer.class);
		int	numberOfPages = (numberOflines % pageSize == 0) ? numberOflines / pageSize : numberOflines / pageSize + 1;

		return numberOfPages;
	}

	@Override
	public List<Computer> getComputersByName(String name, int size, int numPage) {
		LOGGER.info("getting computer by name is running");
		int offset = (numPage - 1) * size + 1;
		List<Computer> computers=jdbcTemplate.query(GET_COMPUTERS_BY_NAME, 
				new Object[] {"%"+name+"%","%"+name+"%",size,offset}, computerMapper);
		return computers;
	}



	@Override
	public int getPagesNumberByName(int pageSize, String name) {
		int numberOflines = jdbcTemplate.queryForObject
				(GET_NUMBER_OF_COMPUTERS_BY_NAME, new Object[] {"%" + name + "%","%" + name + "%"}, Integer.class);
		int	numberOfPages = (numberOflines % pageSize == 0) ? numberOflines / pageSize : numberOflines / pageSize + 1;
		return numberOfPages;
	}



	@Override
	public List<Computer> getComputersByOrder(String order, int sizePage, int numPage) {
		int offset = (numPage - 1) * sizePage + 1;
		List<Computer> computers=jdbcTemplate.
				query(GET_COMPUTERS_BY_ORDER + order + LIMIT, new Object[] {sizePage,offset}, computerMapper);
		return computers;
	}

}
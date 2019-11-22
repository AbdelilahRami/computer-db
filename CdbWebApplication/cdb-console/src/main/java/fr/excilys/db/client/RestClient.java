package fr.excilys.db.client;
import java.util.List;
import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.Entity;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.GenericType;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import fr.excilys.db.dto.ComputerDto;
import fr.excilys.db.mapper.ComputerMapper;
import fr.excilys.db.model.Company;
import fr.excilys.db.model.Computer;
public class RestClient {
	private static final String REST_URI = "http://localhost:8080/cdb-webappli/api/computers";
	private static final String REST_UR = "http://localhost:8080/cdb-webappli/api/companies";

	private Client client = ClientBuilder.newClient();

	public Computer getJsonComputer(int id) {
		return client.target(REST_URI)
				.path(String.valueOf(id))
				.request(MediaType.APPLICATION_JSON)
				.get(Computer.class);
	}
	
	public void deleteComputer(int id) {
		client.target(REST_URI)
			  .path(String.valueOf(id))
			  .request(MediaType.APPLICATION_JSON)
			  .delete(Computer.class);
	}
	
	public List<ComputerDto> getAllComputers(){
		return client.target(REST_URI)
					 .request(MediaType
					 .APPLICATION_JSON)
					 .get().readEntity(new GenericType<List<ComputerDto>>() {});
	}
	
	public List<ComputerDto> getAllComputersByPage(int numPage,int sizePage){
		return client.target(REST_URI+"/"+numPage+"/"+sizePage)
				.request(MediaType.APPLICATION_JSON)
				.get()
				.readEntity(new GenericType<List<ComputerDto>>() {});
	}
	public List<Company> getAllCompanies(){
		return client.target(REST_UR)
				.request(MediaType.APPLICATION_JSON)
				.get()
				.readEntity(new GenericType<List<Company>>() {});
	}
	public  Response registerComputer(Computer computer) {
		ComputerDto computerDto=ComputerMapper.mapComputerToString(computer);
	    System.out.println("Registering user via ");
	    Client client = ClientBuilder.newClient();
	    WebTarget target = client.target(REST_URI);
	    Response response = target.request().post(Entity.entity(computerDto, MediaType.APPLICATION_JSON));

	    try {
	        if (response.getStatus() != 200) {
	            throw new RuntimeException("Failed with HTTP error code : " + response.getStatus());
	        }
	        System.out.println("Successfully got result: " + response.readEntity(String.class));
	    } finally {
	        response.close();
	        client.close();
	    }
	    return response;
	}
	public  Response updateComputer(Computer computer) {
		ComputerDto computerDto=ComputerMapper.mapComputerToString(computer);
	    System.out.println("Registering user via ");
	    Client client = ClientBuilder.newClient();
	    WebTarget target = client.target(REST_URI);
	    Response response = target.request().put(Entity.entity(computerDto, MediaType.APPLICATION_JSON));

	    try {
	        if (response.getStatus() != 200) {
	            throw new RuntimeException("Failed with HTTP error code : " + response.getStatus());
	        }
	        System.out.println("Successfully got result: " + response.readEntity(String.class));
	    } finally {
	        response.close();
	        client.close();
	    }
	    return response;
	}
	
}
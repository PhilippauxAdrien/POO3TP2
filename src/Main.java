import java.io.IOException;
import java.sql.SQLException;

import core.InsuranceRequest;
import view.GestionClient;

public class Main {

	@SuppressWarnings("unused")
	public static void main(String[] args) throws SQLException {
		InsuranceRequest ir = new InsuranceRequest();
		ir.connect();
		ir.ensClients("");
		try {
			GestionClient gc = new GestionClient(ir);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

}

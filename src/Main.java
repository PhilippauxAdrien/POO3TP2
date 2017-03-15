import java.sql.SQLException;

import core.InsuranceRequest;

public class Main {

	public static void main(String[] args) throws SQLException {
		InsuranceRequest ir = new InsuranceRequest();
		ir.connect();
		ir.ensClients("");
	}

}

import methods.ReadAndParse;
import model.Employee;
import org.json.simple.JSONArray;
import org.json.simple.parser.*;

import java.io.FileReader;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.List;

public class Main {
    public static void main(String[] args) throws SQLException, ClassNotFoundException, IOException, ParseException, IllegalAccessException {
        String url = "jdbc:postgresql://localhost:5432/EmployeesManager";
        String userName = "postgres";
        String password = "Kyouma#001";
        Class.forName("org.postgresql.Driver");
        Connection con = DriverManager.getConnection(url, userName, password);

        ReadAndParse readAndParse = new ReadAndParse();
        JSONArray jsonArray = (JSONArray) new JSONParser().parse(new FileReader("src/main/resources/data.json"));
        List<Employee> employeeList = readAndParse.parseJSON(jsonArray);

        readAndParse.insertIntoDB(con, employeeList);
    }
}

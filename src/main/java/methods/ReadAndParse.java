package methods;

import Annotations.NameToName;
import model.Employee;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import java.lang.reflect.Field;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class ReadAndParse {
    public List<Employee> parseJSON(JSONArray jsonArray) throws IllegalAccessException {
        List<Employee> employeeList = new ArrayList<Employee>();
        for(Object object: jsonArray){
            Employee employee = new Employee();
            JSONObject jsonObject = (JSONObject) object;
            Class<?> clapp = Employee.class;
            for(Field field: clapp.getDeclaredFields()){
                field.setAccessible(true);
                Class<?> type = field.getType();
                String fieldName;
                if(field.isAnnotationPresent(NameToName.class)){
                    fieldName = field.getAnnotation(NameToName.class).value();
                }else{
                    fieldName = field.getName();
                }
                if(type.equals(int.class)){
                    field.set(employee, (int)(long)jsonObject.get(fieldName));
                } else if (type.equals(Double.class)) {
                    field.set(employee, (double)jsonObject.get(fieldName));
                }else{
                    field.set(employee, jsonObject.get(fieldName));
                }
            }
            System.out.println(employee.toString());
            employeeList.add(employee);
        }
        return employeeList;
    }

    public void insertIntoDB(Connection connection, List<Employee> employeeList) throws SQLException, IllegalAccessException {
        for(Employee emp : employeeList){
            //String query = "INSERT INTO employees (first_name, last_name, email, gender, password) VALUES (?,?,?,?,?)";
            Class<?> clap = Employee.class;
            StringBuilder query = new StringBuilder();
            query.append("INSERT INTO jsonEmployee (");
            for(Field field: clap.getDeclaredFields()){
                field.setAccessible(true);
                if(field.getName().equals("id")){
                    continue;
                }
                if(field.isAnnotationPresent(NameToName.class)){
                    query.append(field.getAnnotation(NameToName.class).value()).append(",");
                }else{
                    query.append(field.getName()).append(",");
                }
            }
            query.deleteCharAt(query.length()-1);
            query.append(") VALUES (");
            for(Field field: clap.getDeclaredFields()){
                field.setAccessible(true);
                if(field.getName().equals("id")){
                    continue;
                }
                query.append("'").append(field.get(emp)).append("'").append(",");
            }
            query.deleteCharAt(query.length()-1);
            query.append(");");
            System.out.println(query);

            PreparedStatement preparedStatement = connection.prepareStatement(query.toString());
            preparedStatement.executeUpdate();
        }
    }
}

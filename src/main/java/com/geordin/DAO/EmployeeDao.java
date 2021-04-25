package com.geordin.DAO;


public interface EmployeeDao {
// I dont need this yet?
//    public Employee createEmployee(Employee employee) throws SQLException, BusinessException;

    //see applications  -   select * from account where status = 'pending'; - accountDAO
    //viewAccountsByUsername - customerDao?
    //viewLogByDate
    //ViewLogByUser
    //ViewLogByAccount
    //ApproveAccountByAccount




//    @Override
//    public Employee createEmployee(Employee employee) throws SQLException, BusinessException {
//
//        Connection connection= PostgresConnection.getConnection();
//        String sql="INSERT INTO employee_schema.employee\n" +
//                "(\"name\", age, contact, city, gender, deptid)\n" +
//                "VALUES(?, ?, ?, ?, ?, ?);\n";
//        PreparedStatement preparedStatement=connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
//        preparedStatement.setString(1,employee.getName());
//        preparedStatement.setInt(2,employee.getAge());
//        preparedStatement.setLong(3,employee.getContact());
//        preparedStatement.setString(4,employee.getCity());
//        preparedStatement.setString(5,employee.getGender());
//        preparedStatement.setInt(6,employee.getDepartment().getDeptid());
//
//        int c=preparedStatement.executeUpdate();
//        if(c==1){
//            ResultSet resultSet=preparedStatement.getGeneratedKeys();
//            if(resultSet.next()){
//                employee.setId(resultSet.getInt(1));
//            }
//        }else {
//            throw new BusinessException("Failure in registration... Please retry.....");
//        }
//        return employee;
//    }

}

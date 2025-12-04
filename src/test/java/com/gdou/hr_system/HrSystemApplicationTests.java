
package com.gdou.hr_system;
import com.gdou.hr_system.entity.*;
import com.gdou.hr_system.mapper.*;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
// 移除未使用的导入语句 org.springframework.context.annotation.Bean

import java.time.LocalDate;
import java.util.List;

@SpringBootTest
class HrSystemApplicationTests {

    // 下面这个注解的作用是自动注入，会将Spring容器中匹配的Bean注入到当前类中。

    @Autowired
    private EmployeeMapper employeeMapper;
/*  你说的完全正确：EmployeeMapper 只是一个接口（Interface），接口是不能被 new 的，理论上Spring工厂是无法直接创建它的实例的。
    但是，@Autowired 却成功了。为什么呢？
    因为 MyBatis 和 Spring 整合后，在启动时为你悄悄地创建了一个「代理对象」，并把这个代理对象作为一个特殊的 Bean 注册到了 Spring 的工厂里。
    @Mapper 注解的作用：就是一个标记。它告诉MyBatis和Spring：“这个接口很特殊，请你们在运行时为我动态生成一个实现类（代理对象），并把它注册为Spring的Bean”。
    为什么能 @Autowired：因为Spring容器里已经存在了这个接口的代理对象（Bean），所以可以正常进行依赖注入。
    核心魔法：是 MyBatis 利用「动态代理」技术在背后完成了所有这些复杂的工作，让你可以像使用普通Bean一样，直接注入和调用接口方法，从而实现了接口和SQL实现的分离，代码变得非常简洁。*/
    @Autowired
    private LoginDataMapper loginDataMapper;
    @Autowired
    private SalaryMapper salaryMapper;
    @Autowired
    private LeaveMapper leaveMapper;
    @Autowired
    private DocumentMapper documentMapper;

    //测试全部查询
    @Test
    void testEmployeeMapper() {
        // 测试查询所有员工
        List<Employee> employees = employeeMapper.selectAllEmployees();
        System.out.println("----- 数据库查询测试 -----");
        System.out.println("查询到的员工数量: " + employees.size());
        for (Employee emp : employees) {
            System.out.println("员工姓名: " + emp.getName());
        }
        for (Employee emp : employees) {
            System.out.println(emp);
        }
        System.out.println("----- 测试完成 -----");
    }

    //测试条件查询
    @Test
    void test2EmployeeMapper() {
        // 测试查询所有员工
        System.out.println("----- 数据库查询测试 -----");
        int id = 1;
        Employee employees = employeeMapper.selectEmployeeById(id);
        System.out.println(employees);
        System.out.println("----- 测试完成 -----");
    }

    //测试添加
    @Test
    void test3EmployeeMapper() {
        // 测试添加员工
        System.out.println("----- 数据库添加查询测试 -----");
        Employee employee = new Employee();
//        INSERT INTO `employees` (code, name, gender, pwd, birthday, phone, address, remark, dept_id, status)
//        VALUES ('EMP003', '王五', '男', 'password789', '1995-02-10', '13800138003', '广东省深圳市', '在职员工', 1, 1);
        employee.setCode("EMP003");
        employee.setName("王五");
        employee.setGender("男");
        employee.setPwd("password789");
        employee.setBirthday(LocalDate.of(1995, 2, 10));
        employee.setPhone("13800138003");
        employee.setAddress("广东省深圳市");
        employee.setRemark("在职员工");
        employee.setDeptId(1);
        employee.setStatus(1);

        employeeMapper.addEmployee(employee);
        System.out.println(employee);
        System.out.println("----- 测试完成 -----");
    }

    //测试更新
    @Test
    void test4EmployeeMapper() {
        System.out.println("----- 数据库更新查询测试 -----");
        Employee employee = new Employee();
        int id = 3;
        employee.setGender("女");
        employeeMapper.updateEmployee(id, employee);
        System.out.println("----- 测试完成 -----");
    }

    //测试删除
    @Test
    void test5EmployeeMapper() {
        System.out.println("----- 数据库删除查询测试 -----");
        int id = 3;
        employeeMapper.deleteEmployee(id);
        System.out.println("----- 测试完成 -----");
    }
    //指定任务查询
    @Test
    void test6EmployeeMapper() {
        System.out.println("----- 数据库查询测试 -----");
        Employee employee = new Employee();
        employee.setName("王五");
        List<Employee> wandanle = employeeMapper.searchEmployee(employee);
        System.out.println(wandanle);
        System.out.println("----- 测试完成 -----");

    }
    //登录信息查询
    @Test
    void test7EmployeeMapper() {
        System.out.println("----- 数据库登录信息测试 -----");
        String userName = "admin";
        String userName2 = "bdmin";
        String passWord = "123456";
        LoginData loginData1 = loginDataMapper.selectLoginData(userName,passWord);
        LoginData loginData2 = loginDataMapper.selectLoginData(userName2,passWord);
        System.out.println(loginData1);
        System.out.println(loginData1.getuserName());
        System.out.println(loginData2);
        System.out.println(loginData2.getuserName());

        System.out.println("----- 测试完成 -----");
    }
    //工资查询
    @Test
    void test8EmployeeMapper() {
        // 测试查询所有员工
        List<Salary> salaries = salaryMapper.selectAll();
        System.out.println("----- 数据库工资查询测试 -----");
        for (Salary sal : salaries) {
            System.out.println(sal);
        }
        System.out.println("----- 测试完成 -----");

    }

    @Test
    void test9EmployeeMapper() {
        // 测试查询所有员工请假信息
        List<Leave> leaves = leaveMapper.selectAll();
        System.out.println("----- 数据库请假信息查询测试 -----");
        for (Leave leave : leaves) {
            System.out.println(leave);
        }
        System.out.println("----- 测试完成 -----");
    }

    @Test
    void test10EmployeeMapper() {
        // 测试查询所有员工请假信息
        System.out.println("----- 搜索员工请假信息测试 -----");
        String employeeName = "张三";
        String staffId = "1";
        List<Leave> leaves = leaveMapper.searchLeave(employeeName,staffId);
        for (Leave leave : leaves) {
            System.out.println(leave);
        }
        System.out.println("----- 测试完成 -----");
    }

    @Test
    void test11EmployeeMapper() {
        System.out.println("----- 搜索文件信息测试 -----");
        List<Document> documents = documentMapper.selectAll();
        for (Document document : documents) {
            System.out.println(document);
        }
        System.out.println("----- 测试完成 -----");
    }
    //指定搜索文件信息
    @Test
    void test12EmployeeMapper() {
        System.out.println("----- 搜索文件信息测试 -----");
        Document document = new Document();
        document.setName("textdata1");
        document.setType("1");
        List<Document> documents = documentMapper.searchDocument(document);
        for (Document document1 : documents) {
            System.out.println(document1);
        }
        System.out.println("----- 测试完成 -----");
    }

}



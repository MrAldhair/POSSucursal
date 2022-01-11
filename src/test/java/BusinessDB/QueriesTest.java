/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package BusinessDB;

import Models.Employee;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import javafx.collections.ObservableList;
import org.junit.*;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import static org.junit.Assert.*;

public class QueriesTest {

    public QueriesTest() {
    }
    
    @BeforeClass
    public static void setUpClass() {
    }
    
    @AfterClass
    public static void tearDownClass() {
    }
    
    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);
    }
    
    @After
    public void tearDown() {
    }

    /**
     * Test of getUserName method, of class Queries.
     */
    @Test
    public void testGetUserName() {
        System.out.println("getUserName");
        Queries instance = new Queries();
        List<String> expResult = Arrays.asList(new String[]{"admin","eduardo","aldhair"});
        List<String> result = instance.getUserName();
        assertEquals(expResult, result);
    }

    @Test
    public void testGetUserNameExistingUser() throws SQLException {
        System.out.println("Existing user");
        Queries instance = new Queries();
        boolean result = instance.userExist("admin");
        boolean expResult = true;
        System.out.println(result);
        assertEquals(expResult, result);
    }

    @Test
    public void testGetUserNameNoExistingUser() throws SQLException {
        System.out.println("No Existing user");
        Queries instance = new Queries();
        boolean result = instance.userExist("pedro");
        boolean expResult = false;
        assertEquals(expResult, result);
    }
    /**
     * Test of getAllUsers method, of class Queries.
     */
    @Test
    public void testGetAllUsers() {
        System.out.println("getAllUsers");
        Queries instance = new Queries();
        Employee emp1 = new Employee(1,"admin","admin","Administrador","Sucursal 1");
        Employee emp2 = new Employee(2,"eduardo","eduardo","Empleado","Sucursal 2");
        Employee emp3 = new Employee(3,"aldhair","12345","Empleado","Sucursal 1");
        ArrayList<Employee> expResult = new ArrayList();
        expResult.add(emp1);
        expResult.add(emp2);
        expResult.add(emp3);
        ObservableList<Employee> result = instance.getAllUsers();
        assertEquals(expResult, result);
    }

    @Test
    public void testGetAllUsersFindAnyUser() {
        System.out.println("testGetAllUsersFindAnyUser");
        Queries instance = new Queries();
        ObservableList<Employee> result = instance.getAllUsers();
        assertNotNull(result);
    }
    /**
     * Test of getUserId method, of class Queries.
     */
    @Test
    public void testGetUserId() throws Exception {
        System.out.println("getUserId");
        Queries instance = new Queries();
        Integer expResult = 1;
        Integer result = instance.getUserId("admin");
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
       // fail("The test case is a prototype.");
    }

    /**
     * Test of getUserIdLogin method, of class Queries.
     */
    @Test
    public void testGetUserIdLogin() throws Exception {
        System.out.println("getUserIdLogin");
        String user = "admin";
        Queries instance = new Queries();
        Integer expResult = 1;
        Integer result = instance.getUserIdLogin(user);
        assertEquals(expResult, result);
    }

    /**
     * Test of insertUser method, of class Queries.
     */
    @Test
    public void testInsertUser() throws Exception {
        System.out.println("insertUser");
        String usuario = "";
        String pass = "";
        String typeEmployee = "";
        String branch = "";
        Queries instance = new Queries();
        instance.insertUser(usuario, pass, typeEmployee, branch);
        assertNotNull(instance);
    }

    /**
     * Test of validateUser method, of class Queries.
     */
    @Test
    public void testValidateUser() throws Exception {
        System.out.println("validateUser");
        String usuario = "";
        String pass = "";
        String typeEmployee = "";
        String branch = "";
        Queries instance = new Queries();
        boolean expResult = false;
        boolean result = instance.validateUser(usuario, pass, typeEmployee, branch);
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
       // fail("The test case is a prototype.");
    }

    /**
     * Test of deleteUser method, of class Queries.
     */
    @Test
    public void testDeleteUser() throws Exception {
        System.out.println("deleteUser");
        String id = "101"; //depende del id que se agregue en test validate user
        Queries instance = new Queries();
        instance.deleteUser(id);
        // TODO review the generated test code and remove the default call to fail.
        assertNotNull(instance);
    }

    /**
     * Test of userExist method, of class Queries.
     */
    @Test
    public void testUserExist() throws Exception {
        System.out.println("userExist");
        String userName = "eduardo";
        Queries instance = new Queries();
        boolean result = instance.userExist(userName);
        boolean expResult = true;
        assertNotNull(result);
     }

}
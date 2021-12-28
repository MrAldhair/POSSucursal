
package Models;

import org.junit.Test;
import static org.junit.Assert.*;

public class EmployeeTest {


    /**
     * Test of getId method, of class Employee.
     */
    @Test
    public void testGetId() {
        System.out.println("getId");
        Employee instance = new Employee();
        Integer expResult = null;
        Integer result = instance.getId();
        assertEquals(expResult, result);

    }

    /**
     * Test of getUser method, of class Employee.
     */
    @Test
    public void testGetUser() {
        System.out.println("getUser");
        Employee instance = new Employee();
        instance.setUser("ProGrammers");
        String expResult = "ProGrammers";
        assertEquals(expResult, instance.getUser());
    }

    /**
     * Test of getPassword method, of class Employee.
     */
    @Test
    public void testGetPassword() {
        System.out.println("getPassword");
        Employee instance = new Employee();
        instance.setPassword("password");
        String expResult = "password";
        assertEquals(expResult, instance.getPassword());
    }

    /**
     * Test of getTypeEmployee method, of class Employee.
     */
    @Test
    public void testGetTypeEmployee() {
        System.out.println("getTypeEmployee");
        Employee instance = new Employee();
        instance.setTypeEmployee("Administrador");
        String expResult = "Administrador";
        assertEquals(expResult, instance.getTypeEmployee());
    }

    /**
     * Test of getBranchName method, of class Employee.
     */
    @Test
    public void testGetBranchName() {
        System.out.println("getBranchName");
        Employee instance = new Employee();
        instance.setBranchName("Sucursal 2");
        String expResult = "Sucursal 2";
        assertEquals(expResult, instance.getBranchName());
    }

    /**
     * Test of setId method, of class Employee.
     */
    @Test
    public void testSetId() {
        System.out.println("setId");
        Integer id = null;
        Employee instance = new Employee();
        instance.setId(id);
    }

    /**
     * Test of setUser method, of class Employee.
     */
    @Test
    public void testSetUser() {
        System.out.println("setUser");
        String user = "";
        Employee instance = new Employee();
        instance.setUser(user);
    }

    /**
     * Test of setPassword method, of class Employee.
     */
    @Test
    public void testSetPassword() {
        System.out.println("setPassword");
        String password = "";
        Employee instance = new Employee();
        instance.setPassword(password);
    }

    /**
     * Test of setTypeEmployee method, of class Employee.
     */
    @Test
    public void testSetTypeEmployee() {
        System.out.println("setTypeEmployee");
        String typeEmployee = "";
        Employee instance = new Employee();
        instance.setTypeEmployee(typeEmployee);
    }

    /**
     * Test of setBranchName method, of class Employee.
     */
    @Test
    public void testSetBranchName() {
        System.out.println("setBranchName");
        String branchName = "";
        Employee instance = new Employee();
        instance.setBranchName(branchName);
    }
   
}

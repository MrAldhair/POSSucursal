
package Models;

import org.junit.Test;
import static org.junit.Assert.*;

public class BranchOfficeTest {
    
    /**
     * Test of getGetId_branch_office method, of class BranchOffice.
     */
    @Test
    public void testGetId_branch_office() {
        System.out.println("getId_branch_office");
        BranchOffice instance = new BranchOffice();
        long expResult = 0L;
        long result = instance.getId_branch_office();
        assertEquals(expResult, result);
    }

    /**
     * Test of getName method, of class BranchOffice.
     */
    @Test
    public void testGetName() {
        System.out.println("getName");
        BranchOffice instance = new BranchOffice();
        instance.setName("Sucursal 1");
        String expResult = "Sucursal 1";
        assertEquals(expResult, instance.getName());
    }

    /**
     * Test of getState method, of class BranchOffice.
     */
    @Test
    public void testGetState() {
        System.out.println("getState");
        BranchOffice instance = new BranchOffice();
        instance.setState("coahuila");
        String expResult = "coahuila";
        assertEquals(expResult, instance.getState());
    }

    /**
     * Test of getCity method, of class BranchOffice.
     */
    @Test
    public void testGetCity() {
        System.out.println("getCity");
        BranchOffice instance = new BranchOffice();
        instance.setCity("San Pedro");
        String expResult = "San Pedro";
        assertEquals(expResult, instance.getCity());
    }

    /**
     * Test of getStreet method, of class BranchOffice.
     */
    @Test
    public void testGetStreet() {
        System.out.println("getStreet");
        BranchOffice instance = new BranchOffice();
        instance.setStreet("Calle San Pedro");
        String expResult = "Calle San Pedro";
        assertEquals(expResult, instance.getStreet());
    }

    /**
     * Test of getNumber method, of class BranchOffice.
     */
    @Test
    public void testGetNumber() {
        System.out.println("getNumber");
        BranchOffice instance = new BranchOffice();
        instance.setNumber("Num. Ext. 2323");
        String expResult = "Num. Ext. 2323";
        assertEquals(expResult, instance.getNumber());
    }

    /**
     * Test of getZip_code method, of class BranchOffice.
     */
    @Test
    public void testGetZip_code() {
        System.out.println("getZip_code");
        BranchOffice instance = new BranchOffice();
        Integer expResult = null;
        Integer result = instance.getZip_code();
        assertEquals(expResult, result);
    }

    /**
     * Test of setId_branch_office method, of class BranchOffice.
     */
    @Test
    public void testSetId_branch_office() {
        System.out.println("setId_branch_office");
        long id_branch_office = 0L;
        BranchOffice instance = new BranchOffice();
        instance.setId_branch_office(id_branch_office);
    }

    /**
     * Test of setName method, of class BranchOffice.
     */
    @Test
    public void testSetName() {
        System.out.println("setName");
        String name = "";
        BranchOffice instance = new BranchOffice();
        instance.setName(name);
    }

    /**
     * Test of setState method, of class BranchOffice.
     */
    @Test
    public void testSetState() {
        System.out.println("setState");
        String state = "";
        BranchOffice instance = new BranchOffice();
        instance.setState(state);
    }

    /**
     * Test of setCity method, of class BranchOffice.
     */
    @Test
    public void testSetCity() {
        System.out.println("setCity");
        String city = "";
        BranchOffice instance = new BranchOffice();
        instance.setCity(city);
    }

    /**
     * Test of setStreet method, of class BranchOffice.
     */
    @Test
    public void testSetStreet() {
        System.out.println("setStreet");
        String street = "";
        BranchOffice instance = new BranchOffice();
        instance.setStreet(street);
    }

    /**
     * Test of setNumber method, of class BranchOffice.
     */
    @Test
    public void testSetNumber() {
        System.out.println("setNumber");
        String number = "";
        BranchOffice instance = new BranchOffice();
        instance.setNumber(number);
    }

    /**
     * Test of setZip_code method, of class BranchOffice.
     */
    @Test
    public void testSetZip_code() {
        System.out.println("setZip_code");
        Integer zip_code = null;
        BranchOffice instance = new BranchOffice();
        instance.setZip_code(zip_code);
    }
    
}


package Models;

import org.junit.Test;
import static org.junit.Assert.*;

public class SaleTest {
    
    /**
     * Test of getId_sale method, of class Sale.
     */
    @Test
    public void testGetId_sale() {
        System.out.println("getId_sale");
        Sale instance = new Sale();
        long expResult = 0L;
        assertEquals(expResult, instance.getId_sale());
    }

    /**
     * Test of getId_employee method, of class Sale.
     */
    @Test
    public void testGetId_employee() {
        System.out.println("getId_employee");
        Sale instance = new Sale();
        Integer expResult = null;
        assertEquals(expResult, instance.getId_employee());
    }

    /**
     * Test of getName_employee method, of class Sale.
     */
    @Test
    public void testGetName_employee() {
        System.out.println("getName_employee");
        Sale instance = new Sale();
        instance.setName_employee("");
        String expResult = "";
        assertEquals(expResult, instance.getName_employee());
    }

    /**
     * Test of getId_branch_office method, of class Sale.
     */
    @Test
    public void testGetId_branch_office() {
        System.out.println("getId_branch_office");
        Sale instance = new Sale();
        Long expResult = null;
        assertEquals(expResult, instance.getId_branch_office());
    }

    /**
     * Test of getName_branch_office method, of class Sale.
     */
    @Test
    public void testGetName_branch_office() {
        System.out.println("getName_branch_office");
        Sale instance = new Sale();
        instance.setName_branch_office("Sucursal 1");
        String expResult = "Sucursal 1";
        assertEquals(expResult, instance.getName_branch_office());
    }

    /**
     * Test of getTotal_sale method, of class Sale.
     */
    @Test
    public void testGetTotal_sale() {
        System.out.println("getTotal_sale");
        Sale instance = new Sale();
        Double expResult = null;
        assertEquals(expResult, instance.getTotal_sale());

    }

    /**
     * Test of getDescription method, of class Sale.
     */
    @Test
    public void testGetDescription() {
        System.out.println("getDescription");
        Sale instance = new Sale();
        instance.setDescription("Test sale");
        String expResult = "Test sale";
        assertEquals(expResult, instance.getDescription());

    }

    /**
     * Test of getDate_sale method, of class Sale.
     */
    @Test
    public void testGetDate_sale() {
        System.out.println("getDate_sale");
        Sale instance = new Sale();
        instance.setDate_sale("25/12/2021");
        String expResult = "25/12/2021";
        assertEquals(expResult, instance.getDate_sale());

    }

    /**
     * Test of setId_sale method, of class Sale.
     */
    @Test
    public void testSetId_sale() {
        System.out.println("setId_sale");
        long id_sale = 0L;
        Sale instance = new Sale();
        instance.setId_sale(id_sale);
    }

    /**
     * Test of setId_employee method, of class Sale.
     */
    @Test
    public void testSetId_employee() {
        System.out.println("setId_employee");
        Integer id_employee = null;
        Sale instance = new Sale();
        instance.setId_employee(id_employee);
    }

    /**
     * Test of setName_employee method, of class Sale.
     */
    @Test
    public void testSetName_employee() {
        System.out.println("setName_employee");
        String name_employee = "";
        Sale instance = new Sale();
        instance.setName_employee(name_employee);
    }

    /**
     * Test of setId_branch_office method, of class Sale.
     */
    @Test
    public void testSetId_branch_office() {
        System.out.println("setId_branch_office");
        Long id_branch_office = null;
        Sale instance = new Sale();
        instance.setId_branch_office(id_branch_office);
    }

    /**
     * Test of setName_branch_office method, of class Sale.
     */
    @Test
    public void testSetName_branch_office() {
        System.out.println("setName_branch_office");
        String name_branch_office = "";
        Sale instance = new Sale();
        instance.setName_branch_office(name_branch_office);
    }

    /**
     * Test of setTotal_sale method, of class Sale.
     */
    @Test
    public void testSetTotal_sale() {
        System.out.println("setTotal_sale");
        Double total_sale = null;
        Sale instance = new Sale();
        instance.setTotal_sale(total_sale);
    }

    /**
     * Test of setDescription method, of class Sale.
     */
    @Test
    public void testSetDescription() {
        System.out.println("setDescription");
        String description = "";
        Sale instance = new Sale();
        instance.setDescription(description);
    }

    /**
     * Test of setDate_sale method, of class Sale.
     */
    @Test
    public void testSetDate_sale() {
        System.out.println("setDate_sale");
        String date_sale = "";
        Sale instance = new Sale();
        instance.setDate_sale(date_sale);
    }

    
}

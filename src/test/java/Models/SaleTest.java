
package Models;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;

/*
  Test realizados con JUnit4
*/

public class SaleTest {
    
    /*
    private Long id_branch_office;
    private String name_branch_office;
    private Double total_sale;
    private String description;
    private String date_sale;
    
    */
    
    @Test // indicar que es un metodo que se va a ejecutar
    public void test_id_sale(){
    
        Sale sale = new Sale();
        sale.setId_sale(1L);
        Long esperado = 1L;
        Long real = sale.getId_sale();
        assertEquals(esperado, real);
        //assertTrue(esperado == 13);
        
    }
    
    @Test
    public void test_id_empleoyee(){
    
        Sale sale = new Sale();
        sale.setId_employee(13);
        Integer esperado = 13;
        Integer real = sale.getId_employee();
        assertEquals(esperado, real);
        //assertTrue(esperado == 13);
        
    }
    
    @Test 
    public void test_name_empleoyee(){
    
        Sale sale = new Sale();
        sale.setName_employee("Aldhair");
        String esperado = "Aldhair";
        String real = sale.getName_employee();
        assertEquals(esperado, real);
        //assertTrue(esperado == 13);
        
    }
    
    @Test // Pendiente de terminar
    public void test_id_branch_office(){
    
        Sale sale = new Sale();
        sale.setId_sale(1L);
        Long esperado = 1L;
        Long real = sale.getId_sale();
        assertEquals(esperado, real);
        //assertTrue(esperado == 13);
        
    }
    
    
    

}

package BusinessDB;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;

public class ConnDBH2Test {
    @InjectMocks private ConnDBH2 connDBH2 = new ConnDBH2();
    @Mock private Connection mockConnection;
    @Mock private Statement mockStatement;

    @Before
    public void before() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void TestConnectionDBH2Successfull() throws Exception{

      Mockito.when(mockConnection.createStatement()).thenReturn(mockStatement);
      Mockito.when(mockConnection.createStatement().executeUpdate(Mockito.any())).thenReturn(1);
        boolean value = executeQuery("");
        Assert.assertEquals(true,value);
        Mockito.verify(mockConnection.createStatement(),Mockito.times(1));
    }

    public boolean executeQuery(String query) throws ClassNotFoundException, SQLException {
        try{
            connDBH2.connectionDbH2();
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }
}

package Models;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class Employee {
    
    private Integer id;
    private String user;
    private String password;
    private String typeEmployee;
    private Integer idBranch;
    private String branchName;
    private String nameBr;
    private ObservableList<String> optionsBranchOffice = FXCollections.observableArrayList();

    public Employee() {
    }

    public Employee(Integer id, String user, String password, String typeEmployee, Integer idBranch, String BranchName) {
        this.id = id;
        this.user = user;
        this.password = password;
        this.typeEmployee = typeEmployee;
        this.idBranch = idBranch;
        this.branchName= BranchName;
    }
    public Employee(Integer id, String user, String password, String typeEmployee, Integer idBranch) {
        this.id = id;
        this.user = user;
        this.password = password;
        this.typeEmployee = typeEmployee;
        this.idBranch = idBranch;
    
    }



    public String getBranchName() {
        return branchName;
    }

    private void setBranchName(long idBranch){
        
        this.nameBr = optionsBranchOffice.get((int)idBranch);
    }
        public Employee(String user) {
        this.user = user;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getUser() {
        return user;
    }

    public void setUser(String user) {
        this.user = user;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getTypeEmployee() {
        return typeEmployee;
    }

    public void setTypeEmployee(String typeEmployee) {
        this.typeEmployee = typeEmployee;
    }

    public Integer getIdBranch() {
        return idBranch;
    }

    public void setIdBranch(Integer idBranch) {
        this.idBranch = idBranch;
    }

    
    
    

}

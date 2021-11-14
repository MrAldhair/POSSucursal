
package Models;

public class Employee {
    
    private Integer id;
    private String user;
    private String password;
    private String typeEmployee;
    private Integer idBranch;

    public Employee() {
    }

    public Employee(Integer id, String user, String password, String typeEmployee, Integer idBranch) {
        this.id = id;
        this.user = user;
        this.password = password;
        this.typeEmployee = typeEmployee;
        this.idBranch = idBranch;
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

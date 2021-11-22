package Models;

import lombok.Data;

@Data
public class BranchOffice {
    
    private long id_branch_office;
    private String name;
    private String state;
    private String city;
    private String street;
    private String number;
    private Integer zip_code;

    public BranchOffice() { }

    public BranchOffice(long id_branch_office, String name, String state, String city, String street, String number, Integer zip_code) {
        this.id_branch_office = id_branch_office;
        this.name = name;
        this.state = state;
        this.city = city;
        this.street = street;
        this.number = number;
        this.zip_code = zip_code;
    }
  
}

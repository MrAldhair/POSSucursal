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

}

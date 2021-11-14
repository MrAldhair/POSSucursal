
package Models;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class Sale {
    
    @JsonProperty("id_sale")
    private Long id_sale;
    @JsonProperty("id_employee")
    private Integer id_employee;
    @JsonProperty("id_branch_office")
    private Integer id_branch_office;
    @JsonProperty("total_sale")
    private Double total_sale;
    @JsonProperty("description")
    private String description;
    @JsonProperty("date_sale")
    private String date_sale;

    public Sale() {}

    public Sale(Long id_sale, Integer id_employee, Integer id_branch_office, Double total_sale, String description, String date_sale) {
        
            this.id_sale = id_sale;
            this.id_employee = id_employee;
            this.id_branch_office = id_branch_office;
            this.total_sale = total_sale;
            this.description = description;
            this.date_sale = date_sale;
            
    }  
    
}


package Models;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
@NoArgsConstructor
@Data
@AllArgsConstructor
public class Employee {
    private Integer id;
    private String user;
    private String password;
    private String typeEmployee;
    private String branchName;

    public Employee(String user) {
        this.user=user;
    }

}

package hoaftq.example.Redis1.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Employee {

    private String name;

    private String id;

    private int age;

    private Department department;
}

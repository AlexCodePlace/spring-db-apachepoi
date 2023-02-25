package test.apachepoi.springdbapachepoi.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.databind.JsonNode;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
public class ReportData {
    private Long id;
    private String firstName;
    private String lastName;
    private Double balance;
    private Integer age;
    private String town;

    public ReportData(JsonNode node) {
        this.id = node.get("id") != null ? node.get("id").asLong() : null;
        this.firstName = node.get("firstName") != null ? node.get("firstName").asText() : null;
        this.lastName = node.get("lastName") != null ? node.get("lastName").asText() : null;
        this.balance = node.get("balance") != null ? node.get("balance").asDouble() : null;
        this.age = node.get("age") != null ? node.get("age").asInt() : null;
        this.town = node.get("town") != null ? node.get("town").asText() : null;
    }
}


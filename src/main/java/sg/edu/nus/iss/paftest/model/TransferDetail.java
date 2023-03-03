package sg.edu.nus.iss.paftest.model;

import java.time.LocalDate;

import jakarta.json.Json;
import jakarta.json.JsonObject;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class TransferDetail {
    private String transactionId;
    private LocalDate date;
    private String fromAccountId;
    private String toAccountId;
    private Double amount;


    public JsonObject toJSON() {
        return Json.createObjectBuilder()
                .add("transactionId", this.getTransactionId())
                .add("date", this.getDate().toString())
                .add("from_account", this.getFromAccountId())
                .add("to_account", this.getToAccountId())
                .add("amount", this.getAmount())
                .build();
    }
}

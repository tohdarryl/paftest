package sg.edu.nus.iss.paftest.model;

import java.io.Serializable;
import java.math.BigDecimal;

import jakarta.validation.constraints.AssertFalse;
import jakarta.validation.constraints.AssertTrue;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


@AllArgsConstructor
@NoArgsConstructor
public class Transaction implements Serializable{

    @Size(min = 10, max = 10, message="Must be 10 chars")
    @NotNull(message="Account should exist")
    private String fromAccountId;

    @Size(min = 10, max = 10, message="Must be 10 chars")
    @NotNull(message="Account should exist")
    private String toAccountId;

    @AssertFalse(message="Transaction should consists of 2 different accounts")
    private Boolean sameAccount;

    @Positive(message="Amount should be positive")
    @Min(value=10, message="Minimum transfer amount is $10")
    private Double amount;

    @AssertTrue(message="Balance should be greater than transfer amount")
    private Boolean suffBalance;

    private String comments;

  
    public String getFromAccountId() {
        return fromAccountId;
    }
    public void setFromAccountId(String fromAccountId) {
        this.fromAccountId = fromAccountId;
    }
    public String getToAccountId() {
        return toAccountId;
    }
    public void setToAccountId(String toAccountId) {
        this.toAccountId = toAccountId;
    }
    public Boolean getSameAccount() {
        return sameAccount;
    }
    public void setSameAccount(Boolean sameAccount) {
        this.sameAccount = sameAccount;
    }
    public Double getAmount() {
        return amount;
    }
    public void setAmount(Double amount) {
        this.amount = amount;
    }
    public Boolean getSuffBalance() {
        return suffBalance;
    }
    public void setSuffBalance(Boolean suffBalance) {
        this.suffBalance = suffBalance;
    }
    public String getComments() {
        return comments;
    }
    public void setComments(String comments) {
        this.comments = comments;
    }
 
  
   

@Override
    public String toString() {
        return "Transaction [fromAccountId=" + fromAccountId + ", toAccountId=" + toAccountId + ", sameAccount="
                + sameAccount + ", amount=" + amount + ", suffBalance=" + suffBalance + ", comments=" + comments + "]";
    }

    public Boolean checkForAccounts(String fromAccount, String toAccount){
    return fromAccount.equalsIgnoreCase(toAccount)?true:false;
}

    public Boolean sufficientBalance(BigDecimal balance, Double amount){
    return balance.doubleValue() > amount?true:false;
}
}

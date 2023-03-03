package sg.edu.nus.iss.paftest.repository;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.LinkedList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BatchPreparedStatementSetter;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import sg.edu.nus.iss.paftest.model.Account;

@Repository
public class AccountsRepository {

    @Autowired
    JdbcTemplate jdbcTemplate;
    
    private static final String findAllSQL = "select * from accounts";
    private static final String findByIdSQL = "select * from accounts where account_id like '%' ? '%'";
    private static final String updateSQL = "update accounts set balance = ? where account_id = ?";

    public List<Account> findAll(){
        List<Account> aList = new LinkedList<>();
        aList = jdbcTemplate.query(findAllSQL, BeanPropertyRowMapper.newInstance(Account.class));
        return aList;
    }

    public Account findById(String id){
        Account a = new Account();
        a = jdbcTemplate.queryForObject(findByIdSQL, BeanPropertyRowMapper.newInstance(Account.class), id);
        return a;
    }


    public Boolean update(Account a){
        Integer result = jdbcTemplate.update(updateSQL, a.getBalance(), a.getAccountId());
        return result>0?true:false;
    }
    // public int[] Update(List<Account> accounts){
    //     return jdbcTemplate.batchUpdate(updateSQL, new BatchPreparedStatementSetter() {

    //         @Override
    //         public void setValues(PreparedStatement ps, int i) throws SQLException {
    //             ps.setString(1, accounts.get(i).getAccountId());
    //             ps.setString(2, accounts.get(i).getName());
    //             ps.setBigDecimal(3, accounts.get(i).getBalance());
    //         }

    //         @Override
    //         public int getBatchSize() {
    //             return accounts.size();
    //         }
            
    //     });

    // }


}

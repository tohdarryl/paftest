package sg.edu.nus.iss.paftest.service;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import sg.edu.nus.iss.paftest.model.TransferDetail;

@Service
public class LogAuditService {

    @Autowired
    RedisTemplate<String, Object> redisTemplate;
    
    public void save(final TransferDetail detail){
        System.out.println("detail " + detail.toJSON().toString());
        redisTemplate.opsForValue().set(detail.getTransactionId(), detail.toJSON().toString());
    }

    // public TransferDetail findById(final String tId) throws IOException{
    //     String tStr = (String)redisTemplate.opsForValue().get(tId);
    //     TransferDetail detail = TransferDetail.create(tStr);
    //     return detail;
    // }
}

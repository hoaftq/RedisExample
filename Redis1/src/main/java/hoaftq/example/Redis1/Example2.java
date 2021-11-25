package hoaftq.example.Redis1;

import hoaftq.example.Redis1.dto.Department;
import hoaftq.example.Redis1.dto.Employee;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.HashOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.hash.HashMapper;
import org.springframework.data.redis.hash.Jackson2HashMapper;
import org.springframework.data.redis.hash.ObjectHashMapper;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.Map;

@Component
public class Example2 {

    @Autowired
    private RedisTemplate<String, String> redisTemplate;

    @Resource(name = "redisTemplate")
    private HashOperations<String, byte[], byte[]> hashOperations;

    private final HashMapper<Object, byte[], byte[]> hashMapper = new ObjectHashMapper();

    @Resource(name = "redisTemplate")
    private HashOperations<String, String, Object> hashOperationsForJacksonMapper;

    private final Jackson2HashMapper jackson2HashMapper = new Jackson2HashMapper(true);

    public void write() {
        Employee e = getEmployee();
        Map<byte[], byte[]> dHash = hashMapper.toHash(e);
        hashOperations.putAll("hash1", dHash);
    }

    public Employee read() {
        Map<byte[], byte[]> dHash = hashOperations.entries("hash1");
        return (Employee) hashMapper.fromHash(dHash);
    }

    public void writeWithJacksonMapper() {
        Employee e = getEmployee();
        Map<String, Object> dHash = jackson2HashMapper.toHash(e);
        hashOperationsForJacksonMapper.putAll("hash2", dHash);
    }

    public Employee readWithJacksonMapper() {
        Map<String, Object> eHash = hashOperationsForJacksonMapper.entries("hash2");
        return (Employee) jackson2HashMapper.fromHash(eHash);
    }

    private Employee getEmployee() {
        return Employee.builder()
                .id("123-456")
                .name("Hulk")
                .age(100)
                .department(Department.builder()
                        .id("123")
                        .name("HR")
                        .build())
                .build();
    }
}

package hoaftq.example.Redis1;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.ListOperations;
import org.springframework.data.redis.core.RedisCallback;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.nio.charset.StandardCharsets;

@Component
public class Example1 {

    @Autowired
    private StringRedisTemplate redisTemplate;

    @Resource(name = "redisTemplate")
    private ListOperations<String, String> listOperations;

    public void clean() {
        redisTemplate.delete(redisTemplate.keys("*"));
    }

    public void write() {
//        listOperations.leftPush("list1", "world");
        redisTemplate.opsForList().leftPush("list1", "world");
    }

    public void writeDirectly() {
        redisTemplate.execute((RedisCallback<Object>) connection -> {
            connection.lPush("list2".getBytes(StandardCharsets.UTF_8), "!!!".getBytes(StandardCharsets.UTF_8));
            return null;
        });
    }

    public String read() {
//        return listOperations.index("list1", 0);
        return redisTemplate.opsForList().index("list1", 0);
    }

    public String readAll() {
//        return String.join(",", listOperations.range("list1", 0, -1));
        return String.join(",", redisTemplate.opsForList().range("list1", 0, -1));
    }

    public String readDirectly() {
        return redisTemplate.execute((RedisCallback<String>) connection
                -> new String(connection.lIndex("list2".getBytes(StandardCharsets.UTF_8), 0)));
    }
}

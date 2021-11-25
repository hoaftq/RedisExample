package hoaftq.example.Redis1;

import hoaftq.example.Redis1.dto.Employee;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.event.ApplicationStartedEvent;
import org.springframework.context.event.EventListener;

@SpringBootApplication
public class Redis1Application {

    @Autowired
    private Example1 example1;

    @Autowired
    private Example2 example2;

    public static void main(String[] args) {
        SpringApplication.run(Redis1Application.class, args);
    }

    @EventListener(ApplicationStartedEvent.class)
    public void ex1() {
        example1.clean();
        example1.write();
        example1.writeDirectly();
        System.out.println(example1.readAll());
        System.out.println(example1.readDirectly());
    }

    @EventListener(ApplicationStartedEvent.class)
    public void ex2() {
        example2.write();
        Employee e = example2.read();
        System.out.println(e.getName());

        example2.writeWithJacksonMapper();
        Employee e2 = example2.readWithJacksonMapper();
        System.out.println(e2.getName());
    }
}

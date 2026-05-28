package weblab.jmx;

import jakarta.annotation.PostConstruct;
import jakarta.enterprise.context.ApplicationScoped;

import javax.management.MBeanServer;
import javax.management.ObjectName;
import java.lang.management.ManagementFactory;

@ApplicationScoped
public class JMXConfig {
    private PointCounterMBean pointCounter;
    private MissPercentageMBean missPercentage;

    @PostConstruct
    public void init() {
        try {
            MBeanServer mbs = ManagementFactory.getPlatformMBeanServer();

            pointCounter = new PointCounter();
            mbs.registerMBean(pointCounter, new ObjectName("weblab:type=PointCounter"));

            missPercentage = new MissPercentage();
            mbs.registerMBean(missPercentage, new ObjectName("weblab:type=MissPercentage"));

            System.out.println("JMX MBeans registered successfully");
        } catch (Exception e) {
            System.err.println("Failed to register JMX MBeans");
        }
    }

    public PointCounterMBean getPointCounter() {
        return pointCounter;
    }

    public MissPercentageMBean getMissPercentage() {
        return missPercentage;
    }
}

package weblab.jmx;

import javax.management.*;
import java.util.concurrent.atomic.AtomicInteger;

public class PointCounter implements PointCounterMBean, NotificationBroadcaster {
    private final AtomicInteger totalPoints = new AtomicInteger(0);
    private final AtomicInteger missPoints = new AtomicInteger(0);
    private long notificationSequence = 1;
    private final NotificationBroadcasterSupport notificationSupport = new NotificationBroadcasterSupport();

    @Override
    public int getTotalPoints() {
        return totalPoints.get();
    }

    @Override
    public int getMissPoints() {
        return missPoints.get();
    }

    @Override
    public void resetStats() {
        totalPoints.set(0);
        missPoints.set(0);
    }

    @Override
    public void addPointEvent(boolean hit) {
        int newTotal = totalPoints.incrementAndGet();
        if (!hit) missPoints.incrementAndGet();

        if (newTotal % 15 == 0) sendNotification(newTotal, hit);
    }

    private void sendNotification(int total, boolean lastHit) {
        Notification notification = new Notification(
                "point.count.multiple.15",
                this,
                notificationSequence++,
                System.currentTimeMillis(),
                String.format("Total points count = %d (last hit = %s)", total, lastHit)
        );
        notificationSupport.sendNotification(notification);
    }

    @Override
    public void addNotificationListener(NotificationListener listener, NotificationFilter filter, Object handback) throws IllegalArgumentException {
        notificationSupport.addNotificationListener(listener, filter, handback);
    }

    @Override
    public void removeNotificationListener(NotificationListener listener) throws ListenerNotFoundException {
        notificationSupport.removeNotificationListener(listener);
    }

    @Override
    public MBeanNotificationInfo[] getNotificationInfo() {
        return new MBeanNotificationInfo[]{
                new MBeanNotificationInfo(
                        new String[]{"point.count.multiple.15"},
                        Notification.class.getName(),
                        "Sent when total point count becomes a multiple of 15"
                )
        };
    }
}

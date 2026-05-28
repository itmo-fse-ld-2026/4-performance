package weblab.jmx;

public interface PointCounterMBean {
    int getTotalPoints();

    int getMissPoints();

    void resetStats();

    void addPointEvent(boolean hit);
}

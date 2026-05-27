package weblab.jmx;

public interface MissPercentageMBean {
    double getMissPercentage();

    String getMissPercentageFormatted();

    void resetStats();

    void addClickEvent(boolean hit);
}

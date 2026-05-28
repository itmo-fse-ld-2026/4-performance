package weblab.jmx;

import java.text.DecimalFormat;
import java.util.concurrent.atomic.AtomicInteger;

public class MissPercentage implements MissPercentageMBean {
    private final AtomicInteger totalClicks = new AtomicInteger(0);
    private final AtomicInteger missClicks = new AtomicInteger(0);
    private final DecimalFormat df = new DecimalFormat("0.000");

    @Override
    public double getMissPercentage() {
        int total = totalClicks.get();
        if (total == 0) return 0.0;

        return (missClicks.get() * 100.0) / total;
    }

    @Override
    public String getMissPercentageFormatted() {
        return df.format(getMissPercentage()) + "%";
    }

    @Override
    public void resetStats() {
        totalClicks.set(0);
        missClicks.set(0);
    }

    @Override
    public void addClickEvent(boolean hit) {
        totalClicks.incrementAndGet();
        if (!hit) missClicks.incrementAndGet();
    }
}

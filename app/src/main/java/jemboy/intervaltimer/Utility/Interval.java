package jemboy.intervaltimer.Utility;

public class Interval {
    private String description;
    private int duration;

    public Interval(String description, int duration) {
        this.description = description;
        this.duration = duration;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getDuration() {
        return duration;
    }

    public void setDuration(int duration) {
        this.duration = duration;
    }

    public String toString() {
        String duration = "Duration: " + Integer.toString(this.duration) + " seconds";
        return duration + "\n" + this.description;
    }
}
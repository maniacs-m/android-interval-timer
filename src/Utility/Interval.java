package jemboy.alarmz.Utility;

public class Interval {
    private String description;
    private int duration, position;

    public Interval(String description, int duration, int position) {
        this.description = description;
        this.duration = duration;
        this.position = position;
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

    public int getPosition() {
        return position;
    }

    public void setPosition(int position) {
        this.position = position;
    }

    public String toString() {
        String position = "#" + Integer.toString(this.position);
        String duration = "Duration: " + Integer.toString(this.duration) + " seconds";
        return position + " - " + duration + "\n" + this.description;
    }
}

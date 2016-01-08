package jemboy.intervaltimer.Builder;

public interface Creator {
    void onAddCompleted(String description, int duration);
    void onEditCompleted(String description, int duration, int position);
    void onDeleteCompleted(int position);
}

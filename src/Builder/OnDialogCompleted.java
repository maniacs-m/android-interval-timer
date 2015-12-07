package jemboy.alarmz.Builder;

public interface OnDialogCompleted {
    void onAddCompleted(String description, int duration);
    void onEditCompleted(String description, int duration, int position);
    void onDeleteCompleted(int position);
}

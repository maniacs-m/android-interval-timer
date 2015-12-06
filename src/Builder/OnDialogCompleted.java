package jemboy.alarmz.Builder;

public interface OnDialogCompleted {
    void onAddCompleted(String description, int duration);
    void onEditCompleted();
    void onDeleteCompleted();
}

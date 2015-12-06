package jemboy.alarmz.Dialog;

import android.app.AlertDialog;
import android.content.Context;

import jemboy.alarmz.Builder.CreateActivity;
import jemboy.alarmz.Builder.OnDialogCompleted;

public class EditDialog extends AlertDialog.Builder {
    OnDialogCompleted dialogCompleted;

    public EditDialog(Context context) {
        super(context);
        dialogCompleted = ((CreateActivity)context);
    }
}

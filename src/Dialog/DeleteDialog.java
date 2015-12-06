package jemboy.alarmz.Dialog;

import android.app.AlertDialog;
import android.content.Context;

import jemboy.alarmz.Builder.CreateActivity;
import jemboy.alarmz.Builder.OnDialogCompleted;

public class DeleteDialog extends AlertDialog.Builder {
    OnDialogCompleted dialogCompleted;

    public DeleteDialog(Context context) {
        super(context);
        dialogCompleted = ((CreateActivity)context);

    }
}

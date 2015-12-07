package jemboy.alarmz.Dialog;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;

import jemboy.alarmz.Builder.CreateActivity;
import jemboy.alarmz.Builder.OnDialogCompleted;
import jemboy.alarmz.R;

public class DeleteDialog extends AlertDialog.Builder {
    OnDialogCompleted dialogCompleted;
    EditText position;

    public DeleteDialog(Context context) {
        super(context);
        dialogCompleted = ((CreateActivity)context);

        // Get the layout inflater
        LayoutInflater layoutInflater = ((Activity)context).getLayoutInflater();
        View dialogView = layoutInflater.inflate(R.layout.dialog_delete, null);

        position = (EditText)dialogView.findViewById(R.id.position);

        setView(dialogView);

        setPositiveButton("Delete", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                int word1 = Integer.parseInt(position.getText().toString());
                dialogCompleted.onDeleteCompleted(word1);
            }
        });

        setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

            }
        });

    }
}

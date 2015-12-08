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

public class EditDialog extends AlertDialog.Builder {
    private OnDialogCompleted dialogCompleted;
    private EditText description, duration, position;

    public EditDialog(Context context) {
        super(context);
        dialogCompleted = ((CreateActivity)context);

        // Get the layout inflater
        LayoutInflater layoutInflater = ((Activity)context).getLayoutInflater();
        View dialogView = layoutInflater.inflate(R.layout.dialog_edit, null);

        description = (EditText)dialogView.findViewById(R.id.description);
        duration = (EditText)dialogView.findViewById(R.id.duration);
        position = (EditText)dialogView.findViewById(R.id.position);

        setView(dialogView);

        setPositiveButton("Update", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                String word1 = description.getText().toString();
                int word2 = Integer.parseInt(duration.getText().toString());
                int word3 = Integer.parseInt(position.getText().toString());
                dialogCompleted.onEditCompleted(word1, word2, word3);
            }
        });

        setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

            }
        });
    }
}

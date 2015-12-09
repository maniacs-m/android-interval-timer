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

public class AddDialog extends AlertDialog {
    private OnDialogCompleted dialogCompleted;
    private EditText descriptionText, durationText;

    public AddDialog(Context context) {
        super(context);
        dialogCompleted = ((CreateActivity)context);

        // Get the layout inflater
        LayoutInflater layoutInflater = ((Activity)context).getLayoutInflater();
        View dialogView = layoutInflater.inflate(R.layout.dialog_add, null);

        descriptionText = (EditText)dialogView.findViewById(R.id.description);
        durationText = (EditText)dialogView.findViewById(R.id.duration);

        setButton(BUTTON_POSITIVE, "Add", new OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

            }
        });

        setButton(BUTTON_NEGATIVE, "Cancel", new OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

            }
        });

        setView(dialogView);
        setOnShowListener(new OnShowListener() {
            @Override
            public void onShow(DialogInterface dialog) {
                getButton(BUTTON_POSITIVE).setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        String  description = descriptionText.getText().toString(),
                                duration = durationText.getText().toString();
                        if (description.length() > 0 && duration.length() > 0) {
                            dialogCompleted.onAddCompleted(description, Integer.parseInt(duration));
                            dismiss();
                        } else if (description.length() == 0) {
                            descriptionText.setError("You need to write a description!");
                        } else {
                            durationText.setError("You need to specify the duration!");
                        }
                    }
                });
            }
        });
    }
}

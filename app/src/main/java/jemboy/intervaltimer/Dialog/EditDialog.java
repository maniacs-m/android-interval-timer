package jemboy.intervaltimer.Dialog;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;

import jemboy.intervaltimer.Builder.CreateActivity;
import jemboy.intervaltimer.Builder.Creator;
import jemboy.intervaltimer.R;

public class EditDialog extends AlertDialog {
    private Creator dialogCompleted;
    private EditText descriptionText, durationText, positionText;

    public EditDialog(Context context) {
        super(context);
        dialogCompleted = ((CreateActivity)context);

        // Get the layout inflater
        LayoutInflater layoutInflater = ((Activity)context).getLayoutInflater();
        View dialogView = layoutInflater.inflate(R.layout.dialog_edit, null);

        descriptionText = (EditText)dialogView.findViewById(R.id.description);
        durationText = (EditText)dialogView.findViewById(R.id.duration);
        positionText = (EditText)dialogView.findViewById(R.id.position);

        setView(dialogView);

        setButton(BUTTON_POSITIVE, "Delete", new OnClickListener() {
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
                                duration = durationText.getText().toString(),
                                position = positionText.getText().toString();
                        if (description.length() > 0 && duration.length() > 0 && position.length() > 0) {
                            dialogCompleted.onEditCompleted(
                                    description,
                                    Integer.parseInt(duration),
                                    Integer.parseInt(position)
                            );
                            dismiss();
                        } else if (description.length() == 0) {
                            descriptionText.setError("You need to write a description!");
                        } else if (duration.length() == 0) {
                            durationText.setError("You need to specify the duration!");
                        } else {
                            positionText.setError("You need to specify the number!");
                        }
                    }
                });
            }
        });
    }
}

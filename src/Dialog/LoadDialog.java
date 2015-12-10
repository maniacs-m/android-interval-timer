package jemboy.alarmz.Dialog;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import jemboy.alarmz.Builder.LoadActivity;
import jemboy.alarmz.Builder.Loader;

public class LoadDialog extends AlertDialog {
    private Loader dialogCompleted;
    private String title = "";

    public LoadDialog(Context context) {
        super(context);

        dialogCompleted = ((LoadActivity)context);

        setButton(BUTTON_POSITIVE, "Delete", new OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialogCompleted.onDeleteCompleted(title);
            }
        });

        setButton(BUTTON_NEGATIVE, "Load", new OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialogCompleted.onLoadCompleted(title);
            }
        });
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }
}

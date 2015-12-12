package jemboy.alarmz.Dialog;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import jemboy.alarmz.Builder.LoadActivity;
import jemboy.alarmz.Builder.Loader;

public class LoadDialog extends AlertDialog {
    private Loader dialogCompleted;
    private String filename = "";

    public LoadDialog(Context context) {
        super(context);

        dialogCompleted = ((LoadActivity)context);

        setButton(BUTTON_POSITIVE, "Delete", new OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialogCompleted.onDeleteCompleted(filename);
            }
        });

        setButton(BUTTON_NEGATIVE, "Load", new OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialogCompleted.onLoadCompleted(filename);
            }
        });
    }

    public String getFilename() {
        return filename;
    }

    public void setFilename(String filename) {
        setTitle(filename);
        this.filename = filename;
    }
}

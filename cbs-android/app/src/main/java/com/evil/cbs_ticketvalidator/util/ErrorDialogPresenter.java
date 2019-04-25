package com.evil.cbs_ticketvalidator.util;


import android.content.Context;

public class ErrorDialogPresenter {
    private final Context context;
    private final String title;
    private final String message;

    public ErrorDialogPresenter(Context context, String title, String message) {
        this.context = context;
        this.title = title;
        this.message = message;
    }

    public void showDialog() {
        android.app.AlertDialog.Builder builder = new android.app.AlertDialog.Builder(context);
        builder.setMessage(message)
                .setIcon(android.R.drawable.ic_dialog_alert)
                .setTitle(title)
                .setCancelable(false)
                .setPositiveButton("OK", (dialog, which) -> {
                    dialog.dismiss();
                })
                .show();
    }
}
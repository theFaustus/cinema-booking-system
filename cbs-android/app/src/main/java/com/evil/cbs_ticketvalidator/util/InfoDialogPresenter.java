package com.evil.cbs_ticketvalidator.util;


import android.content.Context;

public class InfoDialogPresenter {
    private final Context context;
    private final String title;
    private final String message;

    public InfoDialogPresenter(Context context, String title, String message) {
        this.context = context;
        this.title = title;
        this.message = message;
    }

    public void showDialog() {
        android.app.AlertDialog.Builder builder = new android.app.AlertDialog.Builder(context);
        builder.setMessage(message)
                .setIcon(android.R.drawable.ic_dialog_info)
                .setTitle(title)
                .setCancelable(false)
                .setPositiveButton("OK", (dialog, which) -> {
                    dialog.dismiss();
                })
                .show();
    }
}
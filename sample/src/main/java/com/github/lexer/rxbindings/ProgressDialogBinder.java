package com.github.lexer.rxbindings;


import android.app.ProgressDialog;

/**
 * Created by zakharov on 2/16/14.
 */
public class ProgressDialogBinder implements Binder<Boolean> {
    private ProgressDialog progressDialog;

    public ProgressDialogBinder(ProgressDialog progressDialog) {
        this.progressDialog = progressDialog;
    }

    @Override
    public void onNext(Boolean value) {
        if (value) {
            progressDialog.show();
        } else {
            progressDialog.dismiss();
        }
    }
}

package com.github.lexer.rxbindings;

import android.widget.TextView;

/**
 * Created by zakharov on 2/16/14.
 */
public class TextBinder implements Binder<String> {

    private TextView textView;

    public TextBinder(TextView textView) {
        this.textView = textView;
    }

    @Override
    public void onNext(String value) {
        textView.setText(value);
    }
}

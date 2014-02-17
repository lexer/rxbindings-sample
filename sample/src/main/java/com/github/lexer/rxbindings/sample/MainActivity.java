package com.github.lexer.rxbindings.sample;

import android.app.ProgressDialog;
import android.os.Handler;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.github.lexer.rxbindings.BindingManager;
import com.github.lexer.rxbindings.ProgressDialogBinder;
import com.github.lexer.rxbindings.TextBinder;

import butterknife.ButterKnife;
import butterknife.InjectView;
import rx.subjects.BehaviorSubject;

public class MainActivity extends ActionBarActivity {

    @InjectView(R.id.tv_name)
    TextView tvName;

    @InjectView(R.id.btn_load_name)
    Button btnLoadName;

    BehaviorSubject<String> name = BehaviorSubject.create("name not loaded");
    BehaviorSubject<Boolean> loading = BehaviorSubject.create(false);

    private BindingManager bindingManager;
    private Handler mainThread = new Handler();
    private ProgressDialog progressDialog;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ButterKnife.inject(this);

        progressDialog = new ProgressDialog(this);

        btnLoadName.setOnClickListener(onBtnLoadNameClicked);

        bindingManager = new BindingManager();
        bindingManager
                .bind(new TextBinder(tvName)).to(name)
                .bind(new ProgressDialogBinder(progressDialog)).to(loading);

    }

    @Override
    protected void onResume() {
        super.onResume();

        bindingManager.subscribe();
    }

    @Override
    protected void onPause() {
        super.onPause();

        bindingManager.unsubscribe();
    }

    private View.OnClickListener onBtnLoadNameClicked = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            loadName();
        }
    };

    private void loadName() {
        loading.onNext(true);

        mainThread.postDelayed(new Runnable() {
            @Override
            public void run() {
                name.onNext("Alexander");
                loading.onNext(false);
            }
        }, 5000);
    }

}

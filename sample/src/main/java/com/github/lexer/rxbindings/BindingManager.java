package com.github.lexer.rxbindings;


import java.util.ArrayList;

import rx.Observable;
import rx.Subscription;
import rx.subscriptions.CompositeSubscription;
import rx.util.functions.Action1;

/**
 * Created by zakharov on 2/16/14.
 */
public class BindingManager {
    private ArrayList<Binding<?>> bindings = new ArrayList<Binding<?>>();
    private CompositeSubscription subscribtions;

    public <T> BindingBuilder<T> bind(Binder<T> binder) {
        return new BindingBuilder<T>(this, binder);
    }

    public void subscribe() {
        subscribtions = new CompositeSubscription();
        for (final Binding<?> binding : bindings) {
            Subscription subscription = binding.subscribe();
            subscribtions.add(subscription);
        }
    }

    public void unsubscribe() {
        subscribtions.unsubscribe();
    }

    private <T> void add(Binding<T> binding) {
        bindings.add(binding);
    }

    public static class BindingBuilder<T> {
        private BindingManager bindingManager;
        private Binder<T> binder;

        public BindingBuilder(BindingManager bindingManager, Binder<T> binder) {
            this.bindingManager = bindingManager;
            this.binder = binder;
        }

        public BindingManager to(Observable<T> property) {
            bindingManager.add(new Binding<T>(binder, property));
            return bindingManager;
        }
    }

    public static class Binding<T> {
        private Binder<T> binder;
        private Observable<T> property;

        public Binding(Binder<T> binder, Observable<T> property) {
            this.binder = binder;
            this.property = property;
        }

        public Binder<T> getBinder() {
            return binder;
        }

        public Observable<T> getProperty() {
            return property;
        }

        public Subscription subscribe() {
            return property.subscribe(new Action1<T>() {
                @Override
                public void call(T t) {
                    binder.onNext(t);
                }
            });
        }
    }

}

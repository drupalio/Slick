package com.github.slick;

import android.app.Activity;
import android.app.Fragment;


/**
 * @author : Pedramrn@gmail.com
 *         Created on: 2016-11-03
 */

public class SlickDelegateFragment<V, P extends SlickPresenter<V>> {

    private String id;
    private OnDestroyListener listener;

    private P presenter;
    private Class cls;
    private boolean multiInstance = false;

    public SlickDelegateFragment(P presenter, Class cls, String id) {
        if (presenter == null) {
            throw new IllegalStateException("Presenter cannot be null.");
        }
        this.presenter = presenter;
        this.cls = cls;
        this.id = id;
        if (id != null) multiInstance = true;
    }

    public SlickDelegateFragment(P presenter, Class cls) {
        if (presenter == null) {
            throw new IllegalStateException("Presenter cannot be null.");
        }
        this.presenter = presenter;
        this.cls = cls;
    }

    public void onStart(V view) {
        if (multiInstance) {
            if (isSameInstance(view)) {
                presenter.onViewUp(view);
            }

        } else if (cls.isInstance(view)) {
            presenter.onViewUp(view);
        }
    }

    public void onStop(V view) {
        if (multiInstance) {
            if (isSameInstance(view)) {
                presenter.onViewDown();
            }
        } else if (cls.isInstance(view)) {
            presenter.onViewDown();
        }
    }

    public void onDestroy(V view) {
        if (multiInstance) {
            if (isSameInstance(view)) {
                destroy(view);
            }
        } else if (cls.isInstance(view)) {
            destroy(view);
        }
    }

    private void destroy(V view) {
        Activity activity;
        if (view instanceof Fragment) {
            activity = ((Fragment) view).getActivity();
        } else {
            throw new IllegalStateException(
                    "View should be either a subclass of support Fragment or android.app.Fragment");
        }
        if (!activity.isChangingConfigurations()) {
            presenter.onDestroy();
            if (listener != null) {
                listener.onDestroy(id);
            }
            presenter = null;
        }
    }

    public P getPresenter() {
        return presenter;
    }

    public void setListener(OnDestroyListener listener) {
        this.listener = listener;
    }


    public static String getId(Object view) {
        if (view instanceof SlickUniqueId) return ((SlickUniqueId) view).getUniqueId();
        return null;
    }

    private boolean isSameInstance(Object view) {
        final String id = getId(view);
        return id != null && id.equals(this.id);
    }
}
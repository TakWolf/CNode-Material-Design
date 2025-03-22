package org.cnodejs.android.md.ui.widget;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.Parcel;
import android.os.Parcelable;
import android.util.AttributeSet;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import androidx.annotation.AttrRes;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.StyleRes;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import org.cnodejs.android.md.ui.jsbridge.AppJavascriptInterface;

import java.util.ArrayList;
import java.util.List;

public class CNodeWebView extends WebView {
    @Nullable private OnHideMaskListener onHideMaskListener;
    private final List<OnScrollChangedListener> onScrollChangedListeners = new ArrayList<>();

    private boolean javascriptReady = false;

    @Nullable private SavedState savedState;
    @Nullable private Insets insets;

    public CNodeWebView(@NonNull Context context) {
        super(context);
        init();
    }

    public CNodeWebView(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public CNodeWebView(@NonNull Context context, @Nullable AttributeSet attrs, @AttrRes int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    public CNodeWebView(@NonNull Context context, @Nullable AttributeSet attrs, @AttrRes int defStyleAttr, @StyleRes int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        init();
    }

    @SuppressLint("SetJavaScriptEnabled")
    private void init() {
        getSettings().setJavaScriptEnabled(true);

        addJavascriptInterface(new AppJavascriptInterface(), AppJavascriptInterface.NAME);

        setWebViewClient(new WebViewClient() {
            @Override
            public void onPageFinished(WebView view, String url) {
                if (!javascriptReady) {
                    javascriptReady = true;
                    if (savedState != null) {
                        scrollTo(savedState.scrollX, savedState.scrollY);
                        savedState = null;
                    }
                    if (insets != null) {
                        callJsUpdateWebViewInsets(insets);
                        insets = null;
                    }
                    onJavascriptReady();
                    if (onHideMaskListener != null) {
                        onHideMaskListener.OnHideMask();
                    }
                }
            }
        });

        ViewCompat.setOnApplyWindowInsetsListener(this, (v, windowInsets) -> {
            Insets insets = windowInsets.getInsets(WindowInsetsCompat.Type.navigationBars());
            if (javascriptReady) {
                callJsUpdateWebViewInsets(insets);
            } else {
                CNodeWebView.this.insets = insets;
            }
            return windowInsets;
        });
    }

    public boolean isJavascriptReady() {
        return javascriptReady;
    }

    protected void onJavascriptReady() {}

    private void callJsUpdateWebViewInsets(Insets insets) {
        int webPx = Math.round(insets.bottom / getResources().getDisplayMetrics().density);
        String script = "document.body.style.paddingBottom = '" + webPx + "px'";
        evaluateJavascript(script, null);
    }

    public void setOnHideMaskListener(@Nullable OnHideMaskListener onHideMaskListener) {
        this.onHideMaskListener = onHideMaskListener;
    }

    public interface OnHideMaskListener {
        void OnHideMask();
    }

    @Override
    protected void onScrollChanged(int l, int t, int oldl, int oldt) {
        super.onScrollChanged(l, t, oldl, oldt);
        for (OnScrollChangedListener listener : onScrollChangedListeners) {
            listener.onScrollChanged(l, t, oldl, oldt);
        }
    }

    public void addOnScrollChangedListener(@NonNull OnScrollChangedListener listener) {
        onScrollChangedListeners.add(listener);
    }

    public void removeOnScrollChangedListener(@NonNull OnScrollChangedListener listener) {
        onScrollChangedListeners.remove(listener);
    }

    public interface OnScrollChangedListener {
        void onScrollChanged(int l, int t, int oldl, int oldt);
    }

    @Nullable
    @Override
    protected Parcelable onSaveInstanceState() {
        SavedState savedState = new SavedState(super.onSaveInstanceState());
        savedState.scrollX = getScrollX();
        savedState.scrollY = getScrollY();
        return savedState;
    }

    @Override
    protected void onRestoreInstanceState(Parcelable state) {
        if (!(state instanceof SavedState)) {
            super.onRestoreInstanceState(state);
            return;
        }
        SavedState savedState = (SavedState) state;
        if (javascriptReady) {
            scrollTo(savedState.scrollX, savedState.scrollY);
        } else {
            this.savedState = savedState;
        }
        super.onRestoreInstanceState(savedState.getSuperState());
    }

    private static class SavedState extends BaseSavedState {
        int scrollX;
        int scrollY;

        public SavedState(Parcelable superState) {
            super(superState);
        }

        public SavedState(Parcel source) {
            super(source);
            readValues(source, null);
        }

        public SavedState(Parcel source, ClassLoader loader) {
            super(source, loader);
            readValues(source, loader);
        }

        private void readValues(@NonNull Parcel source, @Nullable ClassLoader loader) {
            scrollX = source.readInt();
            scrollY = source.readInt();
        }

        @Override
        public void writeToParcel(Parcel out, int flags) {
            super.writeToParcel(out, flags);
            out.writeInt(scrollX);
            out.writeInt(scrollY);
        }

        public static final Creator<SavedState> CREATOR = new ClassLoaderCreator<SavedState>() {
            @Override
            public SavedState createFromParcel(Parcel source, ClassLoader loader) {
                return new SavedState(source, loader);
            }

            @Override
            public SavedState createFromParcel(Parcel source) {
                return createFromParcel(source, null);
            }

            @Override
            public SavedState[] newArray(int size) {
                return new SavedState[size];
            }
        };
    }
}

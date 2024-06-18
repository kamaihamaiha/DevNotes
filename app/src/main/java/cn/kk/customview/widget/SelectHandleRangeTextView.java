package cn.kk.customview.widget;

import android.content.Context;
import android.os.Build;
import android.os.Handler;
import android.os.LocaleList;
import android.os.Looper;
import android.text.Selection;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.TextUtils;
import android.text.style.BackgroundColorSpan;
import android.util.AttributeSet;
import android.view.ActionMode;
import android.view.Menu;
import android.view.MenuItem;
import android.view.textclassifier.TextClassification;
import android.view.textclassifier.TextClassificationManager;
import android.view.textclassifier.TextClassifier;
import android.view.textclassifier.TextSelection;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.core.content.ContextCompat;

import cn.kk.base.utils.ThreadHelper;


/**
 * 支持选中文字，去掉系统actiomode 菜单
 */
public class SelectHandleRangeTextView extends AppCompatTextView {
    public SelectHandleRangeTextView(@NonNull Context context) {
        super(context);
        init();
    }

    public SelectHandleRangeTextView(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public SelectHandleRangeTextView(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private SpannableString mSpanStr;
    // 单词播放高亮背景色
    private SelectContentCallback mSelectContentCallback;
    private String lastSelectedText = "";
    private long actionModeRefreshTimeStamp = 0;
    private boolean supportClassifier = false;
    private static final int DETECT_INTERVAL = 300; // unit: ms
    // 过滤频繁更新
    private Handler mHandler = new Handler(Looper.myLooper());

    Runnable detectSelectedTextRunnable = new Runnable() {
        @Override
        public void run() {
            detectSelectText();
            mHandler.postDelayed(detectSelectedTextRunnable, DETECT_INTERVAL);
        }
    };

    private void detectSelectText() {
        if (getSelectionStart() >= 0 && getSelectionStart() < getSelectionEnd()) {
            String selectedText = getText()
                    .subSequence(getSelectionStart(), getSelectionEnd())
                    .toString();
            if (!lastSelectedText.equals(selectedText)) {
                if (!supportClassifier) {
                    if (fingerMoving()) return;
                }

                lastSelectedText = selectedText;
                // notify refresh
                if (mSelectContentCallback != null) {
                    ThreadHelper.INSTANCE.runOnUIThread(() -> mSelectContentCallback.onSelectText(selectedText));
                }
            }
        }
    }

    private void startDetectSelectedText() {
        mHandler.post(detectSelectedTextRunnable);
    }

    private void endDetectSelectedText(){
        mHandler.removeCallbacks(detectSelectedTextRunnable);
        actionModeRefreshTimeStamp = 0;
    }

    public void setSelectContentCallback(SelectContentCallback mSelectContentCallback) {
        this.mSelectContentCallback = mSelectContentCallback;
    }

    @Override
    public void setText(CharSequence text, TextView.BufferType type) {
        super.setText(text, type);
        mSpanStr = new SpannableString(text);
    }

    private void init() {
        setTextIsSelectable(true);
        setTextSize(18);
        setLineSpacing(0, 1.2f);
        setCustomSelectionActionModeCallback(new ActionMode.Callback() {
            @Override
            public boolean onCreateActionMode(ActionMode mode, Menu menu) {
                return true;
            }

            @Override
            public boolean onPrepareActionMode(ActionMode mode, Menu menu) { // 手指移动，或者抬起时会走到这里
                menu.clear();
//                clearSysItemMenuDelay(mode); //小米手机会偷偷加入系统菜单项，所以这里延时删除
                return true;
            }

            @Override
            public boolean onActionItemClicked(ActionMode mode, MenuItem item) {
                if (!supportClassifier) {
                    actionModeRefreshTimeStamp = System.currentTimeMillis();
                }
                return false;
            }

            @Override
            public void onDestroyActionMode(ActionMode mode) {
                pauseHighlight = false;
            }
        });

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O_MR1) { // https://stackoverflow.com/questions/68241252/textview-selection-event-for-the-moment-when-selection-is-done/68247023#68247023
            supportClassifier = true;

            // 特殊处理 Android 8.1
            if (Build.VERSION.SDK_INT == Build.VERSION_CODES.O_MR1) {
                TextClassificationManager textClassificationManager = (TextClassificationManager) getContext().getSystemService(Context.TEXT_CLASSIFICATION_SERVICE);
                setTextClassifier(new EuTextClassifier(textClassificationManager.getTextClassifier()));
            } else {
                ThreadHelper.INSTANCE.runOnUIThread(new Runnable() {
                    @Override
                    public void run() {

                    }
                });
                setTextClassifier(new TextClassifier() {
                    @NonNull
                    @Override
                    public TextClassification classifyText(@NonNull TextClassification.Request request) { // 选中文本完成时触发
                        detectSelectText();
                        return TextClassifier.super.classifyText(request);
                    }
                });
            }

        } else { // Android 8.0 以下: 用 startDetectSelectedText()

        }

        setOnLongClickListener(v -> {
            pauseHighlight = true;
            if (!supportClassifier) {
                startDetectSelectedText();
            }
            return false;
        });
    }

    private boolean fingerMoving(){ // 通过 actionMode 刷新情况判断是否手指在移动
        if (actionModeRefreshTimeStamp == 0) return false;
        return System.currentTimeMillis() - actionModeRefreshTimeStamp < DETECT_INTERVAL;
    }

    private void clearSysItemMenuDelay(ActionMode mode) {
        clearSysItemMenu(mode, 200);
    }

    private void clearSysItemMenu(ActionMode mode, long delay){
        mHandler.postDelayed(() -> {
            if (mode != null && mode.getMenu().size() > 0) {
                mode.getMenu().clear();
            }
        }, delay);
    }


    private boolean pauseHighlight = false;

    private void realClearSpan(Class kind) {
        Object[] spans = mSpanStr.getSpans(0, getText().length(), kind);
        for (Object span : spans) {
            mSpanStr.removeSpan(span);
        }
    }

    public void clearSelection(){
        Selection.removeSelection((Spannable) getText());
        pauseHighlight = false;
        lastSelectedText = "";
        if (!supportClassifier) {
            endDetectSelectedText();
        }
    }


    @RequiresApi(api = Build.VERSION_CODES.O)
    class EuTextClassifier implements TextClassifier {
        private TextClassifier fallback;

        public EuTextClassifier(TextClassifier fallback) {
            this.fallback = fallback;
        }

        @NonNull
        @Override
        public TextSelection suggestSelection(@NonNull CharSequence text, int selectionStartIndex, int selectionEndIndex, @Nullable LocaleList defaultLocales) {
            return fallback.suggestSelection(text, selectionStartIndex, selectionEndIndex, defaultLocales);
        }

        @NonNull
        @Override
        public TextClassification classifyText(@NonNull CharSequence text, int startIndex, int endIndex, @Nullable LocaleList defaultLocales) {
            detectSelectText();
            return fallback.classifyText(text, startIndex, endIndex, defaultLocales);
        }
    }


    public interface SelectContentCallback {
        void onSelectText(@NonNull String selectedText);
    }

}

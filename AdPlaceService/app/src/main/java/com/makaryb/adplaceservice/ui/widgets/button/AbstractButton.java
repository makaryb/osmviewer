package com.makaryb.adplaceservice.ui.widgets.button;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;

import androidx.appcompat.widget.AppCompatButton;

import static com.makaryb.adplaceservice.R.mipmap.roundbackgroundnormal;
import static com.makaryb.adplaceservice.utils.resources.ResourceManager.getDrawable;

/**
 * Абстрактный класс для кнопок, используемых в данном прложении.
 * Для работы с данным классом необходимо в классе наследнике инициализировать поля iconId и onClickListener
 * и затем вызвать метод {@link #onCreate() onCreate}.
 */

public abstract class AbstractButton extends AppCompatButton {
    private static final int SIZE = 150;
    private final Drawable background = getDrawable(roundbackgroundnormal);
    protected int iconId;
    protected OnClickListener onClickListener;

    public AbstractButton(Context context) {
        super(context);
    }

    public AbstractButton(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public AbstractButton(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    protected void onCreate() {
        draw();
        setOnClickListener(onClickListener);
    }

    void draw() {
        setBackground(background);

        Drawable icon = getDrawable(iconId);
        icon.setBounds(0, 0, SIZE, SIZE);
        setCompoundDrawables(icon, null, null, null);
        setPadding(SIZE/2, 0, 0, 0);
    }
}

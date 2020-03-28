package com.makaryb.adplaceservice.ui.widgets.button;

import android.content.Context;
import android.util.AttributeSet;

import com.makaryb.adplaceservice.ui.activities.MapActivity;

import static com.makaryb.adplaceservice.R.drawable.osm_logo;
import static com.makaryb.adplaceservice.utils.ServiceManager.startActivity;

public class MapButtonWidget extends AbstractButton {

    public MapButtonWidget(Context context) {
        super(context);
        init();
    }

    public MapButtonWidget(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public MapButtonWidget(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private void init() {
        iconId = osm_logo;
        onClickListener = (v -> startActivity(MapActivity.class));
        onCreate();
    }
}

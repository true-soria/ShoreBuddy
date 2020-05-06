package com.example.shorebuddy.views.homepage;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.shorebuddy.R;
import com.example.shorebuddy.data.lakes.Lake;
import java.util.Locale;

public class LakeWidget extends ModuleWidget {

    static final String LAKE = "lake";
    private Drawable icon;
    private String title;

    private final TextView countyText;

    private final ImageView boatRampTickbox;
    private final ImageView wheelchairTickbox;
    private final ImageView fuelTickbox;
    private final ImageView restroomTickbox;
    private final Drawable checkMark;

    private final TextView elevationText;
    private final TextView fishingCommentsText;

    public LakeWidget(Context context) {
        this(context, null);
    }

    public LakeWidget(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public LakeWidget(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        inflate(getContext(), R.layout.lake_widget, this);

        countyText = findViewById(R.id.county);

        boatRampTickbox = findViewById(R.id.boat_ramp_tickbox);
        wheelchairTickbox = findViewById(R.id.wheelchair_tickbox);
        fuelTickbox = findViewById(R.id.fuel_tickbox);
        restroomTickbox = findViewById(R.id.restroom_tickbox);

        elevationText = findViewById(R.id.lake_elevation);
        fishingCommentsText = findViewById(R.id.fishing_comments);

        // TODO Shore Buddy / Lake icon
        checkMark = getResources().getDrawable(R.drawable.ic_check_mark,null);
        this.icon = getResources().getDrawable(R.drawable.ic_compass_rose,null);
        this.title = String.format(Locale.US, "%s", LAKE);
    }

    @Override
    public Drawable getIcon() {
        return icon;
    }

    @Override
    public String getTitle() {
        return title;
    }

    public void setData(Lake lake){
        this.title = lake.lakeName;
        setAmenities(lake);

        countyText.setText(String.format(Locale.US, "%s County", lake.county));
        elevationText.setText(String.format(Locale.US, "%s ft Elevation", lake.elevation));
        setFishingComments(lake.fishingComments);

    }

    private void setFishingComments(String fishingComments)
    {
        if (fishingComments == null)
            fishingCommentsText.setHeight(0);
        else
        {
            fishingCommentsText.setHeight(ViewGroup.LayoutParams.WRAP_CONTENT);
            fishingCommentsText.setText(String.format(Locale.US, "%s", fishingComments));
        }
    }

    private void setAmenities(Lake lake) {
        this.boatRampTickbox.setImageDrawable(applyCheckMark(lake.boatramp));
        this.wheelchairTickbox.setImageDrawable(applyCheckMark(lake.wheelChairAccess));
        this.fuelTickbox.setImageDrawable(applyCheckMark(lake.fuel));
        this.restroomTickbox.setImageDrawable(applyCheckMark(lake.restroom));
    }

    private Drawable applyCheckMark(boolean canApply)
    {
        if (canApply)
            return checkMark;
        else
            return null;
    }
}

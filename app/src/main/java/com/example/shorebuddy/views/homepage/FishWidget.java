package com.example.shorebuddy.views.homepage;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.widget.TextView;

import com.example.shorebuddy.R;
import com.example.shorebuddy.data.fish.Fish;

import java.util.Iterator;
import java.util.List;
import java.util.Locale;

public class FishWidget extends ModuleWidget {

    static final String FISH = "Fish";
    private Drawable icon;
    private String title;
    private final int FISH_DISPLAYED = 8;

    private final TextView leftProperty;
    private final TextView rightProperty;


    public FishWidget(Context context) {
        this(context, null);
    }

    public FishWidget(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public FishWidget(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        inflate(getContext(), R.layout.fish_widget, this);

        leftProperty = findViewById(R.id.left_property);
        rightProperty = findViewById(R.id.right_property);

        // TODO Fish icon
        this.icon = getResources().getDrawable(R.drawable.ic_fish_2,null);
        this.title = String.format(Locale.US, "%s", FISH);
    }

    @Override
    public Drawable getIcon() {
        return icon;
    }

    @Override
    public String getTitle() {
        return title;
    }

    public void setData(List<Fish> fishList){
        setFish(fishList);
    }

    public void setTitle(String title) {this.title = title; }

    private void setFish(List<Fish> fishList)
    {
        Iterator<Fish> fishIterator = fishList.iterator();
        StringBuilder leftPropertyFish = new StringBuilder();
        StringBuilder rightPropertyFish = new StringBuilder();
        String NO_FISH_LIST = "\tNo fish available";

        for (int i = 0; (i < FISH_DISPLAYED/2) && fishIterator.hasNext(); i ++)
        {
            leftPropertyFish.append("\t• ");
            leftPropertyFish.append(fishIterator.next());
            leftPropertyFish.append("\n");
        }
        if (fishList.isEmpty())
            leftPropertyFish.append(NO_FISH_LIST);
        else
            leftPropertyFish.deleteCharAt(leftPropertyFish.length() - 1);


        for (int i = 0; (i < FISH_DISPLAYED/2) && fishIterator.hasNext(); i ++)
        {
            rightPropertyFish.append("\t•");
            rightPropertyFish.append(fishIterator.next());
            rightPropertyFish.append("\n");
        }

        if (fishIterator.hasNext())
        {
            leftPropertyFish.append("\n");
            rightPropertyFish.append("\t...");
        }

        this.leftProperty.setText(String.format(Locale.US, "%s", leftPropertyFish));
        this.rightProperty.setText(String.format(Locale.US, "%s", rightPropertyFish));
    }
}

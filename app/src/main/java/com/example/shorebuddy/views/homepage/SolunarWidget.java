package com.example.shorebuddy.views.homepage;

import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.widget.TextView;

import com.example.shorebuddy.R;

import java.util.ArrayList;

public class SolunarWidget extends ModuleWidget {
    private final TextView property1;
    private final TextView property2;
    private final TextView property3;
    private final TextView property4;
    private String[] widgetItemValues;


    public SolunarWidget(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        String[] widgetItemNames;

        if (attrs != null)
        {
            TypedArray a = context.obtainStyledAttributes(attrs, R.styleable.SolunarWidget, defStyleAttr, 0);
            title = a.getString(R.styleable.SolunarWidget_title);
            widgetItemValues = getWidgetProperties(a);
            widgetItemNames = getWidgetPropertyNames(a);
            a.recycle();
        }
        else 
        {
            title = "moon";
            widgetItemNames = null;
            widgetItemValues = null;
        }
        icon = null;

        property1 = findViewById(R.id.sol_widget_1_data);
        property2 = findViewById(R.id.sol_widget_2_data);
        property3 = findViewById(R.id.sol_widget_3_data);
        property4 = findViewById(R.id.sol_widget_4_data);

        TextView propertyName1 = findViewById(R.id.sol_widget_1);
        TextView propertyName2 = findViewById(R.id.sol_widget_2);
        TextView propertyName3 = findViewById(R.id.sol_widget_3);
        TextView propertyName4 = findViewById(R.id.sol_widget_4);

        if (widgetItemNames != null)
        {
            propertyName1.setText(widgetItemNames[0]);
            propertyName2.setText(widgetItemNames[1]);
            propertyName3.setText(widgetItemNames[2]);
            propertyName4.setText(widgetItemNames[3]);
        }
    }

    private String[] getWidgetProperties(TypedArray a)
    {
        ArrayList<String> items = new ArrayList<>();
        items.add(a.getString(R.styleable.SolunarWidget_property1));
        items.add(a.getString(R.styleable.SolunarWidget_property2));
        items.add(a.getString(R.styleable.SolunarWidget_property3));
        items.add(a.getString(R.styleable.SolunarWidget_property4));
        return (String[]) items.toArray();
    }

    private String[] getWidgetPropertyNames(TypedArray a)
    {
        ArrayList<String> items = new ArrayList<>();
        items.add(a.getString(R.styleable.SolunarWidget_property_name1));
        items.add(a.getString(R.styleable.SolunarWidget_property_name2));
        items.add(a.getString(R.styleable.SolunarWidget_property_name3));
        items.add(a.getString(R.styleable.SolunarWidget_property_name4));
        return (String[]) items.toArray();
    }

    public void setProperty1(String property) { property1.setText(property); }

    public void setProperty2(String property) { property2.setText(property); }

    public void setProperty3(String property) { property3.setText(property); }

    public void setProperty4(String property) { property4.setText(property); }

    public String[] getWidgetItemValues() { return widgetItemValues; }

    public void setTitleAndIcon(String title)
    {
        this.title = title;

        switch (title)
        {
            case "New Moon":
                this.icon = getResources().getDrawable(R.drawable.ic_moon1, null);
                break;
            case "Waxing Crescent":
                this.icon = getResources().getDrawable(R.drawable.ic_moon1, null);
                break;
            case "First Quarter":
                this.icon = getResources().getDrawable(R.drawable.ic_moon1, null);
                break;
            case "Waxing Gibbous":
                this.icon = getResources().getDrawable(R.drawable.ic_moon1, null);
                break;
            case "Full Moon":
                this.icon = getResources().getDrawable(R.drawable.ic_moon1, null);
                break;
            case "Waning Gibbous":
                this.icon = getResources().getDrawable(R.drawable.ic_moon1, null);
                break;
            case "Last Quarter":
                this.icon = getResources().getDrawable(R.drawable.ic_moon1, null);
                break;
            case "Waning Crescent":
                this.icon = getResources().getDrawable(R.drawable.ic_moon1, null);
                break;
        }
    }

}

package com.example.shorebuddy.utilities;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.graphics.drawable.Drawable;
import android.view.View;

import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.RecyclerView;

import com.example.shorebuddy.R;

public abstract class SwipeToDeleteCallback extends ItemTouchHelper.SimpleCallback {

    private Drawable deleteIcon;
    private int intrinsicHeight;
    private int intrinsicWidth;
    private Drawable deleteBackground;
    private Drawable mainBackgroundFull;
    private Drawable mainBackgroundHalf;
    private Paint clearPaint = new Paint();

    public SwipeToDeleteCallback(Context context) {
        super(0, ItemTouchHelper.LEFT);

        mainBackgroundFull = ContextCompat.getDrawable(context, R.drawable.rounded_square_white);
        mainBackgroundHalf = ContextCompat.getDrawable(context, R.drawable.half_rounded_square_white);
        deleteBackground = ContextCompat.getDrawable(context, R.drawable.half_rounded_delete);

        deleteIcon = ContextCompat.getDrawable(context, R.drawable.ic_delete_black_24dp);
        intrinsicHeight = deleteIcon.getIntrinsicHeight();
        intrinsicWidth = deleteIcon.getIntrinsicWidth();

        clearPaint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.CLEAR));
    }

    @Override
    public boolean onMove(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder, RecyclerView.ViewHolder target) {
        return false;
    }

    @Override
    public void onChildDraw(Canvas c,
                            RecyclerView recyclerView,
                            RecyclerView.ViewHolder viewHolder,
                            float dX,
                            float dY,
                            int actionState,
                            boolean isCurrentlyActive)
    {
        View itemView = viewHolder.itemView;
        int itemHeight = itemView.getBottom() - itemView.getTop();
        boolean isCanceled = (dX == 0f && !isCurrentlyActive);

        if (isCanceled) {
            clearCanvas(c, itemView.getRight() + dX, (float) itemView.getTop(), + (float) itemView.getRight(), (float) itemView.getBottom());
            itemView.setBackground(mainBackgroundFull);
            super.onChildDraw(c, recyclerView, viewHolder, dX, dY, actionState, isCurrentlyActive);
            return;
        }

        deleteBackground.setBounds(itemView.getRight() + (int) dX ,itemView.getTop(), itemView.getRight(), itemView.getBottom());
        deleteBackground.draw(c);

        itemView.setBackground(mainBackgroundHalf);

        int deleteIconMargin = (itemHeight - intrinsicHeight) / 2;
        int deleteIconTop = itemView.getTop() + deleteIconMargin;
        int deleteIconLeft = itemView.getRight() - deleteIconMargin - intrinsicWidth;
        int deleteIconRight = itemView.getRight() - deleteIconMargin;
        int deleteIconBottom = deleteIconTop + intrinsicHeight;

        deleteIcon.setBounds(deleteIconLeft, deleteIconTop, deleteIconRight, deleteIconBottom);
        deleteIcon.draw(c);

        super.onChildDraw(c, recyclerView, viewHolder, dX, dY, actionState, isCurrentlyActive);
    }

    private void clearCanvas(Canvas c, float left, float top, float right, float bottom) {
        if (c != null) {
            c.drawRect(left, top, right, bottom, clearPaint);
        }
    }
}

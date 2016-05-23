package com.xcz1899.navigation;


import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.support.v4.content.ContextCompat;
import android.util.AttributeSet;
import android.view.View;

/**
 * 底部导航的按钮项
 */
class TabItem extends View {

    /**
     * 显示的文字
     */
    private String mText;

    /**
     * 未选中状态下的图标
     */
    private int mIconDefault;

    /**
     * 选中状态下的图标，未设置的话就和未选中状态下的一样
     */
    private int mIconSelected;

    /**
     * 未选中状态下的图标和文字颜色
     */
    private int mColorDefault;

    /**
     * 选中状态下的图标和文字颜色
     */
    private int mColorSelected;


    private Context mContext;


    /**
     * 表示选中或未选中
     */
    private boolean isSelected;


    public TabItem(Context context) {
        super(context);
        init(context);
    }

    public TabItem(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    public TabItem(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context);
    }

    private void init(Context context) {
        mContext = context;
    }


    @Override
    public boolean isSelected() {
        return isSelected;
    }

    @Override
    public void setSelected(boolean selected) {
        isSelected = selected;
        invalidate();
    }


    public void setmColorDefault(int mColorDefault) {
        this.mColorDefault = mColorDefault;
    }


    public void setmColorSelected(int mColorSelected) {
        this.mColorSelected = mColorSelected;
    }


    public void setmIconDefault(int mIconDefault) {
        this.mIconDefault = mIconDefault;
    }


    public void setmIconSelected(int mIconSelected) {
        this.mIconSelected = mIconSelected;
    }


    public void setmText(String mText) {
        this.mText = mText;
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        // 每个item高度为56dp
        setMeasuredDimension(getMeasuredWidth(), (int) Utils.dp2px(mContext, 56));
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        drawText(canvas);
        drawIcon(canvas);
    }


    /**
     * 画文字
     *
     * @param canvas {@link Canvas Canvas}
     */
    private void drawText(Canvas canvas) {

        Rect textBound = new Rect();
        Paint textPaint = new Paint();
        if (isSelected) {
            textPaint.setTextSize(Utils.sp2px(mContext, 14));//选中size为14sp，未选中为12sp
        } else {
            textPaint.setTextSize(Utils.sp2px(mContext, 12));
        }

        textPaint.getTextBounds(mText, 0, mText.length(), textBound);
        textPaint.setAntiAlias(true);
        textPaint.setTextAlign(Paint.Align.CENTER);

        //判断是否选中再设置颜色
        if (!isSelected) {//未选中
            textPaint.setColor(mColorDefault);
        } else {
            textPaint.setColor(mColorSelected);
        }

        Paint.FontMetrics fontMetrics = textPaint.getFontMetrics();
        //水平居中
        float x = getMeasuredWidth() / 2f;
        //距离底部10dp
        float y = getMeasuredHeight() - Utils.dp2px(mContext, 10) - textBound.height() / 2f - fontMetrics.descent + (fontMetrics.descent - fontMetrics.ascent) / 2;

        canvas.drawText(mText, x, y, textPaint);
    }


    /**
     * 画ICON
     *
     * @param canvas {@link Canvas Canvas}
     */
    private void drawIcon(Canvas canvas) {
        int width = (int) Utils.dp2px(mContext, 24);//icon 24 x 24dp
        Bitmap bitmap = Bitmap.createBitmap(width, width, Bitmap.Config.ARGB_8888);
        Canvas canvasTem = new Canvas(bitmap);
        Paint paint = new Paint();
        //判断是否选中再设置颜色
        if (!isSelected) {
            paint.setColor(mColorDefault);
        } else {
            paint.setColor(mColorSelected);
        }
        paint.setAntiAlias(true);
        canvasTem.drawRect(0, 0, width, width, paint);
        paint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.DST_IN));
        canvasTem.drawBitmap(isSelected ? getICON(ContextCompat.getDrawable(mContext, mIconSelected)) : getICON(ContextCompat.getDrawable(mContext, mIconDefault)), 0, 0, paint);

        float left = getMeasuredWidth() / 2f - Utils.dp2px(mContext, 12);//水平居中 因为宽度是24dp，因此起点为12dp

        float top;
        if (isSelected) {
            top = Utils.dp2px(mContext, 6);//选中的时候，top为6dp，未选中为8dp
        } else {
            top = Utils.dp2px(mContext, 8);//选中的时候，top为6dp，未选中为8dp
        }

        canvas.drawBitmap(bitmap, left, top, null);

        //回收
        bitmap.recycle();
    }

    /**
     * Drawable 转 Bitmap
     * @param drawable
     * @return
     */
    private Bitmap getICON(Drawable drawable) {
        int width = drawable.getIntrinsicWidth();
        int height = drawable.getIntrinsicHeight();
        Bitmap oldbmp = Utils.drawableToBitmap(drawable);
        Matrix matrix = new Matrix();
        float scaleWidth = (Utils.dp2px(mContext, 24) / width);
        float scaleHeight = (Utils.dp2px(mContext, 24) / height);
        matrix.postScale(scaleWidth, scaleHeight);
        Bitmap newbmp = Bitmap.createBitmap(oldbmp, 0, 0, width, height, matrix, true);
        oldbmp.recycle();
        return newbmp;
    }

}

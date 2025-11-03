package com.example.doodlz;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Path;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.graphics.Color;

import java.util.ArrayList;
import java.util.List;

class Stroke {
    Path path;
    Paint paint;

    Stroke(Path p, Paint paint) {
        this.path = p;
        this.paint = new Paint(paint);
    }
}

public class DoodleView extends View {
    private Bitmap bitmap;
    private Canvas drawCanvas;
    private Paint drawPaint;
    private float strokeWidth;
    private int paintColor = Color.BLACK;
    private List<Stroke> strokes = new ArrayList<>();
    private Path currentPath;
    private Paint currentPaint;

    public DoodleView(Context context) {
        super(context);
        init();
    }

    public DoodleView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    private void init() {
        strokeWidth = getResources().getDimension(R.dimen.default_line_width);
        drawPaint = new Paint();
        drawPaint.setColor(paintColor);
        drawPaint.setAntiAlias(true);
        drawPaint.setStrokeWidth(strokeWidth);
        drawPaint.setStyle(Paint.Style.STROKE);
        drawPaint.setStrokeJoin(Paint.Join.ROUND);
        drawPaint.setStrokeCap(Paint.Cap.ROUND);
        currentPaint = new Paint(drawPaint);
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        if (bitmap != null) {
            bitmap.recycle();
        }
        bitmap = Bitmap.createBitmap(w, h, Bitmap.Config.ARGB_8888);
        drawCanvas = new Canvas(bitmap);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        for (Stroke s : strokes) {
            canvas.drawPath(s.path, s.paint);
        }
        if (currentPath != null) {
            canvas.drawPath(currentPath, currentPaint);
        }
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        float touchX = event.getX();
        float touchY = event.getY();

        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                currentPath = new Path();
                currentPath.moveTo(touchX, touchY);
                currentPaint = new Paint(drawPaint);
                break;
            case MotionEvent.ACTION_MOVE:
                if (currentPath != null) {
                    currentPath.lineTo(touchX, touchY);
                }
                break;
            case MotionEvent.ACTION_UP:
                if (currentPath != null) {
                    strokes.add(new Stroke(currentPath, currentPaint));
                    currentPath = null;
                }
                break;
            default:
                return false;
        }
        invalidate();
        return true;
    }

    public void setColor(int color) {
        paintColor = color;
        drawPaint.setColor(paintColor);
    }

    public void setStrokeWidth(float width) {
        strokeWidth = width;
        drawPaint.setStrokeWidth(strokeWidth);
    }

    public void clear() {
        strokes.clear();
        invalidate();
    }

    public Bitmap getBitmap() {
        if (bitmap == null) return null;
        Canvas canvas = new Canvas(bitmap);
        draw(canvas);
        return bitmap;
    }
}

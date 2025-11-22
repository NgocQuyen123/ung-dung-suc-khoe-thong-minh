package admin.example.ungdungsuckhoethongminh.view;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;
import android.graphics.SweepGradient;
import android.util.AttributeSet;
import android.view.View;

public class HalfCircleView extends View {

    private Paint arcPaint;
    private Paint bgPaint;
    private int progress = 39;

    public HalfCircleView(Context context) {
        super(context);
        init();
    }

    public HalfCircleView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public HalfCircleView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private void init() {
        arcPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        arcPaint.setStyle(Paint.Style.STROKE);
        arcPaint.setStrokeWidth(26f);
        arcPaint.setStrokeCap(Paint.Cap.ROUND);

        bgPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        bgPaint.setStyle(Paint.Style.STROKE);
        bgPaint.setStrokeWidth(26f);
        bgPaint.setColor(Color.parseColor("#E0E0E0"));
        bgPaint.setStrokeCap(Paint.Cap.ROUND);
    }

    public void setProgress(int value) {
        progress = Math.max(0, Math.min(value, 100));
        invalidate();
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        float padding = 16f;
        float w = getWidth();
        float h = getHeight();
        float size = Math.min(w, h);

        RectF rect = new RectF(
                padding,
                padding,
                size - padding,
                size - padding
        );

        // Vẽ vòng cung nền màu nhạt
        bgPaint.setColor(Color.parseColor("#E0E0E0")); // màu nhạt
        canvas.drawArc(rect, 180, 180, false, bgPaint);

        // Gradient màu đỏ cho vòng cung tiến độ
        SweepGradient gradient = new SweepGradient(
                size / 2,
                size / 2,
                new int[]{
                        Color.parseColor("#FF4A4A"),
                        Color.parseColor("#FF6A6A"),
                        Color.TRANSPARENT
                },
                new float[]{
                        0f,
                        progress / 100f,
                        progress / 100f + 0.001f
                }
        );

        arcPaint.setShader(gradient);

        // Xoay canvas 180 độ để gradient bắt đầu ở bên trái
        canvas.save();
        canvas.rotate(180, size / 2, size / 2);

        // Vẽ vòng cung màu đỏ theo % tiến độ
        canvas.drawArc(rect, 0, 180 * (progress / 100f), false, arcPaint);

        canvas.restore();
    }

}

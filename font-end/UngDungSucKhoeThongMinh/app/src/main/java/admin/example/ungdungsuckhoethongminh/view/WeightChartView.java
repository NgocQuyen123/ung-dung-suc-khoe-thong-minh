package admin.example.ungdungsuckhoethongminh.view;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.DashPathEffect;
import android.graphics.Paint;
import android.graphics.Path;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.View;

import androidx.annotation.Nullable;

import java.util.ArrayList;
import java.util.List;

public class WeightChartView extends View {
    private List<Double> values;       // có thể chứa null
    private List<String> labels;       // nhãn X tương ứng
    private Paint linePaint, pointPaint, textPaint, verticalPaint, axisPaint;
    private Path path = new Path();

    public WeightChartView(Context context) { super(context); init(); }
    public WeightChartView(Context context, @Nullable AttributeSet attrs) { super(context, attrs); init(); }

    private void init() {
        linePaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        linePaint.setColor(Color.parseColor("#999999")); // đường nối
        linePaint.setStrokeWidth(dp(2));
        linePaint.setStyle(Paint.Style.STROKE);

        pointPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        pointPaint.setColor(Color.parseColor("#27AE60"));
        pointPaint.setStyle(Paint.Style.FILL);

        verticalPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        verticalPaint.setColor(Color.parseColor("#DDDDDD")); // nền dọc, nhẹ
        verticalPaint.setStrokeWidth(dp(1));
        verticalPaint.setStyle(Paint.Style.STROKE);
//        verticalPaint.setPathEffect(new DashPathEffect(new float[]{5,5}, 0));

        axisPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        axisPaint.setColor(Color.parseColor("#CFD8DC"));
        axisPaint.setStrokeWidth(dp(1));

        textPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        textPaint.setColor(Color.DKGRAY);
        textPaint.setTextSize(sp(12));
        textPaint.setTextAlign(Paint.Align.CENTER);
    }

    private float dp(float d) { return TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, d, getResources().getDisplayMetrics()); }
    private float sp(float s) { return TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_SP, s, getResources().getDisplayMetrics()); }

    public void setData(List<Double> values, List<String> labels) {
        this.values = values == null ? new ArrayList<>() : new ArrayList<>(values);
        this.labels = labels == null ? new ArrayList<>() : new ArrayList<>(labels);
        invalidate();
    }

//    @Override
//    protected void onDraw(Canvas canvas) {
//        super.onDraw(canvas);
//        if (labels == null || labels.isEmpty()) return;
//
//        int w = getWidth();
//        int h = getHeight();
//
//        int n = labels.size();
//        float leftPadding = dp(8);
//        float rightPadding = dp(8);
//        float topPadding = dp(16);
//        float bottomPadding = dp(32);
//        float usableW = w - leftPadding - rightPadding;
//        float usableH = h - topPadding - bottomPadding;
//
//        // draw vertical stripes/lines at each label
//        float stepX = usableW / (float) n;
//        for (int i = 0; i < n; i++) {
//            float x = leftPadding + stepX * i + stepX/2f;
//            canvas.drawLine(x, topPadding, x, topPadding + usableH, verticalPaint);
//        }
//
//        // compute min/max from values (ignore null)
//        double max = Double.NEGATIVE_INFINITY;
//        double min = Double.POSITIVE_INFINITY;
//        boolean anyValue = false;
//        if (values != null) {
//            for (Double v : values) {
//                if (v != null) {
//                    anyValue = true;
//                    if (v > max) max = v;
//                    if (v < min) min = v;
//                }
//            }
//        }
//        if (!anyValue) {
//            // default scale
//            max = 100;
//            min = 0;
//        } else if (max == min) {
//            // avoid div0
//            max = max + 1;
//            min = min - 1;
//        }
//
//        // build path (smoothed using quadTo between midpoints)
//        path.reset();
//        Float prevX = null, prevY = null;
//        Integer prevIndex = null;
//        // gather all drawn points to draw circles and labels later
//        List<Float> drawnX = new ArrayList<>();
//        List<Float> drawnY = new ArrayList<>();
//        List<Double> drawnVal = new ArrayList<>();
//
//        for (int i = 0; i < n; i++) {
//            Double v = (values != null && i < values.size()) ? values.get(i) : null;
//            float x = leftPadding + stepX * i + stepX/2f;
//            float y;
//            if (v == null) {
//                y = topPadding + usableH; // put off bottom if null (not used)
//            } else {
//                // map v to y (higher value -> smaller y)
//                y = (float) (topPadding + (max - v) / (max - min) * usableH);
//            }
//
//            if (v != null) {
//                drawnX.add(x);
//                drawnY.add(y);
//                drawnVal.add(v);
//            }
//
//            if (v != null) {
//                if (prevX == null) {
//                    path.moveTo(x, y);
//                    prevX = x; prevY = y; prevIndex = i;
//                } else {
//                    // if previous valid exists and contiguous (there might be gaps), create a smooth quad
//                    if (prevIndex != null) {
//                        float midX = (prevX + x) / 2f;
//                        float midY = (prevY + y) / 2f;
//                        path.quadTo(prevX, prevY, midX, midY);
//                        // continue with last segment: move prev to mid, next iteration will quad from mid to next
//                        path.lineTo(x, y); // ensure connect to actual point; keep simple
//                    } else {
//                        path.moveTo(x, y);
//                    }
//                    prevX = x; prevY = y; prevIndex = i;
//                }
//            } else {
//                // gap -> break path
//                prevX = prevY = null;
//                prevIndex = null;
//            }
//        }
//
//        // draw the path
//        canvas.drawPath(path, linePaint);
//
//        // draw points and value labels
//        for (int i = 0; i < drawnX.size(); i++) {
//            float x = drawnX.get(i);
//            float y = drawnY.get(i);
//            Double v = drawnVal.get(i);
//            // circle
//            canvas.drawCircle(x, y, dp(5), pointPaint);
//            // white bubble background for number
//            Paint bubble = new Paint(Paint.ANTI_ALIAS_FLAG);
//            bubble.setColor(Color.WHITE);
//            float textY = y - dp(12);
//            // little rounded rect not necessary; draw text with slight background circle
//            canvas.drawCircle(x, textY - dp(6), dp(12), bubble);
//            // number
//            textPaint.setColor(Color.BLACK);
//            textPaint.setTextSize(sp(11));
//            canvas.drawText(String.format("%.0f", v), x, textY - dp(2), textPaint);
//        }
//
//    }
@Override
protected void onDraw(Canvas canvas) {
    super.onDraw(canvas);
    if (labels == null || labels.isEmpty()) return;

    int w = getWidth();
    int h = getHeight();
    int n = labels.size();

    float leftPadding = dp(8);
    float rightPadding = dp(8);
    float topPadding = dp(35); // Padding cao để không mất bong bóng số
    float bottomPadding = dp(8);

    float usableW = w - leftPadding - rightPadding;
    float usableH = h - topPadding - bottomPadding;

    // THANG ĐO CỐ ĐỊNH 0 - 150kg
    float minY = 0f;
    float maxY = 150f;
    float stepX = usableW / (float) n;

    // 1. Vẽ lưới dọc (Gridlines)
    for (int i = 0; i < n; i++) {
        float x = leftPadding + stepX * i + stepX/2f;
        canvas.drawLine(x, topPadding, x, topPadding + usableH, verticalPaint);
    }

    // 2. LOGIC NỐI ĐIỂM THÔNG MINH
    path.reset();
    boolean isFirstPoint = true;

    for (int i = 0; i < n; i++) {
        Double v = (values != null && i < values.size()) ? values.get(i) : null;

        // QUAN TRỌNG: Nếu gặp điểm null, ta bỏ qua hoàn toàn,
        // không ngắt Path, không moveTo điểm mới.
        if (v == null) continue;

        float x = leftPadding + stepX * i + stepX/2f;
        float y = (float) (topPadding + (maxY - v) / (maxY - minY) * usableH);

        if (isFirstPoint) {
            path.moveTo(x, y);
            isFirstPoint = false;
        } else {
            // Nối trực tiếp từ điểm có dữ liệu cuối cùng đến điểm hiện tại
            path.lineTo(x, y);
        }
    }
    canvas.drawPath(path, linePaint);

    // --- VẼ ĐIỂM CHẤM VÀ CHỮ SỐ ---
    for (int i = 0; i < n; i++) {
        Double v = (values != null && i < values.size()) ? values.get(i) : null;
        if (v == null) continue;

        float x = leftPadding + stepX * i + stepX/2f;
        float y = (float) (topPadding + (maxY - v) / (maxY - minY) * usableH);

        canvas.drawCircle(x, y, dp(5), pointPaint);

        // Bong bóng nền trắng cho số
        Paint bubblePaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        bubblePaint.setColor(Color.WHITE);
        canvas.drawCircle(x, y - dp(20), dp(12), bubblePaint);

        // Hiển thị số cân nặng (1 chữ số thập phân)
        textPaint.setColor(Color.BLACK);
        textPaint.setTextSize(sp(10));
        textPaint.setFakeBoldText(true);
        canvas.drawText(String.format("%.1f", v), x, y - dp(16), textPaint);
    }
}
}

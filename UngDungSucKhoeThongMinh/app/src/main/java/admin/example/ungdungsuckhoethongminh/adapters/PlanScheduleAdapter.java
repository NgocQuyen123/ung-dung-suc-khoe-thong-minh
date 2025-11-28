package admin.example.ungdungsuckhoethongminh.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import admin.example.ungdungsuckhoethongminh.R;
import admin.example.ungdungsuckhoethongminh.model.PlanScheduleModel;

public class PlanScheduleAdapter extends RecyclerView.Adapter<PlanScheduleAdapter.ViewHolder> {

    private final List<PlanScheduleModel> list;

    public PlanScheduleAdapter(List<PlanScheduleModel> list) {
        this.list = list;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_plan_schedule, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        PlanScheduleModel item = list.get(position);

        // GÁN DỮ LIỆU
        holder.tvTime.setText(item.time);
        holder.tvWorkoutName.setText(item.workoutName);
        holder.progressBar.setProgress(item.progress);
        holder.tvProgress.setText(item.progress + "%");

        // Nút Chỉnh sửa
        holder.btnEdit.setOnClickListener(v ->
                System.out.println("Chỉnh sửa: " + item.workoutName)
        );

        // Nút Hoàn thành
        holder.btnMarkDone.setOnClickListener(v ->
                System.out.println("Đánh dấu hoàn thành: " + item.workoutName)
        );
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    static class ViewHolder extends RecyclerView.ViewHolder {

        TextView tvTime, tvWorkoutName, tvProgress;
        ProgressBar progressBar;
        Button btnEdit, btnMarkDone;
        CardView cardSchedule;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            // MATCH với id trong layout
            tvTime = itemView.findViewById(R.id.tvTime);
            cardSchedule = itemView.findViewById(R.id.cardSchedule);
            tvWorkoutName = itemView.findViewById(R.id.tvWorkoutName);
            progressBar = itemView.findViewById(R.id.progressBar);
            tvProgress = itemView.findViewById(R.id.tvProgress);
            btnEdit = itemView.findViewById(R.id.btnEdit);
            btnMarkDone = itemView.findViewById(R.id.btnMarkDone);
        }
    }
}

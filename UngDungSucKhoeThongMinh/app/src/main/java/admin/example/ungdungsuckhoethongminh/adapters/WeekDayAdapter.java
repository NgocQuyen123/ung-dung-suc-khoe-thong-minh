package admin.example.ungdungsuckhoethongminh.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import admin.example.ungdungsuckhoethongminh.R;
import admin.example.ungdungsuckhoethongminh.model.WeekDay;

public class WeekDayAdapter extends RecyclerView.Adapter<WeekDayAdapter.DayViewHolder> {

    private Context context;
    private List<WeekDay> list;

    public WeekDayAdapter(Context context, List<WeekDay> list) {
        this.context = context;
        this.list = list;
    }

    @NonNull
    @Override
    public DayViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.demo_item_week_day, parent, false);
        return new DayViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull DayViewHolder holder, int position) {
        WeekDay day = list.get(position);
        holder.tvDayName.setText(day.getName());
        holder.tvDayNumber.setText(String.valueOf(day.getDayNumber()));
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public static class DayViewHolder extends RecyclerView.ViewHolder {

        TextView tvDayName, tvDayNumber;
        CardView cardDay;

        public DayViewHolder(@NonNull View itemView) {
            super(itemView);
            tvDayName = itemView.findViewById(R.id.tvDayName);
            tvDayNumber = itemView.findViewById(R.id.tvDayNumber);
            cardDay = itemView.findViewById(R.id.cardDay);
        }
    }
}

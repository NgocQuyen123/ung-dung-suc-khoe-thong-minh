package admin.example.ungdungsuckhoethongminh.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import admin.example.ungdungsuckhoethongminh.R;
import admin.example.ungdungsuckhoethongminh.model.HealthItemModel;

public class HealthPagerAdapter extends RecyclerView.Adapter<HealthPagerAdapter.HealthViewHolder> {
    private List<HealthItemModel> items;
    private OnItemClickListener listener;

    public HealthPagerAdapter(List<HealthItemModel> items, OnItemClickListener listener) {
        this.items = items;
        this.listener = listener;
    }

    @NonNull
    @Override
    public HealthViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_health_card, parent, false);
        return new HealthViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull HealthViewHolder holder, int position) {
        HealthItemModel item = items.get(position);
        holder.tvTitle.setText(item.getTitle());
        holder.tvSubtitle.setText(item.getSubtitle());
        holder.icon.setImageResource(item.getIconRes());

        // Gán click listener cho item
        holder.itemView.setOnClickListener(v -> {
            if (listener != null) {
                listener.onItemClick(position);
            }
        });
    }

    @Override
    public int getItemCount() {
        return items.size();
    }

    static class HealthViewHolder extends RecyclerView.ViewHolder {
        ImageView icon;
        TextView tvTitle, tvSubtitle;

        public HealthViewHolder(@NonNull View itemView) {
            super(itemView);
            icon = itemView.findViewById(R.id.icon);
            tvTitle = itemView.findViewById(R.id.tvTitle);
            tvSubtitle = itemView.findViewById(R.id.tvSubtitle);
        }
    }

    // Interface để callback sự kiện click
    public interface OnItemClickListener {
        void onItemClick(int position);
    }
}


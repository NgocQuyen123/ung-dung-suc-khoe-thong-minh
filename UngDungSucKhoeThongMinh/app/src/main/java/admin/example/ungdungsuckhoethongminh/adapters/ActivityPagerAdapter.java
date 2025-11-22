package admin.example.ungdungsuckhoethongminh.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import admin.example.ungdungsuckhoethongminh.R;
import admin.example.ungdungsuckhoethongminh.model.ActivityLever;

public class ActivityPagerAdapter extends RecyclerView.Adapter<ActivityPagerAdapter.VH> {
    private List<ActivityLever> items;
    private int selectedId = -1;
    private final OnItemClick listener;

    public interface OnItemClick { void onClick(ActivityLever item); }

    public ActivityPagerAdapter(List<ActivityLever> items, OnItemClick listener) {
        this.items = items;
        this.listener = listener;
    }

    public int getSelectedId() {
        return selectedId;
    }
    public void setSelectedId(int id) {
        int oldIndex = getIndexById(selectedId);
        selectedId = id;
        int newIndex = getIndexById(selectedId);

        if (oldIndex != -1) notifyItemChanged(oldIndex);
        if (newIndex != -1) notifyItemChanged(newIndex);
        if (oldIndex == -1 && newIndex == -1) notifyDataSetChanged();
    }

    private int getIndexById(int id) {
        for (int i = 0; i < items.size(); i++) {
            if (items.get(i).id == id) return i;
        }
        return -1;
    }

    @NonNull
    @Override
    public VH onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_weight_activity_level, parent, false);
        return new VH(v);
    }

    @Override
    public void onBindViewHolder(@NonNull VH holder, int position) {
        ActivityLever currentItem = items.get(position);
        boolean isSelected = selectedId == currentItem.id;

        // Đặt Text
        holder.tvActivityName.setText(currentItem.ten);
        holder.tvActivityDescription.setText(currentItem.moTa);

        // Đặt Icon
        holder.ivActivityIcon.setImageResource(getIconResId(currentItem.id));

        // Đặt trạng thái chọn (RadioButton và Viền)
        holder.rbActivitySelector.setChecked(isSelected);
        holder.clItemContainer.setActivated(isSelected);

        // Xử lý click
        holder.clItemContainer.setOnClickListener(v -> {
            listener.onClick(currentItem);
            setSelectedId(currentItem.id);
        });
    }

    @Override public int getItemCount() { return items.size(); }

    private int getIconResId(int activityId) {
        switch (activityId) {
            case 1: return R.drawable.active;
            case 2: return R.drawable.lifting;
            case 3: return R.drawable.walking;
            case 4: return R.drawable.sitting;
            default: return R.drawable.active;
        }
    }

    static class VH extends RecyclerView.ViewHolder {
        final ConstraintLayout clItemContainer;
        final ImageView ivActivityIcon;
        final TextView tvActivityName;
        final TextView tvActivityDescription;
        final RadioButton rbActivitySelector;

        VH(@NonNull View itemView) {
            super(itemView);
            clItemContainer = itemView.findViewById(R.id.clItemContainer);
            ivActivityIcon = itemView.findViewById(R.id.ivActivityIcon);
            tvActivityName = itemView.findViewById(R.id.tvActivityName);
            tvActivityDescription = itemView.findViewById(R.id.tvActivityDescription);
            rbActivitySelector = itemView.findViewById(R.id.rbActivitySelector);
        }
    }
}
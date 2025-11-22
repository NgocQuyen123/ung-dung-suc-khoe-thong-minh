package admin.example.ungdungsuckhoethongminh.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import admin.example.ungdungsuckhoethongminh.R;
import admin.example.ungdungsuckhoethongminh.model.SpeedWeight;

public class PacePagerAdapter extends RecyclerView.Adapter<PacePagerAdapter.VH>{
    private List<SpeedWeight> items;
    private String selectedId = null;
    private final OnItemClick listener;
    private final boolean isLosingWeight;


    public interface OnItemClick { void onClick(SpeedWeight item); }

    public String getSelectedId() {
        return selectedId;
    }
    public PacePagerAdapter(List<SpeedWeight> items, OnItemClick listener, boolean isLosingWeight) {
        this.items = items;
        this.listener = listener;
        this.isLosingWeight = isLosingWeight;
    }

    public void setSelectedId(String id) {
        int oldIndex = getIndexById(selectedId);
        selectedId = id;
        int newIndex = getIndexById(selectedId);

        if (oldIndex != -1) notifyItemChanged(oldIndex);
        if (newIndex != -1) notifyItemChanged(newIndex);
        if (oldIndex == -1 && newIndex == -1) notifyDataSetChanged();
    }

    private int getIndexById(String id) {
        if (id == null) return -1;
        for (int i = 0; i < items.size(); i++) {
            if (items.get(i).id.equals(id)) return i;
        }
        return -1;
    }

    @NonNull
    @Override
    public VH onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_pace_card, parent, false);
        return new VH(v);
    }

    @Override
    public void onBindViewHolder(@NonNull VH holder, int position) {
        SpeedWeight t = items.get(position);
        boolean isSelected = selectedId != null && selectedId.equals(t.id);
        Context context = holder.itemView.getContext();

        // 1. LOGIC XỬ LÝ DẤU (+/-)
        String paceValue = String.valueOf(t.tocDoKgTuan);
        if (isLosingWeight) {
            paceValue = "-" + paceValue;
        } else {
            paceValue = "+" + paceValue;
        }

        // 2. Cập nhật Text
        holder.tvPaceName.setText(t.ten);
        holder.tvPaceRate.setText(paceValue + " kg/tuần");

        holder.clPaceContainer.setActivated(isSelected);
        holder.clPaceNameWrapper.setActivated(isSelected);
        if (isSelected) {
            holder.tvPaceName.setTextColor(context.getResources().getColor(android.R.color.white));
        } else {
            holder.tvPaceName.setTextColor(context.getResources().getColor(android.R.color.black));
        }

        // 4. Xử lý click
        holder.clPaceContainer.setOnClickListener(v -> {
            listener.onClick(t);
            setSelectedId(t.id);
        });

    }

    @Override public int getItemCount() { return items.size(); }

    static class VH extends RecyclerView.ViewHolder {
        final ConstraintLayout clPaceContainer;
        final ConstraintLayout clPaceNameWrapper;
        final TextView tvPaceName;
        final TextView tvPaceRate;

        VH(@NonNull View itemView) {
            super(itemView);
            // Ánh xạ View mới
            clPaceContainer = itemView.findViewById(R.id.clPaceContainer);
            clPaceNameWrapper = itemView.findViewById(R.id.clPaceNameWrapper);
            tvPaceName = itemView.findViewById(R.id.tvPaceName);
            tvPaceRate = itemView.findViewById(R.id.tvPaceRate);

        }
    }
}
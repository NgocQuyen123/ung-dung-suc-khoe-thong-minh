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
import admin.example.ungdungsuckhoethongminh.model.LeverModel;
import admin.example.ungdungsuckhoethongminh.model.MucDoHoatDongModel;

public class ActivityPagerAdapter extends RecyclerView.Adapter<ActivityPagerAdapter.VH> {
    private List<MucDoHoatDongModel> items;
    private int selectedId = -1;
    private final OnItemClick listener;

    public interface OnItemClick { void onClick(MucDoHoatDongModel item); }

    public ActivityPagerAdapter(List<MucDoHoatDongModel> items, OnItemClick listener) {
        this.items = items;
        this.listener = listener;
    }

    @NonNull
    @Override
    public VH onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_weight_activity_level, parent, false);
        return new VH(v);
    }

    @Override
    public void onBindViewHolder(@NonNull VH holder, int position) {
        MucDoHoatDongModel currentItem = items.get(position);
        boolean isSelected = selectedId == currentItem.getId();

        holder.tvActivityName.setText(currentItem.getTenMDHD());
        holder.tvActivityDescription.setText(currentItem.getMoTa());

        holder.rbActivitySelector.setChecked(isSelected);
        holder.clItemContainer.setActivated(isSelected);

        holder.clItemContainer.setOnClickListener(v -> {
            listener.onClick(currentItem);
            setSelectedId(currentItem.getId());
        });
    }

    @Override public int getItemCount() { return items.size(); }

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
            if (items.get(i).getId() == id) return i;
        }
        return -1;
    }

    static class VH extends RecyclerView.ViewHolder {
        final ConstraintLayout clItemContainer;
        final TextView tvActivityName;
        final TextView tvActivityDescription;
        final RadioButton rbActivitySelector;

        VH(@NonNull View itemView) {
            super(itemView);
            clItemContainer = itemView.findViewById(R.id.clItemContainer);
            tvActivityName = itemView.findViewById(R.id.tvActivityName);
            tvActivityDescription = itemView.findViewById(R.id.tvActivityDescription);
            rbActivitySelector = itemView.findViewById(R.id.rbActivitySelector);
        }
    }
}
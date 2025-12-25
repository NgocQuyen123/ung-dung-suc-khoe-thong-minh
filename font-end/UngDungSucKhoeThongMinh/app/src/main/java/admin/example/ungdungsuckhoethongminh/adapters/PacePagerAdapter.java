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
import admin.example.ungdungsuckhoethongminh.model.NhipDoCanNangModel;
import admin.example.ungdungsuckhoethongminh.model.SpeedWeightModel;

public class PacePagerAdapter extends RecyclerView.Adapter<PacePagerAdapter.VH>{
    private List<NhipDoCanNangModel> items;
    private String selectedId = null;
    private final OnItemClick listener;
    private final boolean isLosingWeight;

    public interface OnItemClick { void onClick(NhipDoCanNangModel item); }

    public String getSelectedId() {
        return selectedId;
    }

    public PacePagerAdapter(List<NhipDoCanNangModel> items, OnItemClick listener, boolean isLosingWeight) {
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
            if (items.get(i).getId().toString().equals(id)) return i;
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
        NhipDoCanNangModel item = items.get(position);
        boolean isSelected = selectedId != null && selectedId.equals(item.getId().toString());
        Context context = holder.itemView.getContext();

        // Hiển thị tốc độ
        String paceValue = String.valueOf(item.getTocDoKgTuan());
        if (isLosingWeight) {
            paceValue = "-" + paceValue;
        } else {
            paceValue = "+" + paceValue;
        }

        holder.tvPaceName.setText(item.getTenNDCD());
        holder.tvPaceRate.setText(paceValue  + " kg/tuần");

        // Hiệu ứng chọn
        holder.clPaceContainer.setActivated(isSelected);
        holder.clPaceNameWrapper.setActivated(isSelected);
        if (isSelected) {
            holder.tvPaceName.setTextColor(context.getResources().getColor(android.R.color.white));
        } else {
            holder.tvPaceName.setTextColor(context.getResources().getColor(android.R.color.black));
        }

        // Xử lý click
        holder.clPaceContainer.setOnClickListener(v -> {
            listener.onClick(item);
            setSelectedId(item.getId().toString());
        });
    }

    @Override
    public int getItemCount() {
        return items.size();
    }

    static class VH extends RecyclerView.ViewHolder {
        final ConstraintLayout clPaceContainer;
        final ConstraintLayout clPaceNameWrapper;
        final TextView tvPaceName;
        final TextView tvPaceRate;

        VH(@NonNull View itemView) {
            super(itemView);
            clPaceContainer = itemView.findViewById(R.id.clPaceContainer);
            clPaceNameWrapper = itemView.findViewById(R.id.clPaceNameWrapper);
            tvPaceName = itemView.findViewById(R.id.tvPaceName);
            tvPaceRate = itemView.findViewById(R.id.tvPaceRate);
        }
    }
}
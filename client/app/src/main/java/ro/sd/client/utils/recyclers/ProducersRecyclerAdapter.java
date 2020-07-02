package ro.sd.client.utils.recyclers;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import ro.sd.client.R;
import ro.sd.client.dto.ProducersDTO;

public class ProducersRecyclerAdapter extends RecyclerView.Adapter<ProducersRecyclerAdapter.RecycleViewHolder> {

    private ProducersDTO data;

    public ProducersRecyclerAdapter(ProducersDTO producersDTO){
        this.data = producersDTO;
    }

    @NonNull
    @Override
    public RecycleViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(viewGroup.getContext());
        View recyclerRow = layoutInflater.inflate(R.layout.producer, viewGroup, false);
        return new RecycleViewHolder(recyclerRow);
    }

    @Override
    public void onBindViewHolder(@NonNull RecycleViewHolder recycleViewHolder, final int position) {
        TextView textView = recycleViewHolder.producersTextView;
        textView.setText(data.getProducerDTOs().get(position).getName());
    }

    @Override
    public int getItemCount() {
        return data.getProducerDTOs().size();
    }

    static class RecycleViewHolder extends RecyclerView.ViewHolder {

        final TextView producersTextView;

        RecycleViewHolder(@NonNull View itemView) {
            super(itemView);
            this.producersTextView = itemView.findViewById(R.id.producer_name);
        }
    }
}

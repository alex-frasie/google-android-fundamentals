package ro.sd.client.utils.recyclers;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import ro.sd.client.R;
import ro.sd.client.dto.OrdersDTO;

public class OrdersRecyclerAdapter extends RecyclerView.Adapter<OrdersRecyclerAdapter.RecycleViewHolder> {

    private OrdersDTO data;

    public OrdersRecyclerAdapter(OrdersDTO ordersDTO){
        this.data = ordersDTO;
    }

    @NonNull
    @Override
    public OrdersRecyclerAdapter.RecycleViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View recyclerRow = layoutInflater.inflate(R.layout.order, parent, false);
        return new RecycleViewHolder(recyclerRow);
    }

    @Override
    public void onBindViewHolder(@NonNull RecycleViewHolder holder, int position) {
        TextView textView = holder.tvDescription;
        textView.setText(data.getOrderDTOs().get(position).getDescription());

        TextView textView2 = holder.tvDate;
        textView2.setText(data.getOrderDTOs().get(position).getPlacementDate());

        TextView textView3 = holder.tvPrice;
        textView3.setText(String.valueOf(data.getOrderDTOs().get(position).getTotalAmount()) + "RON");
    }

    @Override
    public int getItemCount() {
        return data.getOrderDTOs().size();
    }


    static class RecycleViewHolder extends RecyclerView.ViewHolder {

        final TextView tvDescription;
        final TextView tvPrice;
        final TextView tvDate;

        RecycleViewHolder(@NonNull View itemView) {
            super(itemView);
            this.tvDescription = itemView.findViewById(R.id.order_description);
            this.tvPrice = itemView.findViewById(R.id.order_price);
            this.tvDate = itemView.findViewById(R.id.order_date);
        }
    }
}

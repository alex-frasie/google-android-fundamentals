package ro.sd.client.utils.recyclers;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import ro.sd.client.R;
import ro.sd.client.dto.CarPartsDTO;
import ro.sd.client.views.admin.SeeProducts;
import ro.sd.client.views.customer.SeeProductsCustomer;

public class CarPartsRecyclerAdapter extends RecyclerView.Adapter<CarPartsRecyclerAdapter.RecycleViewHolder> {

    private final static String TAG = CarPartsRecyclerAdapter.class.getSimpleName();
    private CarPartsDTO data;

    public CarPartsRecyclerAdapter(CarPartsDTO carPartsDTO) {
        this.data = carPartsDTO;
    }

    @NonNull
    @Override
    public RecycleViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int position) {
        LayoutInflater layoutInflater = LayoutInflater.from(viewGroup.getContext());
        View recyclerRow = layoutInflater.inflate(R.layout.product, viewGroup, false);
        recyclerRow.setOnClickListener(SeeProducts.myOnClickListener);
        recyclerRow.setOnClickListener(SeeProductsCustomer.myOnClickListener);
        return new RecycleViewHolder(recyclerRow);
    }

    @Override
    public void onBindViewHolder(@NonNull RecycleViewHolder recycleViewHolder, final int position) {
        TextView textView = recycleViewHolder.carNameTextView;
        textView.setText(data.getCarPartDTOS().get(position).getName());

        TextView textView1 = recycleViewHolder.priceTextView;
        textView1.setText(String.valueOf(data.getCarPartDTOS().get(position).getPrice()));
    }

    @Override
    public int getItemCount() {
        return data.getCarPartDTOS().size();
    }

    static class RecycleViewHolder extends RecyclerView.ViewHolder {

        final TextView carNameTextView;
        final TextView priceTextView;

        RecycleViewHolder(@NonNull View itemView) {
            super(itemView);
            this.carNameTextView = itemView.findViewById(R.id.car_part_name);
            this.priceTextView = itemView.findViewById(R.id.price);
        }
    }
}

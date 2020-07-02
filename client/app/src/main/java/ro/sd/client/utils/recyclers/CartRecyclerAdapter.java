package ro.sd.client.utils.recyclers;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.google.gson.Gson;
import com.google.gson.TypeAdapter;

import java.io.IOException;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import ro.sd.client.R;
import ro.sd.client.dto.CarPartCartDTO;
import ro.sd.client.dto.CartProductsDTO;
import ro.sd.client.dto.ResponseDTO;
import ro.sd.client.utils.Validator;
import ro.sd.client.utils.retrofit.RetrofitClientInstance;
import ro.sd.client.views.customer.CartCustomer;

public class CartRecyclerAdapter extends RecyclerView.Adapter<CartRecyclerAdapter.RecycleViewHolder> {

    private CartProductsDTO data;

    private Validator validator = new Validator();
    private Context context;

    public CartRecyclerAdapter(CartProductsDTO cartProductsDTO, Context context){
        this.data = cartProductsDTO;
        this.context = context;
    }

    @NonNull
    @Override
    public RecycleViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View recyclerRow = layoutInflater.inflate(R.layout.item, parent, false);
        return new RecycleViewHolder(recyclerRow);
    }

    @Override
    public void onBindViewHolder(@NonNull RecycleViewHolder holder, final int position) {
        final TextView textView = holder.itemName;
        textView.setText(data.getCarPartCartDTOs().get(position).getName());

        TextView textView2 = holder.itemPrice;
        textView2.setText(String.valueOf(data.getCarPartCartDTOs().get(position).getPrice()) + " RON");

        TextView textView3 = holder.itemQuantity;
        textView3.setText("Quantity: " + String.valueOf(data.getCarPartCartDTOs().get(position).getQuantity()));

        Button btnRemove = holder.remove;

        btnRemove.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                CarPartCartDTO carPartCartDTO = new CarPartCartDTO();
                carPartCartDTO.setName(data.getCarPartCartDTOs().get(position).getName());

                Call<ResponseDTO> call = RetrofitClientInstance
                        .getInstance()
                        .getApi()
                        .deleteFromCart(carPartCartDTO);

                call.enqueue(new Callback<ResponseDTO>() {
                    @Override
                    public void onResponse(Call<ResponseDTO> call, Response<ResponseDTO> response) {
                        Log.d("DELETED", String.valueOf(response.code()));
                        if (response.isSuccessful()) {
                            Log.d("DELETED", "Success");
                            validator.validate(response.body(), context);

                            Intent intent = new Intent(context, CartCustomer.class);
                            context.startActivity(intent);

                        } else {
                            if (response.code() >= 400) {
                                ResponseDTO responseDTO = new ResponseDTO();
                                Log.d("Login", "onResponse - Status : " + response.code());
                                Gson gson = new Gson();
                                TypeAdapter<ResponseDTO> adapter = gson.getAdapter(ResponseDTO.class);
                                try {
                                    if (response.errorBody() != null)
                                        responseDTO =
                                                adapter.fromJson(
                                                        response.errorBody().string());
                                } catch (IOException e) {
                                    e.printStackTrace();
                                }

                                validator.validate(responseDTO, context);
                            }
                        }
                    }

                    @Override
                    public void onFailure(Call<ResponseDTO> call, Throwable t) {
                        Log.d("DELETED", "Fail");
                    }
                });

            }
        });
    }

    @Override
    public int getItemCount() {
        return data.getCarPartCartDTOs().size();
    }

    static class RecycleViewHolder extends RecyclerView.ViewHolder {

        final TextView itemName;
        final TextView itemPrice;
        final TextView itemQuantity;
        Button remove;

        RecycleViewHolder(@NonNull View itemView) {
            super(itemView);
            this.itemName = itemView.findViewById(R.id.cart_item_name);
            this.itemPrice = itemView.findViewById(R.id.cart_item_price);
            this.itemQuantity = itemView.findViewById(R.id.cart_item_quantity);
            this.remove = itemView.findViewById(R.id.btnItemRemove);
        }
    }
}

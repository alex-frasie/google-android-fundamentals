package ro.sd.client.utils.retrofit;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import ro.sd.client.dto.CarPartCartDTO;
import ro.sd.client.dto.CartProductsDTO;
import ro.sd.client.dto.LoginDTO;
import ro.sd.client.dto.OrdersDTO;
import ro.sd.client.dto.PasswordResetDTO;
import ro.sd.client.dto.RegisterDTO;
import ro.sd.client.dto.ResponseDTO;

public interface EndpointsAPI {

    @POST("loginCustomer")
    Call<ResponseDTO> loginCustomer(@Body LoginDTO loginDTO);

    @POST("register")
    Call<ResponseDTO> registerCustomer(@Body RegisterDTO registerDTO);

    @POST("customer/addToCart")
    Call<ResponseDTO> addToCart(@Body CarPartCartDTO carPartCartDTO);

    @GET("customer/orders")
    Call<OrdersDTO> getOrders();

    @GET("customer/cart")
    Call<CartProductsDTO> getCart();

    @POST("customer/deleteCart")
    Call<ResponseDTO> deleteFromCart(@Body CarPartCartDTO carPartCartDTO);

    @POST("customer/checkout")
    Call<ResponseDTO> computeOrder();

    @POST("reset")
    Call<ResponseDTO> resetPassword(@Body PasswordResetDTO passwordResetDTO);
}

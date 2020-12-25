package com.ali.ssb;

import android.content.SharedPreferences;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.ali.ssb.Models.modelcart;
import com.ali.ssb.Models.modelreturnoforderinfo;
import com.ali.ssb.interfacesapi.orderinfoapi;
import com.ali.ssb.interfacesapi.orderitemapi;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import com.stripe.android.ApiResultCallback;
import com.stripe.android.PaymentIntentResult;
import com.stripe.android.Stripe;
import com.stripe.android.model.ConfirmPaymentIntentParams;
import com.stripe.android.model.PaymentIntent;
import com.stripe.android.model.PaymentMethodCreateParams;
import com.stripe.android.view.CardInputWidget;

import java.io.IOException;
import java.lang.ref.WeakReference;
import java.lang.reflect.Type;
import java.util.List;
import java.util.Map;
import java.util.Objects;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;
import okhttp3.ResponseBody;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

import static com.ali.ssb.Fragments.paymentpage.MY_PREFS_forcart;
import static com.ali.ssb.loginpagecustomer.MY_PREFS_NAME;

public class checkoutpaymentstripe extends AppCompatActivity {
//    SharedPreferences prefs =getSharedPreferences(MY_PREFS_NAME, MODE_PRIVATE);
    private static final String BACKEND_URL = "http://192.168.43.148:3000/";

    int total;
    String grand;
    int d=0;
    String lat,lon,paymentmethodorder,paymentstatusorder;
    String orderid,shopid;
    private OkHttpClient httpClient = new OkHttpClient();
    private String paymentIntentClientSecret;
    private Stripe stripe;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_checkoutpaymentstripe);

        stripe = new Stripe(
                getApplicationContext(),
                Objects.requireNonNull("pk_test_51HTDUvLEXeAwmixgZmjx10jEZNbt5eJiAVpkNdxNBefQGJJZ6YL3YVjggygPKSNRKYhwYlUVyOrxVGKzgpxq1RtU00QLP6r5MB")
        );
        startCheckout();
    }

    private void startCheckout() {
        // Create a PaymentIntent by calling the server's endpoint.

        dbhandler dbhandler=new dbhandler(this);

        total=dbhandler.totalprice();
        if (dbhandler.totalprice()<50){
            total=50;

        }

        MediaType mediaType = MediaType.get("application/json; charset=utf-8");
        String json = "{"
                + "\"currency\":\"PKR\","
                + "\"price\":"+String.valueOf(total)+","
                + "\"items\":["
                + "{\"id\":\"photo_subscription\"}"
                + "]"
                + "}";

        dbhandler.close();


        RequestBody body = RequestBody.create(json, mediaType);
        Request request = new Request.Builder()
                .url(BACKEND_URL + "create-payment-intent")
                .post(body)
                .build();
        httpClient.newCall(request)
                .enqueue(new PayCallback(this));

        // Hook up the pay button to the card widget and stripe instance
        Button payButton = findViewById(R.id.payButton);
        payButton.setOnClickListener((View view) -> {
            CardInputWidget cardInputWidget = findViewById(R.id.cardInputWidgets);
            PaymentMethodCreateParams params = cardInputWidget.getPaymentMethodCreateParams();
            if (params != null) {
                ConfirmPaymentIntentParams confirmParams = ConfirmPaymentIntentParams
                        .createWithPaymentMethodCreateParams(params, paymentIntentClientSecret);
                stripe.confirmPayment(this, confirmParams);
            }
        });
    }

    private void displayAlert(@NonNull String title,
                              @Nullable String message) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this)
                .setTitle(title)
                .setMessage(message);

        builder.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
             getinfoapi();
            }
        });
        
        
        
        builder.create().show();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        // Handle the result of stripe.confirmPayment
        stripe.onPaymentResult(requestCode, data, new PaymentResultCallback(this));
    }

    private void onPaymentSuccess(@NonNull final Response response) throws IOException {
        Gson gson = new Gson();
        Type type = new TypeToken<Map<String, String>>(){}.getType();
        Map<String, String> responseMap = gson.fromJson(
                Objects.requireNonNull(response.body()).string(),
                type
        );

        paymentIntentClientSecret = responseMap.get("clientSecret");
    }

    private static final class PayCallback implements Callback {
        @NonNull private final WeakReference<checkoutpaymentstripe> activityRef;

        PayCallback(@NonNull checkoutpaymentstripe activity) {
            activityRef = new WeakReference<>(activity);
        }

        @Override
        public void onFailure(@NonNull Call call, @NonNull IOException e) {
            final checkoutpaymentstripe activity = activityRef.get();
            if (activity == null) {
                return;
            }

            activity.runOnUiThread(() ->
                    Toast.makeText(
                            activity, "Error: " + e.toString(), Toast.LENGTH_LONG
                    ).show()
            );
        }

        @Override
        public void onResponse(@NonNull Call call, @NonNull final Response response)
                throws IOException {
            final checkoutpaymentstripe activity = activityRef.get();
            if (activity == null) {
                return;
            }

            if (!response.isSuccessful()) {
                activity.runOnUiThread(() ->
                        Toast.makeText(
                                activity, "Error: " + response.toString(), Toast.LENGTH_LONG
                        ).show()
                );
            } else {
                activity.onPaymentSuccess(response);
            }
        }
    }

    private static final class PaymentResultCallback
            implements ApiResultCallback<PaymentIntentResult> {
        @NonNull private final WeakReference<checkoutpaymentstripe> activityRef;

        PaymentResultCallback(@NonNull checkoutpaymentstripe activity) {
            activityRef = new WeakReference<>(activity);
        }

        @Override
        public void onSuccess(@NonNull PaymentIntentResult result) {
            final checkoutpaymentstripe activity = activityRef.get();
            if (activity == null) {
                return;
            }

            PaymentIntent paymentIntent = result.getIntent();
            PaymentIntent.Status status = paymentIntent.getStatus();
            if (status == PaymentIntent.Status.Succeeded) {
                // Payment completed successfully
                Gson gson = new GsonBuilder().setPrettyPrinting().create();
                activity.displayAlert(
                        "Payment completed",
                        gson.toJson(paymentIntent)
                );
            } else if (status == PaymentIntent.Status.RequiresPaymentMethod) {
                // Payment failed – allow retrying using a different payment method
                activity.displayAlert(
                        "Payment failed",
                        Objects.requireNonNull(paymentIntent.getLastPaymentError()).getMessage()
                );
            }
        }

        @Override
        public void onError(@NonNull Exception e) {
            final checkoutpaymentstripe activity = activityRef.get();
            if (activity == null) {
                return;
            }

            // Payment request failed – allow retrying using the same payment method
            activity.displayAlert("Error", e.toString());
        }
    }



    public void getinfoapi() {
        SharedPreferences prefs = getSharedPreferences(MY_PREFS_NAME, MODE_PRIVATE);
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("http://" + prefs.getString("ipv4", "10.0.2.2") + ":5000/orders/addorder/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        SharedPreferences editor = getSharedPreferences(MY_PREFS_forcart, MODE_PRIVATE);

        shopid=editor.getString("shopincartid","");


        SharedPreferences preferences=getSharedPreferences(MY_PREFS_NAME, MODE_PRIVATE);

        dbhandler dbhandler=new dbhandler(checkoutpaymentstripe.this);
        String tot=String.valueOf(dbhandler.totalprice());
        String dis=String.valueOf(dbhandler.totaldiscount());

        SharedPreferences pref2 = getSharedPreferences(MY_PREFS_forcart, MODE_PRIVATE);
        String delcharge=pref2.getString("deliverycharges","nocharge");

        grand=String.valueOf(Integer.valueOf(tot)+Integer.valueOf(delcharge));

        dbhandler dbhandler1=new dbhandler(checkoutpaymentstripe.this);
        String tax_actualprice=String.valueOf(dbhandler1.totalpricewithoutdiscount());

        Bundle bundle=new Bundle();
        paymentmethodorder= getIntent().getStringExtra("method");
        paymentstatusorder=getIntent().getStringExtra("status");
        lat=editor.getString("latitude","");
        lon=editor.getString("longitude", "");

        orderinfoapi api = retrofit.create(orderinfoapi.class);
        retrofit2.Call<modelreturnoforderinfo> listCall = api.response(getIntent().getStringExtra("name"),preferences.getString("customerid",""),getIntent().getStringExtra("address"),getIntent().getStringExtra("phone"),tot,dis,delcharge,grand,shopid,tax_actualprice,Integer.parseInt(delcharge),lon,lat,paymentmethodorder,paymentstatusorder);
        dbhandler1.close();

        listCall.enqueue(new retrofit2.Callback<modelreturnoforderinfo>() {
            @Override
            public void onResponse(retrofit2.Call<modelreturnoforderinfo> call, retrofit2.Response<modelreturnoforderinfo> response) {
                if (response.isSuccessful()) {
                    orderid=response.body().get_id();
                    orderitems(orderid);
                    Toast.makeText(checkoutpaymentstripe.this, orderid, Toast.LENGTH_SHORT).show();
                }
            }
            @Override
            public void onFailure(retrofit2.Call<modelreturnoforderinfo> call, Throwable t) {
                Toast.makeText(checkoutpaymentstripe.this, t.getMessage(), Toast.LENGTH_LONG).show();
            }
        });
    }
    public void orderitems(String orderid) {
        SharedPreferences prefs = getSharedPreferences(MY_PREFS_NAME, MODE_PRIVATE);
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("http://" + prefs.getString("ipv4", "10.0.2.2") + ":5000/orders/addorderitem/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        dbhandler dbhandler = new dbhandler(checkoutpaymentstripe.this);
        List<modelcart> list = dbhandler.retrievecart();
        dbhandler.close();

        orderitemapi api = retrofit.create(orderitemapi.class);

        for (int i = 0; i < list.size(); i++) {
            d = i;
            retrofit2.Call<ResponseBody> listCall = api.response(orderid, list.get(i).getTitle(), list.get(i).getImage(), list.get(i).getQuantity(), list.get(i).getDiscounted(), list.get(i).getProid(),list.get(i).getPrice());
            listCall.enqueue(new retrofit2.Callback<ResponseBody>() {
                @Override
                public void onResponse(retrofit2.Call<ResponseBody> call, retrofit2.Response<ResponseBody> response) {
                    if (response.isSuccessful()) {
                        Toast.makeText(checkoutpaymentstripe.this, "Ordered", Toast.LENGTH_SHORT).show();
                    }
                    if (response.isSuccessful() && d == list.size() - 1) {
//
                        Intent intent=new Intent(checkoutpaymentstripe.this,dashboardcustomer.class);
                        intent.putExtra("pay",7);
                        intent.putExtra("name",getIntent().getStringExtra("name"));
                        intent.putExtra("method",paymentmethodorder);
                        intent.putExtra("total",grand);
                        startActivity(intent);
                        finish();

                    }
                }

                @Override
                public void onFailure(retrofit2.Call<ResponseBody> call, Throwable t) {
                }
            });
        }
    }


    }
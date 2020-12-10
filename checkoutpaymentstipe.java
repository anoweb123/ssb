package com.ali.ssb;

import androidx.annotation.LongDef;
import androidx.appcompat.app.AppCompatActivity;

import android.nfc.Tag;
import android.os.Bundle;
import android.content.Intent;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;

import com.ali.ssb.Fragments.summary;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import com.stripe.android.ApiResultCallback;
import com.stripe.android.PaymentConfiguration;
import com.stripe.android.PaymentIntentResult;
import com.stripe.android.Stripe;
import com.stripe.android.model.ConfirmPaymentIntentParams;
import com.stripe.android.model.PaymentIntent;
import com.stripe.android.model.PaymentMethodCreateParams;
import com.stripe.android.model.StripeModel;
import com.stripe.android.view.CardInputWidget;

import org.jetbrains.annotations.NotNull;

import java.io.IOException;
import java.lang.ref.WeakReference;
import java.lang.reflect.Type;
import java.util.Map;
import java.util.Objects;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class checkoutpaymentstipe extends AppCompatActivity {
    private static final String BACKEND_URL = "http://192.168.0.107:3000/";
    private OkHttpClient httpClient = new OkHttpClient();
    private String paymentIntentClientSecret;
    private Stripe stripe;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_checkoutpaymentstipe);


        stripe = new Stripe(
                getApplicationContext(),
                Objects.requireNonNull("pk_test_51HTDUvLEXeAwmixgZmjx10jEZNbt5eJiAVpkNdxNBefQGJJZ6YL3YVjggygPKSNRKYhwYlUVyOrxVGKzgpxq1RtU00QLP6r5MB")
        );
        startCheckout();

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        // Handle the result of stripe.confirmPayment
        stripe.onPaymentResult(requestCode, data, new PaymentResultCallback(this));

    }

    private void startCheckout() {
        // Create a PaymentIntent by calling the server's endpoint.
        Button payButton = findViewById(R.id.payButton);

        payButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                MediaType mediaType = MediaType.get("application/json; charset=utf-8");
                String json = "{"
                        + "\"currency\":\"usd\","
                        + "\"items\":["
                        + "{\"id\":\"photo_subscription\"}"
                        + "]"
                        + "}";
                RequestBody body = RequestBody.create(json, mediaType);
                Request request = new Request.Builder()
                        .url(BACKEND_URL + "create-payment-intent")
                        .post(body)
                        .build();
                httpClient.newCall(request)
                        .enqueue(new PayCallback(checkoutpaymentstipe.this));
                PaymentConfiguration.init(getApplicationContext(), "pk_test_51HTDUvLEXeAwmixgZmjx10jEZNbt5eJiAVpkNdxNBefQGJJZ6YL3YVjggygPKSNRKYhwYlUVyOrxVGKzgpxq1RtU00QLP6r5MB");




//                CardInputWidget cardInputWidget = findViewById(R.id.cardInputWidgets);
//                PaymentMethodCreateParams params = cardInputWidget.getPaymentMethodCreateParams();
//                if (params != null) {
////                startActivity(new Intent(checkoutpaymentstipe.this, summary.class));
//                    ConfirmPaymentIntentParams confirmParams = ConfirmPaymentIntentParams
//                            .createWithPaymentMethodCreateParams(params, paymentIntentClientSecret);
//                    stripe.confirmPayment(checkoutpaymentstipe.this, confirmParams);
//                }


            }
        });
//         Hook up the pay button to the card widget and stripe instance
    }



    final class PayCallback implements Callback {
        @NonNull private final WeakReference<checkoutpaymentstipe> activityRef;
        PayCallback(@NonNull checkoutpaymentstipe activity) {
            activityRef = new WeakReference<>(activity);
        }
        @Override
        public void onFailure(@NonNull Call call, @NonNull IOException e) {
            final checkoutpaymentstipe activity = activityRef.get();
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
            checkoutpaymentstipe activity = activityRef.get();
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
                try {
                    activity.onPaymentSuccess(response);
                }
                catch (Exception e){
                }
//              }
            }
        }
    }

    private static class PaymentResultCallback implements ApiResultCallback<PaymentIntentResult> {
        @NonNull private final WeakReference<checkoutpaymentstipe> activityRef;
        PaymentResultCallback(@NonNull checkoutpaymentstipe activity) {
            activityRef = new WeakReference<>(activity);
        }
        @Override
        public void onError(@NotNull Exception e) {
            final checkoutpaymentstipe activity = activityRef.get();
            if (activity == null) {
                return;
            }
            // Payment request failed – allow retrying using the same payment method
            activity.displayAlert("Error", e.toString());
        }


        @Override
        public void onSuccess(@NotNull PaymentIntentResult paymentIntentResult) {
            final checkoutpaymentstipe activity = activityRef.get();
            if (activity == null) {
                return;
            }
            PaymentIntent paymentIntent = paymentIntentResult.getIntent();
            PaymentIntent.Status status = paymentIntent.getStatus();
            if (status == PaymentIntent.Status.Succeeded) {
                Toast.makeText(activity, "Payment completed successfully", Toast.LENGTH_SHORT).show();
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
        }

    private void displayAlert(String title,
                              String message) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this)
                .setTitle(title)
                .setMessage(message);
        builder.setPositiveButton("Ok", null);
        builder.create().show();
    }

    private void onPaymentSuccess(@NonNull Response response) {




        if (response.isSuccessful()){
            Toast.makeText(this, "", Toast.LENGTH_SHORT).show();
//        Gson gson = new Gson();
//        Type type = new TypeToken<Map<String, String>>(){}.getType();
//        Map<String, String> responseMap = gson.fromJson(
//                Objects.requireNonNull(response.body()).toString(),
//                type
//        );
//            Toast.makeText(this, "", Toast.LENGTH_SHORT).show();
//        paymentIntentClientSecret = responseMap.get("clientSecret");

        }
    
    }
    }


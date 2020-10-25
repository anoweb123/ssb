package com.ali.ssb.Fragments;

import android.Manifest;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.os.FileUtils;
import android.provider.MediaStore;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.ali.ssb.Models.modelbaner;
import com.ali.ssb.Models.modelgetresultofimageupdate;
import com.ali.ssb.R;
import com.ali.ssb.holderclasses.sliderbanneradapter;
import com.ali.ssb.interfacesapi.banerapi;
import com.ali.ssb.interfacesapi.imageupdateapi;
import com.ali.ssb.interfacesapi.nameupdateapi;
import com.google.android.gms.common.util.Base64Utils;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.smarteist.autoimageslider.IndicatorView.animation.type.IndicatorAnimationType;
import com.smarteist.autoimageslider.SliderAnimations;
import com.smarteist.autoimageslider.SliderView;
import com.squareup.picasso.Picasso;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.Base64;
import java.util.HashMap;
import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

import static android.app.Activity.RESULT_OK;
import static android.content.Context.MODE_PRIVATE;
import static android.content.Context.PRINT_SERVICE;
import static androidx.constraintlayout.motion.utils.Oscillator.TAG;
import static com.ali.ssb.loginpagecustomer.MY_PREFS_NAME;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link profilecustomer#newInstance} factory method to
 * create an instance of this fragment.
 */
public class profilecustomer extends Fragment {

    ImageView name,address,phone,password,email;
    FloatingActionButton choseimg;
    Uri imageUri;
    ProgressBar bar;
    CircleImageView profileimage;
    onexitclicklistener monexitclicklistener;

    public void setonbanerclicklistener(onexitclicklistener listener){
        monexitclicklistener=  listener;
    }

    public interface onexitclicklistener{
        void onexitclick(String name);
    }



    InputStream imageStream;
    int CAMERA_PERMISSION_CODE=100;
    ImageView camera,gallary;
    AlertDialog alertDialog;
    private static final int REQUEST_IMAGE_CAPTURE = 1,imageGalary=2;
    AlertDialog.Builder builder;
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    public static final String MY_PREFS_NAME = "mydetails";
    TextView names,emails,cells,addresss;
    int count;
    String sname,semail,scell,saddress,sid,spass;

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public profilecustomer() {
        // Required empty public constructor
    }

    // TODO: Rename and change types and number of parameters
    public static profilecustomer newInstance(String param1, String param2) {
        profilecustomer fragment = new profilecustomer();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if(requestCode == imageGalary && resultCode == RESULT_OK)
        {
            bar.setVisibility(View.VISIBLE);
            imageUri=data.getData();

            final Uri imageUri = data.getData();
            InputStream imageStream = null;
            try {
                imageStream = getContext().getContentResolver().openInputStream(imageUri);
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }
            final Bitmap selectedImage = BitmapFactory.decodeStream(imageStream);
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            byte[] b = baos.toByteArray();
            String encImage ="data:image/jpeg;base64,data:image/jpeg;base64,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";
            Toast.makeText(getContext(), String.valueOf(encImage.length()), Toast.LENGTH_SHORT).show();

            SharedPreferences prefss=getContext().getSharedPreferences(MY_PREFS_NAME,MODE_PRIVATE);
            Retrofit retrofitpro = new Retrofit.Builder()
                    .baseUrl("http://"+prefss.getString("ipv4","10.0.2.2")+":5000/")
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();
            imageupdateapi apipro=retrofitpro.create(imageupdateapi.class);

            Call<ResponseBody> listCallpro=apipro.updateimg(sid,semail,encImage);
                    listCallpro.enqueue(new Callback<ResponseBody>() {
                @Override
                public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                    Toast.makeText(getContext(), String.valueOf(response.code()), Toast.LENGTH_SHORT).show();
                    if (response.isSuccessful()) {
//                    SharedPreferences.Editor editor= (SharedPreferences.Editor) getContext().getSharedPreferences(MY_PREFS_NAME,MODE_PRIVATE);
//                    editor.putString("image",response.body().getImage());
//                    editor.putString("imagehave","yes");
//                    editor.apply();
                        bar.setVisibility(View.INVISIBLE);
//                    Picasso.get().load(response.body().getImage()).into(profileimage);
                    }
                }
                @Override
                public void onFailure(Call<ResponseBody> call, Throwable t) {
                    Toast.makeText(getContext(), t.getMessage(), Toast.LENGTH_SHORT).show();
                }
            });

            Toast.makeText(getContext(), "yes", Toast.LENGTH_SHORT).show();
            Log.d(TAG, "onActivityResult: "+encImage);
//            String encodedImage = encodeImage(selectedImage);

            SharedPreferences prefs = getContext().getSharedPreferences(MY_PREFS_NAME, MODE_PRIVATE);
            sname = prefs.getString("name", "Null");//"No name defined" is the default value.
            sid = prefs.getString("customerid", "Null");

//            Base64Utils.encode(b);
//            profileimage.setImageBitmap(selectedImage);
            profileimage.setImageURI(imageUri);
        }
        if (requestCode == REQUEST_IMAGE_CAPTURE && resultCode == RESULT_OK) {
            imageUri=data.getData();
            Bundle extras = data.getExtras();
            Bitmap imageBitmap = (Bitmap) extras.get("data");
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            byte[] databytes = baos.toByteArray();
            bar.setVisibility(View.VISIBLE);

//            String s=Base64Utils.encode(databytes);
//            Toast.makeText(getContext(), s, Toast.LENGTH_LONG).show();
//            String encodedImage = Base64.encodeToString(databytes, Base64.DEFAULT);
            profileimage.setImageBitmap(imageBitmap);


            UploadTask uploadTask= FirebaseStorage.getInstance().getReference().child(sid).putBytes(databytes);
            uploadTask.addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                @Override
                public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                    Toast.makeText(getContext(), "Image updated", Toast.LENGTH_SHORT).show();
                    bar.setVisibility(View.INVISIBLE);
                }
            });

        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view= inflater.inflate(R.layout.fragment_profilecustomer, container, false);

        SharedPreferences.Editor editor =getContext().getSharedPreferences(MY_PREFS_NAME, MODE_PRIVATE).edit();
        editor.putString("ipv4","192.168.43.148");
        editor.putString("onback","profile");
        editor.apply();
        try {

            address = view.findViewById(R.id.addressupdate);
            name = view.findViewById(R.id.nameupdate);
            phone = view.findViewById(R.id.phoneupdate);
            password = view.findViewById(R.id.updatepassword);
            email = view.findViewById(R.id.emailupdate);
            bar = view.findViewById(R.id.bar);

            bar.setVisibility(View.INVISIBLE);
            addresss = view.findViewById(R.id.address);
            names = view.findViewById(R.id.name);
            cells = view.findViewById(R.id.phone);
            emails = view.findViewById(R.id.email);

            SharedPreferences prefs = getContext().getSharedPreferences(MY_PREFS_NAME, MODE_PRIVATE);
            sname = prefs.getString("name", "Null");//"No name defined" is the default value.
            semail = prefs.getString("email", "Null");//"No name defined" is the default value.
            scell = prefs.getString("phone", "Null"); //0 is the default value.
            saddress = prefs.getString("address", "Null"); //0 is the default value.
            spass = prefs.getString("password", "Null");
            sid = prefs.getString("customerid", "Null");

            names.setText(sname);
            emails.setText(semail);
            cells.setText(scell);
            addresss.setText(saddress);


//            bar.setVisibility(View.VISIBLE);
//            FirebaseStorage.getInstance().getReference(sid).getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
//                @Override
//                public void onSuccess(Uri uri) {
//                    Picasso.get().load(uri).into(profileimage);
//                }
//            });
//            bar.setVisibility(View.INVISIBLE);

            name.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    updatename updatename = new updatename();
                    FragmentManager fragmentManagerpro =getActivity().getSupportFragmentManager();
                    FragmentTransaction fragmentTransactionpro = fragmentManagerpro.beginTransaction();
                    fragmentTransactionpro.replace(R.id.fragment, updatename);
                    fragmentTransactionpro.commit();
                }
            });
            address.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    addressupdate updateaddress = new addressupdate();
                    FragmentManager fragmentManagerpro =getActivity().getSupportFragmentManager();
                    FragmentTransaction fragmentTransactionpro = fragmentManagerpro.beginTransaction();
                    fragmentTransactionpro.replace(R.id.fragment, updateaddress);
                    fragmentTransactionpro.commit();
                }
            });
            phone.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    updatephone enterpassword = new updatephone();
                    FragmentManager fragmentManagerpro =getActivity().getSupportFragmentManager();
                    FragmentTransaction fragmentTransactionpro = fragmentManagerpro.beginTransaction();
                    fragmentTransactionpro.replace(R.id.fragment, enterpassword);
                    fragmentTransactionpro.commit();
                }
            });
            password.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    updatepassword enterpassword = new updatepassword();
                    FragmentManager fragmentManagerpro =getActivity().getSupportFragmentManager();
                    FragmentTransaction fragmentTransactionpro = fragmentManagerpro.beginTransaction();
                    fragmentTransactionpro.replace(R.id.fragment, enterpassword);
                    fragmentTransactionpro.commit();
                }
            });
            email.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    updateemail enter = new updateemail();
                    FragmentManager fragmentManagerpro =getActivity().getSupportFragmentManager();
                    FragmentTransaction fragmentTransactionpro = fragmentManagerpro.beginTransaction();
                    fragmentTransactionpro.replace(R.id.fragment, enter);
                    fragmentTransactionpro.commit();
                }
            });


            choseimg = view.findViewById(R.id.floatingActionButton);
            profileimage = view.findViewById(R.id.imageview_account_profile);

            choseimg.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    builder = new AlertDialog.Builder(getContext());
                    View viewimg = LayoutInflater.from(getContext()).inflate(R.layout.dialogforimage, null);
                    builder.setView(viewimg).setTitle("Select You Image");
                    alertDialog = builder.create();
                    alertDialog.show();
                    camera = viewimg.findViewById(R.id.camraImage);
                    gallary = viewimg.findViewById(R.id.galaryImage);
                    gallary.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            alertDialog.cancel();
                            Intent intent = new Intent(Intent.ACTION_PICK);
                            intent.setType("image/*");
                            String[] mimeTypes = {"image/jpeg", "image/png"};
                            intent.putExtra(Intent.EXTRA_MIME_TYPES, mimeTypes);
                            startActivityForResult(intent, imageGalary);
                        }
                    });
                    camera.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            alertDialog.cancel();
                            if (ContextCompat.checkSelfPermission(getContext(),
                                    Manifest.permission.CAMERA) == PackageManager.PERMISSION_GRANTED) {
                                Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                                if (takePictureIntent.resolveActivity(getContext().getPackageManager()) != null) {
                                    try {
                                        startActivityForResult(takePictureIntent, REQUEST_IMAGE_CAPTURE);
                                    } catch (Exception e) {
                                        Toast.makeText(getContext(), e.getMessage(), Toast.LENGTH_SHORT).show();
                                    }
                                }
                            } else {
                                requestStoragePermission();
                            }
                        }
                    });

                }
            });
        }
        catch (Exception e){
            Toast.makeText(getContext(), e.getMessage(), Toast.LENGTH_SHORT).show();
        }
        return  view;
    }
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        if (requestCode == CAMERA_PERMISSION_CODE)  {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                Toast.makeText(getContext(), "Permission GRANTED", Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(getContext(), "Permission DENIED", Toast.LENGTH_SHORT).show();
            }
        }
    }
    private void requestStoragePermission() {
        if (ActivityCompat.shouldShowRequestPermissionRationale(getActivity(),
                Manifest.permission.CAMERA)) {
            new AlertDialog.Builder(getContext())
                    .setTitle("Permission needed")
                    .setMessage("This permission is needed because of Camera")
                    .setPositiveButton("ok", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            ActivityCompat.requestPermissions(getActivity(),
                                    new String[] {Manifest.permission.CAMERA}, CAMERA_PERMISSION_CODE);
                        }
                    })
                    .setNegativeButton("cancel", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.dismiss();
                        }
                    })
                    .create().show();
        } else {
            ActivityCompat.requestPermissions(getActivity(),
                    new String[] {Manifest.permission.CAMERA}, CAMERA_PERMISSION_CODE);
        }
    }
}
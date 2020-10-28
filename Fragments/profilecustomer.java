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
import com.squareup.picasso.NetworkPolicy;
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

    InputStream imageStream;
    int CAMERA_PERMISSION_CODE=100;
    ImageView camera,gallary;
    AlertDialog alertDialog;
    private static final int REQUEST_IMAGE_CAPTURE = 1,imageGalary=2;
    AlertDialog.Builder builder;
    public void setonbanerclicklistener(onexitclicklistener listener){
        monexitclicklistener=  listener;
    }

    public interface onexitclicklistener{
        void onexitclick(String name);
    }


    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    public static final String MY_PREFS_NAME = "mydetails";
    TextView names,emails,cells,addresss;
    int count;
    String sname,semail,scell,saddress,sid,spass,simage;

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

//            final Uri imageUri = data.getData();
//            InputStream imageStream = null;
//            try {
//                imageStream = getContext().getContentResolver().openInputStream(imageUri);
//            } catch (FileNotFoundException e) {
//                e.printStackTrace();
//            }
//            final Bitmap selectedImage = BitmapFactory.decodeStream(imageStream);
//            ByteArrayOutputStream baos = new ByteArrayOutputStream();
//            selectedImage.compress(Bitmap.CompressFormat.JPEG, 100, baos);
//            byte[] databytes = baos.toByteArray();

//            String encImage ="data:image/png;base64,"+Base64Utils.encodeUrlSafe(databytes);
//            Toast.makeText(getContext(), String.valueOf(encImage.length()), Toast.LENGTH_SHORT).show();

//            SharedPreferences prefss=getContext().getSharedPreferences(MY_PREFS_NAME,MODE_PRIVATE);
//            Retrofit retrofitpro = new Retrofit.Builder()
//                    .baseUrl("http://"+prefss.getString("ipv4","10.0.2.2")+":5000/customer/updateimage/")
//                    .addConverterFactory(GsonConverterFactory.create())
//                    .build();
//            imageupdateapi apipro=retrofitpro.create(imageupdateapi.class);

//            Call<modelgetresultofimageupdate> listCallpro=apipro.updateimg(sid,semail,encImage);
//                    listCallpro.enqueue(new Callback<modelgetresultofimageupdate>() {
//                @Override
//                public void onResponse(Call<modelgetresultofimageupdate> call, Response<modelgetresultofimageupdate> response) {
//                    Toast.makeText(getContext(), String.valueOf(response.code()), Toast.LENGTH_SHORT).show();
//                    if (response.isSuccessful()) {
////                    SharedPreferences.Editor editor= (SharedPreferences.Editor) getContext().getSharedPreferences(MY_PREFS_NAME,MODE_PRIVATE);
////                    editor.putString("image",response.body().getImage());
////                    editor.putString("imagehave","yes");
////                    editor.apply();
//                        if (response.body().getImage().equals(null)){
//                        Toast.makeText(getContext(), "yesnull", Toast.LENGTH_SHORT).show();
//                        }
//                        else {
//                            Toast.makeText(getContext(), response.body().getImage(), Toast.LENGTH_SHORT).show();
//                        }
//                        bar.setVisibility(View.INVISIBLE);
////                        assert response.body() != null;
////                        Picasso.get().load(response.body().getImage().replaceFirst("localhost",prefss.getString("ipv4","10.0.2.2"))).networkPolicy(NetworkPolicy.NO_STORE).into(profileimage);
//                    }
//                }
//                @Override
//                public void onFailure(Call<modelgetresultofimageupdate> call, Throwable t) {
//                    Toast.makeText(getContext(), t.getMessage(), Toast.LENGTH_SHORT).show();
//                }
//            });

//            Toast.makeText(getContext(), "yes", Toast.LENGTH_SHORT).show();
//            Log.d(TAG, "onActivityResult: "+encImage);
//            String encodedImage = encodeImage(selectedImage);

            SharedPreferences prefs = getContext().getSharedPreferences(MY_PREFS_NAME, MODE_PRIVATE);
            sname = prefs.getString("name", "Null");//"No name defined" is the default value.
            sid = prefs.getString("customerid", "Null");

            FirebaseStorage.getInstance().getReference().child(sid).putFile(imageUri).addOnCompleteListener(new OnCompleteListener<UploadTask.TaskSnapshot>() {
                @Override
                public void onComplete(@NonNull Task<UploadTask.TaskSnapshot> task) {
                    Toast.makeText(getContext(),"Image updated", Toast.LENGTH_SHORT).show();
                    FirebaseStorage.getInstance().getReference().child(sid).getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                        @Override
                        public void onSuccess(Uri uri) {
                            Picasso.get().load(uri).into(profileimage);
                            SharedPreferences.Editor editor=getContext().getSharedPreferences(MY_PREFS_NAME,MODE_PRIVATE).edit();
                            editor.putString("image",uri.toString());
                            editor.apply();

                            bar.setVisibility(View.INVISIBLE);
                        }
                    });
                }
            });

//            UploadTask uploadTask= FirebaseStorage.getInstance().getReference().child(sid).putBytes(databytes);
//            uploadTask.addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
//                @Override
//                public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
//                    Toast.makeText(getContext(), "Image updated", Toast.LENGTH_SHORT).show();
//                    Picasso.get().load().into(profileimage);
//                    bar.setVisibility(View.INVISIBLE);
//                }
//            });


//            Base64Utils.encode(b);
//            profileimage.setImageBitmap(selectedImage);
//            profileimage.setImageURI(imageUri);
        }
        if (requestCode == REQUEST_IMAGE_CAPTURE && resultCode == RESULT_OK){
            imageUri=data.getData();
            Bundle extras = data.getExtras();
            Bitmap imageBitmap = (Bitmap) extras.get("data");
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            byte[] databytes = baos.toByteArray();
            bar.setVisibility(View.VISIBLE);

//            String s=Base64Utils.encode(databytes);
//            Toast.makeText(getContext(), s, Toast.LENGTH_LONG).show();
//            String encodedImage = Base64.encodeToString(databytes, Base64.DEFAULT);
//            profileimage.setImageBitmap(imageBitmap);



            SharedPreferences prefs = getContext().getSharedPreferences(MY_PREFS_NAME, MODE_PRIVATE);
            sname = prefs.getString("name", "Null");//"No name defined" is the default value.
            sid = prefs.getString("customerid", "Null");


            FirebaseStorage.getInstance().getReference().child(sid).putFile(imageUri).addOnCompleteListener(new OnCompleteListener<UploadTask.TaskSnapshot>() {
                @Override
                public void onComplete(@NonNull Task<UploadTask.TaskSnapshot> task) {
                    Toast.makeText(getContext(),"Image updated", Toast.LENGTH_SHORT).show();
                    FirebaseStorage.getInstance().getReference().child(sid).getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                        @Override
                        public void onSuccess(Uri uri) {
                            Picasso.get().load(uri).into(profileimage);
                            SharedPreferences.Editor editor=getContext().getSharedPreferences(MY_PREFS_NAME,MODE_PRIVATE).edit();
                            editor.putString("image", String.valueOf(uri));
                            editor.apply();

                            bar.setVisibility(View.INVISIBLE);
                        }
                    });
                }
            });


        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view= inflater.inflate(R.layout.fragment_profilecustomer, container, false);

//        SharedPreferences.Editor editor =getContext().getSharedPreferences(MY_PREFS_NAME, MODE_PRIVATE).edit();
//        editor.putString("ipv4","192.168.43.148");
//        editor.putString("onback","profile");
//        editor.apply();

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


            bar.setVisibility(View.VISIBLE);
            simage = prefs.getString("image", "no");//"No name defined" is the default value.
            if (simage.equals("")||simage.equals("no")){

            }
            else {
            Picasso.get().load(simage).into(profileimage);}

            bar.setVisibility(View.INVISIBLE);


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
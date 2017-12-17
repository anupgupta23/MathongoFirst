package com.learnacad.learnacad.Adapters;

import android.app.DownloadManager;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Environment;
import android.provider.Settings;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.androidnetworking.AndroidNetworking;
import com.androidnetworking.error.ANError;
import com.androidnetworking.interfaces.DownloadListener;
import com.learnacad.learnacad.Activities.MaterialViewActivity;
import com.learnacad.learnacad.Fragments.LCCMaterialsFragment;
import com.learnacad.learnacad.Models.Material;
import com.learnacad.learnacad.Networking.Api_Urls;
import com.learnacad.learnacad.R;

import java.io.File;
import java.util.ArrayList;

import cn.pedant.SweetAlert.SweetAlertDialog;

import static android.Manifest.permission.READ_EXTERNAL_STORAGE;
import static android.Manifest.permission.WRITE_EXTERNAL_STORAGE;

/**
 * Created by Sahil Malhotra on 10-11-2017.
 */

public class LCCMatereialsAdapeter extends RecyclerView.Adapter<LCCMatereialsAdapeter.MaterialsViewHolder> {

    Context mContext;
    long size;
    boolean isDownloaded = false;
    String path;
    ArrayList<Material> materials;
    LCCMaterialsFragment fragment;

    public LCCMatereialsAdapeter(Context context,ArrayList<Material> materials,LCCMaterialsFragment fragment){

        mContext = context;
        this.materials = materials;
        this.fragment = fragment;
    }


    @Override
    public LCCMatereialsAdapeter.MaterialsViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.lccmaterials_item_layout,parent,false);
        return new MaterialsViewHolder(view);
    }

    @Override
    public void onBindViewHolder(LCCMatereialsAdapeter.MaterialsViewHolder holder, int position) {

        final Material m = materials.get(position);

        holder.numberingTextView.setText(position + 1 + "");

        holder.titleTextView.setText(m.getName());

        holder.downloadImageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (checkPermission()) {

                    path = Environment.getExternalStorageDirectory() + "/" + Environment.DIRECTORY_DOWNLOADS;

                    File file = new File(path + "/" + m.getName());

                    File fileDownloads = new File(path);

                    File[] filesExisting = fileDownloads.listFiles();

                    for (File file1 : filesExisting) {

                        if (String.valueOf(file1).equals(String.valueOf(file))) {

                            isDownloaded = true;
                            break;
                        }
                    }

                    if (isDownloaded) {

                        Intent intent = new Intent(mContext, MaterialViewActivity.class);
                        intent.putExtra("path", path);
                        intent.putExtra("title", m.getName());
                        mContext.startActivity(intent);

                    } else {

                        AndroidNetworking.download(Api_Urls.BASE_URL + "uploads/" + m.getMinicourseId() + "/" + m.getName(), path, m.getName())
                                .build()
                                .startDownload(new DownloadListener() {
                                    @Override
                                    public void onDownloadComplete() {
                                        Toast.makeText(mContext, m.getName() + " downloaded", Toast.LENGTH_SHORT).show();
                                        DownloadManager downloadManager = (DownloadManager) mContext.getSystemService(Context.DOWNLOAD_SERVICE);
                                        if (downloadManager != null) {
                                            File file = new File(path, m.getName());
                                            Intent intent = new Intent(mContext, MaterialViewActivity.class);
                                            intent.putExtra("path", path);
                                            intent.putExtra("title", m.getName());
                                            mContext.startActivity(intent);
                                            downloadManager.addCompletedDownload(m.getName(), " ", false, "application/pdf", path, file.length(), true);

                                        }
                                    }

                                    @Override
                                    public void onError(ANError anError) {

                                        Log.d("materialsFileCheck", anError.getMessage());
                                    }
                                });

                    }

                }else{

                    new SweetAlertDialog(mContext,SweetAlertDialog.ERROR_TYPE)
                            .setTitleText("Oops...")
                            .setContentText("It seems you haven't given the permission to access the storage.\nPlease give the permission to access the materials.")
                            .setConfirmText("OK")
                            .setConfirmClickListener(new SweetAlertDialog.OnSweetClickListener() {
                                @Override
                                public void onClick(SweetAlertDialog sweetAlertDialog) {

                                    Intent intent = new Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS,
                                            Uri.parse("package:" + mContext.getPackageName()));

                                    intent.addCategory(Intent.CATEGORY_DEFAULT);
                                    intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                                    mContext.startActivity(intent);
                                }
                            })
                            .show();
                }
            }
        });

            holder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                    if (checkPermission()) {

                        path = Environment.getExternalStorageDirectory() + "/" + Environment.DIRECTORY_DOWNLOADS;

                        File file = new File(path + "/" + m.getName());

                        File fileDownloads = new File(path);

                        File[] filesExisting = fileDownloads.listFiles();

                        for (File file1 : filesExisting) {

                            if (String.valueOf(file1).equals(String.valueOf(file))) {

                                isDownloaded = true;
                                break;
                            }
                        }

                        if (isDownloaded) {

                            Intent intent = new Intent(mContext, MaterialViewActivity.class);
                            intent.putExtra("path", path);
                            intent.putExtra("title", m.getName());
                            mContext.startActivity(intent);

                        } else {

                            AndroidNetworking.download(Api_Urls.BASE_URL + "uploads/" + m.getMinicourseId() + "/" + m.getName(), path, m.getName())
                                    .build()
                                    .startDownload(new DownloadListener() {
                                        @Override
                                        public void onDownloadComplete() {
                                            Toast.makeText(mContext, m.getName() + " downloaded", Toast.LENGTH_SHORT).show();
                                            DownloadManager downloadManager = (DownloadManager) mContext.getSystemService(Context.DOWNLOAD_SERVICE);
                                            if (downloadManager != null) {
                                                File file = new File(path, m.getName());
                                                Intent intent = new Intent(mContext, MaterialViewActivity.class);
                                                intent.putExtra("path", path);
                                                intent.putExtra("title", m.getName());
                                                mContext.startActivity(intent);
                                                downloadManager.addCompletedDownload(m.getName(), " ", false, "application/pdf", path, file.length(), true);

                                            }
                                        }

                                        @Override
                                        public void onError(ANError anError) {

                                            Log.d("materialsFileCheck", anError.getMessage());
                                        }
                                    });

                        }

                    }else{

                        new SweetAlertDialog(mContext,SweetAlertDialog.ERROR_TYPE)
                                .setTitleText("Oops...")
                                .setContentText("It seems you haven't given the permission to access the storage.\nPlease give the permission to access the materials.")
                                .setConfirmText("OK")
                                .setConfirmClickListener(new SweetAlertDialog.OnSweetClickListener() {
                                    @Override
                                    public void onClick(SweetAlertDialog sweetAlertDialog) {

                                        Intent intent = new Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS,
                                                Uri.parse("package:" + mContext.getPackageName()));

                                        intent.addCategory(Intent.CATEGORY_DEFAULT);
                                        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                                        mContext.startActivity(intent);
                                    }
                                })
                                .show();
                    }
                }
            });


    }

    private boolean checkPermission() {

        int readExternal = ContextCompat.checkSelfPermission(mContext.getApplicationContext(), READ_EXTERNAL_STORAGE);
        int writeExternal = ContextCompat.checkSelfPermission(mContext.getApplicationContext(), WRITE_EXTERNAL_STORAGE);

        return (readExternal == PackageManager.PERMISSION_GRANTED && writeExternal == PackageManager.PERMISSION_GRANTED);
    }

    @Override
    public int getItemCount() {
        return materials.size();
    }

    public class MaterialsViewHolder extends RecyclerView.ViewHolder{

        TextView titleTextView;
        TextView numberingTextView;
        ImageButton downloadImageButton;

        public MaterialsViewHolder(View itemView) {
            super(itemView);

            titleTextView = itemView.findViewById(R.id.lccmaterials_recyclerview_item_titleTextView_layout);
            numberingTextView = itemView.findViewById(R.id.lccmaterials_recyclerview_item_numberingTextView_layout);
            downloadImageButton = itemView.findViewById(R.id.lccmaterials_recyclerview_item_downloadImageView_layout);
        }
    }
}

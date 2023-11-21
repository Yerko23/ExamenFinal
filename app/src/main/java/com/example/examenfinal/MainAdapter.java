package com.example.examenfinal;


import android.app.AlertDialog;
import android.content.DialogInterface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.FirebaseDatabase;
import com.orhanobut.dialogplus.DialogPlus;
import com.orhanobut.dialogplus.ViewHolder;

import java.util.HashMap;
import java.util.Map;

import de.hdodenhof.circleimageview.CircleImageView;

public class MainAdapter extends FirebaseRecyclerAdapter<MainModel,MainAdapter.myViewHolder>  {
    public MainAdapter(@NonNull FirebaseRecyclerOptions<MainModel> options){
        super(options);
    }
    @Override
    protected void onBindViewHolder(@NonNull MainAdapter.myViewHolder holder, int position, @NonNull MainModel model) {
        holder.marca.setText(model.getMarca());
        holder.estado.setText(model.getEstado());
        holder.retiro.setText(model.getRetiro());

        Glide.with(holder.img.getContext())
                .load(model.getImgURL())
                .placeholder(com.firebase.ui.database.R.drawable.common_google_signin_btn_icon_dark)
                .circleCrop()
                .error(com.google.firebase.database.R.drawable.common_google_signin_btn_icon_dark_normal)
                .into(holder.img);
        holder.editar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final DialogPlus dialogPlus = DialogPlus.newDialog(holder.img.getContext())
                        .setContentHolder(new ViewHolder(R.layout.ventana_emergente))
                        .setExpanded(true,1200)
                        .create();

                View view = dialogPlus.getHolderView();
                EditText marca = view.findViewById(R.id.marcaText);
                EditText retiro = view.findViewById(R.id.retiroText);
                EditText estado = view.findViewById(R.id.estadoText);
                EditText imgUrl = view.findViewById(R.id.img1);

                Button actualizar = view.findViewById(R.id.btn_actualizar);

                marca.setText(model.getMarca());
                retiro.setText((model.getRetiro()));
                estado.setText(model.getEstado());
                imgUrl.setText(model.getImgURL());

                dialogPlus.show();

                actualizar.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Map<String,Object> map = new HashMap<>();
                        map.put("Marca",marca.getText().toString());
                        map.put("Retiro",retiro.getText().toString());
                        map.put("Estado",estado.getText().toString());
                        map.put("imgURL", imgUrl.getText().toString());

                        FirebaseDatabase.getInstance().getReference().child("Productos")
                                .child(getRef(position).getKey()).updateChildren(map)
                                .addOnSuccessListener(new OnSuccessListener<Void>() {
                                    @Override
                                    public void onSuccess(Void unused) {
                                        Toast.makeText(holder.marca.getContext(),"Actualizacion Correcta",Toast.LENGTH_SHORT).show();
                                        dialogPlus.dismiss();
                                    }
                                }).addOnFailureListener(new OnFailureListener() {
                                    @Override
                                    public void onFailure(@NonNull Exception e) {
                                        Toast.makeText(holder.marca.getContext(),"Error en la Actualizacion",Toast.LENGTH_SHORT).show();
                                        dialogPlus.dismiss();
                                    }
                                });
                        holder.eliminar.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                AlertDialog.Builder builder = new AlertDialog.Builder(holder.marca.getContext());
                                builder.setTitle("Estas seguro de eliminar");
                                builder.setMessage("Eliminado");

                                builder.setPositiveButton("Eliminado", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {
                                        FirebaseDatabase.getInstance().getReference().child("Productos")
                                                .child(getRef(position).getKey()).removeValue();
                                    }
                                });
                                builder.setNegativeButton("Cancelar", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {
                                        Toast.makeText(holder.marca.getContext(),"Cancelar",Toast.LENGTH_SHORT).show();
                                    }
                                });
                                builder.show();
                            }
                        });

                    }
                });

            }
        });


    }

    @NonNull
    @Override
    public MainAdapter.myViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.main_item,parent, false);
        return new myViewHolder(view);
    }
    class myViewHolder extends RecyclerView.ViewHolder{
        CircleImageView img;
        TextView marca,estado,retiro;
        Button editar, eliminar;

        public myViewHolder(@NonNull View itemView) {
            super(itemView);
            img = itemView.findViewById(R.id.img1);
            marca = itemView.findViewById(R.id.marcaText);
            estado = itemView.findViewById(R.id.estadoText);
            retiro = itemView.findViewById(R.id.retiroText);

            editar = itemView.findViewById(R.id.btn_editar);
            eliminar = itemView.findViewById(R.id.btn_eliminar);

        }
    }
}

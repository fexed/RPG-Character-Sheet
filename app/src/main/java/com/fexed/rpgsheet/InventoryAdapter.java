package com.fexed.rpgsheet;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import androidx.annotation.NonNull;
import androidx.collection.ArraySet;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Set;

public class InventoryAdapter extends RecyclerView.Adapter {
    private ArrayList<String> inventory;
    private SharedPreferences state;

    public InventoryAdapter(SharedPreferences state) {
        this.state = state;
        Set<String> stringSet = state.getStringSet("inventory", null);
        if (stringSet != null) {
            inventory = new ArrayList<>(stringSet);
            Collections.sort(inventory);
        }
        else inventory = new ArrayList<>();

    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.objectcard, viewGroup, false);
        return new InventoryViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder viewHolder, int i) {
        String obj = inventory.get(i);
        if (obj != null) ((InventoryViewHolder) viewHolder).bindObj(obj.split("::")[0], obj.split(("::"))[1]);
    }

    @Override
    public int getItemCount() {
        return inventory.size();
    }

    public void removeObj(String obj) {
        inventory.remove(obj);
        state.edit().putStringSet("inventory", new ArraySet<>(inventory)).apply();
        notifyDataSetChanged();
    }

    public void addObj(String obj) {
        inventory.add(obj);
        Collections.sort(inventory);
        state.edit().putStringSet("inventory", new ArraySet<>(inventory)).apply();
    }

    class InventoryViewHolder extends RecyclerView.ViewHolder {
        TextView nameTxt;
        TextView descTxt;
        Button removeBtn;

        public InventoryViewHolder(@NonNull View itemView) {
            super(itemView);
            nameTxt = itemView.findViewById(R.id.objNameTxtV);
            descTxt = itemView.findViewById(R.id.objDescTxtV);
            removeBtn = itemView.findViewById(R.id.removeObjBtn);
        }

        void bindObj(final String title, final String desc) {
            nameTxt.setText(title);
            nameTxt.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View view) {
                    final AlertDialog.Builder alert = new AlertDialog.Builder(view.getContext());
                    final EditText input = new EditText(view.getContext());
                    input.setText(title);
                    alert.setView(input);
                    alert.setNegativeButton(view.getContext().getString(R.string.annulla), null);
                    final AlertDialog alertd = alert.create();
                    alert.setTitle("Modifica " + title);
                    alert.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int whichButton) {
                            String newTitle = input.getText().toString();

                            removeObj(title + "::" + desc);
                            addObj(newTitle + "::" + desc);
                            dialog.cancel();
                            alertd.dismiss();
                        }
                    });
                    alert.show();
                    return true;
                }
            });
            descTxt.setText(desc);
            descTxt.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View view) {
                    final AlertDialog.Builder alert = new AlertDialog.Builder(view.getContext());
                    final EditText input = new EditText(view.getContext());
                    input.setText(desc);
                    alert.setView(input);
                    alert.setNegativeButton(view.getContext().getString(R.string.annulla), null);
                    final AlertDialog alertd = alert.create();
                    alert.setTitle("Modifica descrizione");
                    alert.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int whichButton) {
                            String newDesc = input.getText().toString();
                            removeObj(title + "::" + desc);
                            addObj(title + "::" + newDesc);
                            dialog.cancel();
                            alertd.dismiss();
                        }
                    });
                    alert.show();
                    return true;
                }
            });
            removeBtn.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View view) {
                    new AlertDialog.Builder(view.getContext())
                            .setTitle("Sei sicuro?")
                            .setMessage("Sei sicuro di voler eliminare " + title + "?")
                            .setIcon(android.R.drawable.ic_dialog_alert)
                            .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialogInterface, int i) {
                                    removeObj(title + "::" + desc);
                                }
                            })
                            .setNegativeButton(android.R.string.no, null)
                            .show();
                    return true;
                }
            });
        }
    }
}

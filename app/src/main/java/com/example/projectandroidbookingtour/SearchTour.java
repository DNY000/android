package com.example.projectandroidbookingtour;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatDialogFragment;

public class SearchTour extends AppCompatDialogFragment {

    private EditText edtSearch;
    private SearchTourListener listener;

    @SuppressLint("MissingInflatedId")
    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        super.onCreateDialog(savedInstanceState);

        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());

        LayoutInflater inflater = getActivity().getLayoutInflater();
        View view = inflater.inflate(R.layout.search_tour, null);

        edtSearch = view.findViewById(R.id.edtSearch);
        builder.setView(view);
        builder.setTitle("Tìm kiếm tour du lịch");
        builder.setNegativeButton("HỦY", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                dialogInterface.dismiss();
            }
        });
        builder.setPositiveButton("TÌM KIẾM", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                String search = edtSearch.getText().toString();
                listener.applyText(search);
            }
        });

        return builder.create();
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);

        try {
            listener = (SearchTourListener) context;
        } catch (Exception exception) {
            throw new ClassCastException(context.toString() + " phải implement SearchTourListener");
        }
    }

    public interface SearchTourListener {
        void applyText(String txtSearch);
    }
}

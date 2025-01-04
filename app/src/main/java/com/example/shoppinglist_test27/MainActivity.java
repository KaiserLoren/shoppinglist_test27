package com.example.shoppinglist_test27;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    private RecyclerView recyclerView;
    private ShoppingListAdapter adapter;
    private DatabaseHelper dbHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        recyclerView = findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        dbHelper = new DatabaseHelper(this);

        loadShoppingLists();

        FloatingActionButton fabAddList = findViewById(R.id.fabAddList);
        fabAddList.setOnClickListener(v -> showAddListDialog());
    }

    private void loadShoppingLists() {
        Cursor cursor = dbHelper.getShoppingLists();
        adapter = new ShoppingListAdapter(this, cursor);
        recyclerView.setAdapter(adapter);
    }

    private void showAddListDialog() {
        View dialogView = LayoutInflater.from(this).inflate(R.layout.dialog_add_list, null);
        EditText etListName = dialogView.findViewById(R.id.etListName);
        EditText etListDate = dialogView.findViewById(R.id.etListDate);

        new AlertDialog.Builder(this)
                .setTitle("Yeni Liste Ekle")
                .setView(dialogView)
                .setPositiveButton("Ekle", (dialog, which) -> {
                    String listName = etListName.getText().toString();
                    String listDate = etListDate.getText().toString();

                    if (!listName.isEmpty() && !listDate.isEmpty()) {
                        dbHelper.addShoppingList(listName, listDate);
                        loadShoppingLists();
                    } else {
                        Toast.makeText(this, "Lütfen tüm alanları doldurun", Toast.LENGTH_SHORT).show();
                    }
                })
                .setNegativeButton("İptal", null)
                .show();
    }
}

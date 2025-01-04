package com.example.shoppinglist_test27;

import android.content.DialogInterface;
import android.database.Cursor;
import android.os.Bundle;
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

public class ItemsActivity extends AppCompatActivity {
    private RecyclerView recyclerView;
    private ItemsAdapter adapter;
    private DatabaseHelper dbHelper;
    private long listId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_items);

        listId = getIntent().getLongExtra("listId", -1);
        recyclerView = findViewById(R.id.recyclerViewItems);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        dbHelper = new DatabaseHelper(this);

        loadItems();

        FloatingActionButton fabAddItem = findViewById(R.id.fabAddItem);
        fabAddItem.setOnClickListener(v -> showAddItemDialog());
    }

    private void loadItems() {
        Cursor cursor = dbHelper.getItemsForList(listId);
        adapter = new ItemsAdapter(this, cursor, dbHelper);
        recyclerView.setAdapter(adapter);
    }

    private void showAddItemDialog() {
        EditText etItemName = new EditText(this);
        etItemName.setHint("Öğe İsmi");

        new AlertDialog.Builder(this)
                .setTitle("Yeni Öğe Ekle")
                .setView(etItemName)
                .setPositiveButton("Ekle", (dialog, which) -> {
                    String itemName = etItemName.getText().toString();
                    if (!itemName.isEmpty()) {
                        dbHelper.addItem(itemName, listId);
                        loadItems();
                    } else {
                        Toast.makeText(this, "Öğe ismi boş olamaz", Toast.LENGTH_SHORT).show();
                    }
                })
                .setNegativeButton("İptal", null)
                .show();
    }
}

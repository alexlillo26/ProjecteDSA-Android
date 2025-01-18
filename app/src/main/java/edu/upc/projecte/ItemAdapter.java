package edu.upc.projecte;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.List;

public class ItemAdapter extends RecyclerView.Adapter<ItemAdapter.ItemViewHolder> {

    private List<Item> itemList;
    private Context context;
    private TiendaActivity tiendaActivity;

    public ItemAdapter(List<Item> itemList, TiendaActivity tiendaActivity) {
        this.itemList = itemList;
        this.context = tiendaActivity;
        this.tiendaActivity = tiendaActivity;
    }

    @NonNull
    @Override
    public ItemViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.activity_item_layout, parent, false);
        return new ItemViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ItemViewHolder holder, int position) {
        Item item = itemList.get(position);
        holder.itemName.setText(item.getName());
        holder.itemPrice.setText("Price: $" + item.getPrice());

        // Cargar la imagen usando AsyncTask
        new ImageLoadTask(item.getImageUrl(), holder.itemImage).execute();

        // Set up the quantity spinner
        ArrayAdapter<Integer> adapter = new ArrayAdapter<>(context, android.R.layout.simple_spinner_item, getQuantities());
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        holder.itemQuantity.setAdapter(adapter);

        // Set up the buy button
        holder.buttonBuy.setOnClickListener(v -> {
            int quantity = Integer.parseInt(holder.itemQuantity.getSelectedItem().toString());
            tiendaActivity.purchaseItem(item, quantity);
        });
    }

    @Override
    public int getItemCount() {
        return itemList.size();
    }

    private List<Integer> getQuantities() {
        return java.util.Arrays.asList(1, 2, 3, 4, 5, 6, 7, 8, 9, 10);
    }

    public Item[] getItemList() {
        return itemList.toArray(new Item[0]);
    }

    public static class ItemViewHolder extends RecyclerView.ViewHolder {
        TextView itemName;
        TextView itemPrice;
        ImageView itemImage;
        Spinner itemQuantity;
        Button buttonBuy;

        public ItemViewHolder(@NonNull View itemView) {
            super(itemView);
            itemName = itemView.findViewById(R.id.item_name);
            itemPrice = itemView.findViewById(R.id.item_price);
            itemImage = itemView.findViewById(R.id.item_image);
            itemQuantity = itemView.findViewById(R.id.item_quantity);
            buttonBuy = itemView.findViewById(R.id.button_buy);
        }
    }

    private static class ImageLoadTask extends AsyncTask<String, Void, Bitmap> {
        private String url;
        private ImageView imageView;

        public ImageLoadTask(String url, ImageView imageView) {
            this.url = url;
            this.imageView = imageView;
        }

        @Override
        protected Bitmap doInBackground(String... params) {
            try {
                URL urlConnection = new URL(url);
                HttpURLConnection connection = (HttpURLConnection) urlConnection.openConnection();
                connection.setDoInput(true);
                connection.connect();
                InputStream input = connection.getInputStream();
                return BitmapFactory.decodeStream(input);
            } catch (Exception e) {
                e.printStackTrace();
            }
            return null;
        }

        @Override
        protected void onPostExecute(Bitmap result) {
            if (result != null) {
                imageView.setImageBitmap(result);
            }
        }
    }
}

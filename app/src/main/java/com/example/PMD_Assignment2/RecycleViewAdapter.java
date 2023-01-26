package com.example.PMD_Assignment2;

import android.content.Context;
import android.content.Intent;
import android.view.ContextMenu;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

import java.util.List;

public class RecycleViewAdapter extends RecyclerView.Adapter<RecycleViewAdapter.ViewHolder> {
    private List<Weather> mData;
    private LayoutInflater mInflater;
    Intent intent;
    Context ctx;
    boolean flag;


    public RecycleViewAdapter(Context context, List<Weather> data, boolean flag){
        this.mInflater = LayoutInflater.from(context);
        this.mData = data;
        this.ctx = context;
        this.flag = flag;
    }

    @NonNull
    @Override
    public RecycleViewAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = mInflater.inflate(R.layout.list_row, parent, false);
        return new ViewHolder(view);
    }


    @Override
    public void onBindViewHolder(@NonNull RecycleViewAdapter.ViewHolder holder, int position) {
        Weather entry = mData.get(position);
        String name = entry.getTitle();
        String weather = entry.getWeather_state_name();
        String temp = "Temp: " + entry.getThe_temp();
        String min = "Min: " + entry.getMin_temp();
        String max = "Max: " + entry.getMax_temp();
        String abbr = entry.getWeather_state_abbr();

        holder.cityTv.setText(name);
        holder.weatherTv.setText(weather);
        holder.tempTv.setText(temp);
        holder.minTv.setText(min);
        holder.maxTv.setText(max);

        Glide.with(ctx)
                .load("https://www.metaweather.com/static/img/weather/png/" + abbr + ".png")
                .error(R.drawable.chicken)
                .fitCenter()
                .into(holder.weatherIcon);

    }

    @Override
    public int getItemCount() {
        return mData.size();
    }

    String getItem(int id) {
        //return mData.get(id);
        return "aaaaaaaaaaaaaa";
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnCreateContextMenuListener {
        ImageView weatherIcon;
        TextView cityTv;
        TextView weatherTv;
        TextView tempTv;
        TextView minTv;
        TextView maxTv;

        ViewHolder(View itemView) {
            super(itemView);
            weatherIcon = itemView.findViewById(R.id.weatherIcon);
            cityTv = itemView.findViewById(R.id.city);
            weatherTv = itemView.findViewById(R.id.weather);
            tempTv = itemView.findViewById(R.id.temp);
            minTv = itemView.findViewById(R.id.min);
            maxTv= itemView.findViewById(R.id.max);

            if (flag) {
                itemView.setOnCreateContextMenuListener(null);
            } else {
                itemView.setOnCreateContextMenuListener(this);
            }
        }


        @Override
        public void onCreateContextMenu(ContextMenu contextMenu, View view, ContextMenu.ContextMenuInfo contextMenuInfo) {
            MenuItem load = contextMenu.add(Menu.NONE, 1, 1, "Week Forecast");
            MenuItem delete = contextMenu.add(Menu.NONE, 2, 2, "Delete");

            load.setOnMenuItemClickListener(onEditMenu);
            delete.setOnMenuItemClickListener(onEditMenu);
        }

        private final MenuItem.OnMenuItemClickListener onEditMenu = new MenuItem.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                int id = item.getItemId();
                int recyclerId = getLayoutPosition();

                Weather weather = mData.get(recyclerId);

                String title = weather.getTitle();
                String woeid = weather.getWoeid();

                DBHandler db = new DBHandler(ctx);
                switch (id) {
                    case 1:
                        intent = new Intent(ctx, WeekForecast.class);
                        intent.putExtra("woeid", woeid);
                        intent.putExtra("title", title);
                        ctx.startActivity(intent);
                        return true;
                    case 2:
                        db.DeleteLocation(title);
                        Toast.makeText(ctx, "Record deleted",Toast.LENGTH_SHORT).show();
                        mData.remove(recyclerId);
                        notifyItemRemoved(recyclerId);
                        return true;
                    default:
                        return true;
                }
            }
        };
    }
}

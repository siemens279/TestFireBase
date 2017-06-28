package com.example.siemens.testfirebase;


import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import java.util.ArrayList;

public class FireBaseRecyclerAdapter extends RecyclerView.Adapter<FireBaseRecyclerAdapter.ViewHolder> {

    ArrayList<Drug> drugs;
    private OnItemClickListener mOnItemClickListener;

    public interface OnItemClickListener {
        public void onItemClick(View view, int position);
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        public TextView name;
        public TextView text;
        public Button del;

        public ViewHolder(View v) {
            super(v);
            name = (TextView) v.findViewById(R.id.textViewName);
            text = (TextView) v.findViewById(R.id.textViewText);
            del = (Button) v.findViewById(R.id.buttonDel);
        }
    }

    // Конструктор
    public FireBaseRecyclerAdapter(ArrayList<Drug> d) {
        drugs = new ArrayList<>(d);
    }
    public FireBaseRecyclerAdapter(ArrayList<Drug> itemsData, OnItemClickListener onItemClickListener) {
        mOnItemClickListener = onItemClickListener;
        drugs = new ArrayList<>(itemsData);
    }

    // Создает новые views (вызывается layout manager-ом)
    @Override
    public FireBaseRecyclerAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        // create a new view
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.one_item, parent, false);
        // тут можно программно менять атрибуты лэйаута (size, margins, paddings и др.)
        ViewHolder vh = new ViewHolder(v);
        return vh;
    }

    // Заменяет контент отдельного view (вызывается layout manager-ом)
    @Override
    public void onBindViewHolder(final ViewHolder holder, final int position) {
        holder.name.setText(drugs.get(position).name);
        holder.text.setText(drugs.get(position).text);
        holder.del.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mOnItemClickListener.onItemClick(v, position);
            }
        });
    }

    // Возвращает размер данных (вызывается layout manager-ом)
    @Override
    public int getItemCount() {
        return drugs.size();
    }

    public void refreshData(ArrayList<Drug> d){
        //Чистим коллекцию с данными
        drugs.clear();
        //наполняем измененными данными
        drugs = new ArrayList<>(d);
        //передергиваем адаптер
        notifyDataSetChanged();
    }



}

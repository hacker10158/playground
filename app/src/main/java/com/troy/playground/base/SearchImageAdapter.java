package com.troy.playground.base;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import com.troy.playground.base.model.ImageData;
import com.troy.playground.databinding.SearchImageItemBinding;

import java.util.ArrayList;
import java.util.List;

public class SearchImageAdapter extends RecyclerView.Adapter<SearchPictureViewHolder> {
    List<ImageData> dataList;

    public SearchImageAdapter() {

    }

    @Override
    public SearchPictureViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        return new SearchPictureViewHolder(SearchImageItemBinding.inflate(layoutInflater, parent, false));
    }

    @Override
    public void onBindViewHolder(final SearchPictureViewHolder holder, int position) {
        holder.bindView(dataList.get(position));
    }

    @Override
    public int getItemCount() {
        return dataList == null ? 0 : dataList.size();
    }

    public void updateData(List<ImageData> data) {
        if (dataList == null) {
            dataList = new ArrayList<>();
        }
        dataList.clear();
        dataList.addAll(data);
        notifyDataSetChanged();
    }

}
package com.troy.playground.search;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import com.troy.playground.search.model.ImageData;
import com.troy.playground.databinding.SearchImageItemBinding;

import java.util.ArrayList;
import java.util.List;

public class SearchImageAdapter extends RecyclerView.Adapter<SearchImageViewHolder> {
    List<ImageData> dataList;

    public SearchImageAdapter() {

    }

    @Override
    public SearchImageViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        return new SearchImageViewHolder(SearchImageItemBinding.inflate(layoutInflater, parent, false));
    }

    @Override
    public void onBindViewHolder(final SearchImageViewHolder holder, int position) {
        holder.bindView(dataList.get(position));
    }

    @Override
    public int getItemCount() {
        return dataList == null ? 0 : dataList.size();
    }

    public void cleanData() {
        if (dataList != null) {
            dataList.clear();
            notifyDataSetChanged();
        }
    }

    public void addData(List<ImageData> data) {
        if (dataList == null) {
            dataList = new ArrayList<>();
        }

        int start = dataList.size();
        dataList.addAll(data);
        int end = dataList.size();
        notifyItemRangeInserted(start, end);
    }

}
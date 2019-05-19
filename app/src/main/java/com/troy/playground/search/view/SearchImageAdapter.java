package com.troy.playground.search.view;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import com.troy.playground.databinding.LoadingMoreItemBinding;
import com.troy.playground.databinding.SearchImageItemBinding;
import com.troy.playground.search.model.ImageData;

import java.util.ArrayList;
import java.util.List;

public class SearchImageAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private final int TYPE_IMAGE = 0x0001;
    private final int TYPE_LOADING = 0x0002;

    private List<ImageData> dataList;
    private boolean showLoadingMore;

    public SearchImageAdapter() {

    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        switch (viewType) {
            case TYPE_LOADING:
                return new LoadingMoreViewHolder(LoadingMoreItemBinding.inflate(layoutInflater, parent, false));
            default:
            case TYPE_IMAGE:
                return new SearchImageViewHolder(SearchImageItemBinding.inflate(layoutInflater, parent, false));

        }
    }

    @Override
    public void onBindViewHolder(final RecyclerView.ViewHolder holder, int position) {
        if (holder instanceof SearchImageViewHolder) {
            ((SearchImageViewHolder)holder).bindView(dataList.get(position));
        }

        if (holder instanceof LoadingMoreViewHolder) {
            ((LoadingMoreViewHolder)holder).bindView(showLoadingMore);
        }
    }

    @Override
    public int getItemCount() {
        return getDataListSize() + getLoadingIndicatorSize();
    }


    @Override
    public int getItemViewType(int position) {
        if (position == getItemCount() - 1) {
            return TYPE_LOADING;
        }
        return TYPE_IMAGE;
    }

    private int getDataListSize() {
        return dataList == null ? 0 : dataList.size();
    }

    private int getLoadingIndicatorSize() {
        return 1;
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
        if (start == 0) {
            notifyDataSetChanged();
        } else {
            notifyItemRangeInserted(start, end);
        }
    }

    public void setLoadingMore(boolean loadingMore) {
        showLoadingMore = loadingMore;
        notifyItemChanged(getItemCount() - 1);
    }
}
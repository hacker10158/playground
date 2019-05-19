package com.troy.playground.search.view;

import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.troy.playground.databinding.LoadingMoreItemBinding;

public class LoadingMoreViewHolder extends RecyclerView.ViewHolder {
    LoadingMoreItemBinding binding;

    LoadingMoreViewHolder(LoadingMoreItemBinding binding) {
        super(binding.getRoot());
        this.binding = binding;
    }

    public void bindView(boolean showLoadingMore) {
        if (showLoadingMore) {
            binding.pbLoadingMore.setVisibility(View.VISIBLE);
        } else {
            binding.pbLoadingMore.setVisibility(View.GONE);
        }
    }

}
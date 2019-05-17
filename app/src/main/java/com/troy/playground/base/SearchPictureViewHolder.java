package com.troy.playground.base;

import android.support.v7.widget.RecyclerView;

import com.troy.playground.base.model.ImageData;
import com.troy.playground.databinding.SearchImageItemBinding;

public class SearchPictureViewHolder extends RecyclerView.ViewHolder {
    SearchImageItemBinding binding;

    SearchPictureViewHolder(SearchImageItemBinding binding) {
        super(binding.getRoot());
        this.binding = binding;
    }

    public void bindView(ImageData imageData) {
        binding.tvId.setText(imageData.getWebformatURL());
    }

}
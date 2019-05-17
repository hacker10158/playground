package com.troy.playground.base;

import android.support.v7.widget.RecyclerView;
import android.view.ViewGroup;

import com.troy.playground.base.model.ImageData;
import com.troy.playground.databinding.SearchImageItemBinding;
import com.troy.playground.utility.FrescoHelper;

public class SearchImageViewHolder extends RecyclerView.ViewHolder {
    SearchImageItemBinding binding;

    SearchImageViewHolder(SearchImageItemBinding binding) {
        super(binding.getRoot());
        this.binding = binding;
    }

    public void bindView(ImageData imageData) {
        binding.tvId.setText(imageData.getWebformatURL());
//        binding.ivImage.setImageURI(imageData.getWebformatURL());
        ViewGroup.LayoutParams params = binding.ivImage.getLayoutParams();
        params.width = imageData.getWebformatWidth();
        params.height = imageData.getWebformatHeight();
        binding.ivImage.setLayoutParams(params);

        FrescoHelper.loadInto(imageData.getPreviewURL(), binding.ivImage, imageData.getWebformatWidth(), imageData.getWebformatWidth());
    }

}
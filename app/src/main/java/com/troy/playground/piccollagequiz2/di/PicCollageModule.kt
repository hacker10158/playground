package com.troy.playground.piccollagequiz2.di

import android.arch.lifecycle.ViewModelProvider
import com.troy.playground.ViewModelProviderFactory
import com.troy.playground.piccollagequiz2.PicCollageFragment
import com.troy.playground.piccollagequiz2.view.PicCollageView
import com.troy.playground.piccollagequiz2.viewmodel.PicCollageViewModel
import dagger.Module
import dagger.Provides
import javax.inject.Named

@Module
class PicCollageModule {

    @Provides
    fun providePicCollageView(picCollageFragment: PicCollageFragment): PicCollageView {
        return picCollageFragment
    }

    @Provides
    fun providePicCollageViewModel(picCollageView: PicCollageView): PicCollageViewModel {
        return PicCollageViewModel(picCollageView)
    }


    @Provides
    @Named("piccollage")
    internal fun providePicCollageViewModelProviderFactory(viewModel: PicCollageViewModel): ViewModelProvider.Factory {
        return ViewModelProviderFactory(viewModel)
    }
}
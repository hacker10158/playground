package com.troy.playground.piccollagequiz2.di

import android.arch.lifecycle.ViewModelProvider
import com.troy.playground.ViewModelProviderFactory
import com.troy.playground.piccollagequiz2.viewmodel.PicCollageViewModel
import dagger.Module
import dagger.Provides
import javax.inject.Named

@Module
class PicCollageModule {

//    @Provides
//    fun provideViewerQuizView(viewerQuizFragment: ViewerQuizFragment): ViewerQuizView {
//        return viewerQuizFragment
//    }

    @Provides
    fun providePicCollageViewModel( ): PicCollageViewModel {
        return PicCollageViewModel()
    }


    @Provides
    @Named("piccollage")
    internal fun providePicCollageViewModelProviderFactory(viewModel: PicCollageViewModel): ViewModelProvider.Factory {
        return ViewModelProviderFactory(viewModel)
    }
}
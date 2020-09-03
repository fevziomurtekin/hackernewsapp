package com.fevziomurtekin.hackernewsapp.uitest

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.LiveData
import com.fevziomurtekin.hackernewsapp.data.room.ItemRepository
import com.fevziomurtekin.hackernewsapp.ui.main.MainViewModel
import com.fevziomurtekin.hackernewsapp.util.ApplicationSchedulerProvider
import com.fevziomurtekin.hackernewsapp.util.Event
import io.reactivex.Observer
import org.junit.Before
import org.junit.Rule
import org.mockito.Mock
import org.mockito.MockitoAnnotations

class MainViewModelTest{

    
    lateinit var mainViewModel: MainViewModel

    @Mock
    lateinit var view:Observer<Event>

    @Mock
    lateinit var repository: ItemRepository

    @get:Rule
    val rule = InstantTaskExecutorRule()

    @Before
    fun before(){
        /* init mockito */
        MockitoAnnotations.initMocks(this)

        mainViewModel =  MainViewModel(repository,ApplicationSchedulerProvider())

    }
}



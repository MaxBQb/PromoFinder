package edu.mirea_ikbo0619.promofinder.utils

import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer

fun <T> Fragment.observe(livedata: LiveData<T>, observer: Observer<T>) =
    livedata.observe(viewLifecycleOwner, observer)

fun <T> AppCompatActivity.observe(livedata: LiveData<T>, observer: Observer<T>) =
    livedata.observe(this, observer)

fun <T> MutableLiveData<T>.set(value: T) = postValue(value)
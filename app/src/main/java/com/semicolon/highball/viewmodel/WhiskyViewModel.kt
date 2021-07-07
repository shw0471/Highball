package com.semicolon.highball.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.semicolon.highball.base.SingleLiveEvent
import com.semicolon.highball.local.FavoriteWhiskyDao
import com.semicolon.highball.local.FavoriteWhiskyRoomData
import com.semicolon.highball.remote.WhiskyData
import com.semicolon.highball.remote.WhiskyService
import io.reactivex.Single
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.observers.DisposableSingleObserver
import io.reactivex.schedulers.Schedulers

class WhiskyViewModel(
    private val whiskyService: WhiskyService,
    private val favoriteWhiskyDao: FavoriteWhiskyDao
) : ViewModel() {

    private val compositeDisposable = CompositeDisposable()

    private val whiskyList = MutableLiveData<List<WhiskyData>>()
    private val whiskyInfo = MutableLiveData<WhiskyData>()
    private val favoriteWhiskyList = MutableLiveData<List<FavoriteWhiskyRoomData>>()

    private val messageEvent = SingleLiveEvent<String>()
    private val failedToGetWhiskyListEvent = SingleLiveEvent<Unit>()
    private val failedToGetWhiskyInfoEvent = SingleLiveEvent<Unit>()
    private val failedToGetFavoriteWhiskyListEvent = SingleLiveEvent<Unit>()
    private val addFavoriteWhiskyEvent =  SingleLiveEvent<Unit>()
    private val deleteFavoriteWhiskyEvent = SingleLiveEvent<Unit>()

    fun getWhiskyList() {
        val result = whiskyService.getWhiskyList()
        val disposableSingleObserver =
            object : DisposableSingleObserver<List<WhiskyData>>() {

                override fun onSuccess(t: List<WhiskyData>) {
                    whiskyList.value = t
                }

                override fun onError(e: Throwable) {
                    failedToGetWhiskyListEvent.call()
                }
            }
        addDisposable(result, disposableSingleObserver)
    }

    fun getWhiskyInfo(id: Int) {
        val result = whiskyService.getWhiskyById(id)
        val disposableSingleObserver =
            object : DisposableSingleObserver<WhiskyData>() {

                override fun onSuccess(t: WhiskyData) {
                    whiskyInfo.value = t
                }

                override fun onError(e: Throwable) {
                    failedToGetWhiskyInfoEvent.call()
                }
            }
        addDisposable(result, disposableSingleObserver)
    }

    fun getFavoriteWhiskyList() {
        val result = favoriteWhiskyDao.getFavoriteWhiskyList()
        val disposableSingleObserver =
            object : DisposableSingleObserver<List<FavoriteWhiskyRoomData>>() {

                override fun onSuccess(t: List<FavoriteWhiskyRoomData>) {
                    favoriteWhiskyList.value = t
                }

                override fun onError(e: Throwable) {
                    failedToGetFavoriteWhiskyListEvent.call()
                }
            }
        addDisposable(result, disposableSingleObserver)
    }

    fun addFavoriteWhisky(id: Int) {
        val result = favoriteWhiskyDao
            .addFavoriteWhisky(FavoriteWhiskyRoomData(id, "", 0, "")).toSingle {}
        val disposableSingleObserver =
            object : DisposableSingleObserver<Unit>() {
                override fun onSuccess(t: Unit) {
                    addFavoriteWhiskyEvent.call()
                }

                override fun onError(e: Throwable) {
                    messageEvent.setValue("failed to add")
                }
            }
        addDisposable(result, disposableSingleObserver)
    }

    fun deleteFavoriteWhisky(id: Int) {
        val result = favoriteWhiskyDao
            .deleteFavoriteWhisky(FavoriteWhiskyRoomData(id, "", 0, "")).toSingle {}
        val disposableSingleObserver =
            object : DisposableSingleObserver<Unit>() {
                override fun onSuccess(t: Unit) {
                    deleteFavoriteWhiskyEvent.call()
                }

                override fun onError(e: Throwable) {
                    messageEvent.setValue("failed to delete")
                }
            }
        addDisposable(result, disposableSingleObserver)
    }

    private fun <E> addDisposable(
        singleObservable: Single<E>,
        singleObserver: DisposableSingleObserver<E>
    ) {
        val observer = singleObservable
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribeWith(singleObserver)
        compositeDisposable.add(observer)
    }

    override fun onCleared() {
        super.onCleared()
        compositeDisposable.dispose()
    }

}
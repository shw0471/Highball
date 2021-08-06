package com.semicolon.highball.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.semicolon.highball.base.SingleLiveEvent
import com.semicolon.highball.local.FavoriteWhiskyDao
import com.semicolon.highball.local.FavoriteWhiskyRoomData
import com.semicolon.highball.remote.WhiskyData
import com.semicolon.highball.remote.WhiskyResponse
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

    val nextPage = MutableLiveData<Int>(1)
    val whiskyList = MutableLiveData<WhiskyResponse>()
    val whiskyInfo = MutableLiveData<WhiskyData>()
    val favoriteWhiskyList = MutableLiveData<List<FavoriteWhiskyRoomData>>()

    val messageEvent = SingleLiveEvent<String>()
    val failedToGetWhiskyListEvent = SingleLiveEvent<Unit>()
    val failedToGetWhiskyInfoEvent = SingleLiveEvent<Unit>()
    val failedToGetFavoriteWhiskyListEvent = SingleLiveEvent<Unit>()
    val favoriteWhiskyStateEvent = SingleLiveEvent<Boolean>()


    fun getWhiskyList(page: Int) {
        println("log $page")
        val result = whiskyService.getWhiskyList(page)
        val disposableSingleObserver =
            object : DisposableSingleObserver<WhiskyResponse>() {

                override fun onSuccess(t: WhiskyResponse) {
                    if (page == 1) whiskyList.value = t
                    else whiskyList.value!!.results.addAll(t.results)

                    if(t.next == null) nextPage.value = 0
                    else nextPage.value = nextPage.value!!.plus(1)
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

    fun addFavoriteWhisky(id: Int, title: String, price: Int, imageUrl: String) {
        val result = favoriteWhiskyDao
            .addFavoriteWhisky(FavoriteWhiskyRoomData(id, title, price, imageUrl)).toSingle {}
        val disposableSingleObserver =
            object : DisposableSingleObserver<Unit>() {
                override fun onSuccess(t: Unit) {
                    favoriteWhiskyStateEvent.setValue(true)
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
                    favoriteWhiskyStateEvent.setValue(false)
                }

                override fun onError(e: Throwable) {
                    messageEvent.setValue("failed to delete")
                }
            }
        addDisposable(result, disposableSingleObserver)
    }

    fun isFavoriteWhisky(id: Int) {
        val result = favoriteWhiskyDao
            .getFavoriteWhiskyListById(id)
        val disposableSingleObserver =
            object : DisposableSingleObserver<List<FavoriteWhiskyRoomData>>() {
                override fun onSuccess(t: List<FavoriteWhiskyRoomData>) {
                    if (t.isEmpty()) favoriteWhiskyStateEvent.setValue(false)
                    else favoriteWhiskyStateEvent.setValue(true)
                }

                override fun onError(e: Throwable) {
                    messageEvent.setValue("failed to check")
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
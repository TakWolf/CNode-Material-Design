package org.cnodejs.android.md.vm.holder

import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import org.cnodejs.android.md.BuildConfig
import org.cnodejs.android.md.model.api.CNodeClient
import org.cnodejs.android.md.model.entity.ErrorResult
import org.cnodejs.android.md.model.entity.Tab
import org.cnodejs.android.md.model.entity.Topic

class TopicPagingLiveHolder(
    viewModel: AndroidViewModel,
    toastHolder: ToastLiveHolder,
) : PagingLiveHolder<Topic, Int>(
    viewModel,
    toastHolder,
) {
    companion object {
        private const val TAG = "TopicForHomePaging"
    }

    private val api = CNodeClient.getInstance(viewModel.getApplication()).api

    val tabData = MutableLiveData(Tab.ALL)

    init {
        refresh()
    }

    fun getTab(): Tab {
        return tabData.value ?: Tab.ALL
    }

    fun switchTab(tab: Tab) {
        if (getTab() != tab) {
            tabData.value = tab
            resetPaging()
            refresh()
        }
    }

    override suspend fun doRefresh(version: Int) {
        try {
            val topics = api.getTopics(getTab().queryValue).data
            refreshSuccess(version, topics, 1, topics.isEmpty())
        } catch (e: Exception) {
            if (BuildConfig.DEBUG) {
                Log.e(TAG, "doRefresh", e)
            }
            val errorResult = ErrorResult.from(e)
            refreshFailure(version, errorResult.message)
        }
    }

    override suspend fun doLoadMore(version: Int, pagingParams: Int) {
        try {
            val nextPage = pagingParams + 1
            val topics = api.getTopics(getTab().queryValue, nextPage).data
            loadMoreSuccess(version, topics, nextPage, topics.isEmpty())
        } catch (e: Exception) {
            if (BuildConfig.DEBUG) {
                Log.e(TAG, "doLoadMore", e)
            }
            val errorResult = ErrorResult.from(e)
            loadMoreFailure(version, errorResult.message)
        }
    }
}

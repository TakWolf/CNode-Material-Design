package org.cnodejs.android.md.ui.widget

import android.view.LayoutInflater
import android.view.View
import com.takwolf.android.hfrecyclerview.HeaderAndFooterRecyclerView
import com.takwolf.android.hfrecyclerview.paging.LoadMoreState
import org.cnodejs.android.md.R
import org.cnodejs.android.md.databinding.FooterLoadMoreBinding

class LoadMoreFooter private constructor(
    private val binding: FooterLoadMoreBinding,
) : com.takwolf.android.hfrecyclerview.paging.LoadMoreFooter(binding.root) {
    companion object {
        fun create(recyclerView: HeaderAndFooterRecyclerView): LoadMoreFooter {
            val binding = FooterLoadMoreBinding.inflate(LayoutInflater.from(recyclerView.context), recyclerView.footerViewContainer, false)
            return LoadMoreFooter(binding)
        }
    }

    init {
        binding.tvText.setOnClickListener {
            checkDoLoadMore()
        }
        preloadOffset = 1
    }

    override fun onUpdateViews() {
        when (state) {
            LoadMoreState.DISABLED -> {
                binding.loadingBar.visibility = View.INVISIBLE
                binding.tvText.visibility = View.INVISIBLE
                binding.tvText.text = null
                binding.tvText.isClickable = false
            }
            LoadMoreState.IDLE -> {
                binding.loadingBar.visibility = View.INVISIBLE
                binding.tvText.visibility = View.VISIBLE
                binding.tvText.text = null
                binding.tvText.isClickable = true
            }
            LoadMoreState.LOADING -> {
                binding.loadingBar.visibility = View.VISIBLE
                binding.tvText.visibility = View.INVISIBLE
                binding.tvText.text = null
                binding.tvText.isClickable = false
            }
            LoadMoreState.FINISHED -> {
                binding.loadingBar.visibility = View.INVISIBLE
                binding.tvText.visibility = View.VISIBLE
                binding.tvText.setText(R.string.load_more_finished)
                binding.tvText.isClickable = false
            }
            LoadMoreState.FAILED -> {
                binding.loadingBar.visibility = View.INVISIBLE
                binding.tvText.visibility = View.VISIBLE
                binding.tvText.setText(R.string.load_more_failed)
                binding.tvText.isClickable = true
            }
        }
    }
}

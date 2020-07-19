package com.michaeludjiawan.githubfinder.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.EditorInfo
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.paging.ExperimentalPagingApi
import androidx.paging.LoadState
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.michaeludjiawan.githubfinder.R
import com.michaeludjiawan.githubfinder.util.hideSoftKeyboard
import com.michaeludjiawan.githubfinder.util.showSingle
import kotlinx.android.synthetic.main.fragment_main.*
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import org.koin.androidx.viewmodel.ext.android.viewModel

class MainFragment : Fragment() {

    private val viewModel by viewModel<MainViewModel>()

    private val userAdapter = UserAdapter()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_main, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initSearch()
        initRecyclerView()
        initAdapter()
    }

    private fun initSearch() {
        et_main_input.setOnEditorActionListener { _, actionId, _ ->
            if (actionId == EditorInfo.IME_ACTION_SEARCH) {
                btn_main_search.performClick()
                true
            } else {
                false
            }
        }

        btn_main_search.setOnClickListener {
            val query = et_main_input.text.toString()
            if (query.isNotBlank()) {
                viewModel.getUsers(query)
            }
        }
    }

    private fun initRecyclerView() {
        rv_main_users.apply {
            setHasFixedSize(true)
            layoutManager = LinearLayoutManager(context)
            addItemDecoration(DividerItemDecoration(context, DividerItemDecoration.VERTICAL))
        }

        rv_main_users.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrollStateChanged(recyclerView: RecyclerView, newState: Int) {
                super.onScrollStateChanged(recyclerView, newState)
                if (newState == RecyclerView.SCROLL_STATE_DRAGGING) {
                    requireActivity().hideSoftKeyboard()
                }
            }
        })
    }

    @OptIn(ExperimentalPagingApi::class)
    private fun initAdapter() {
        rv_main_users.adapter = userAdapter.withLoadStateHeaderAndFooter(
            header = UserLoadStateAdapter { userAdapter.retry() },
            footer = UserLoadStateAdapter { userAdapter.retry() }
        )

        userAdapter.addLoadStateListener { loadState ->
            when (val refreshState = loadState.refresh) {
                is LoadState.NotLoading -> {
                    rv_main_users.showSingle()

                    // Check if empty result
                    if (loadState.append.endOfPaginationReached && userAdapter.itemCount == 0) {
                        tv_main_info.text = getString(R.string.empty_result_info)
                        tv_main_info.visibility = View.VISIBLE
                    }
                }
                is LoadState.Loading -> {
                    pb_main_progress_bar.showSingle()
                }
                is LoadState.Error -> {
                    val errorMessage = refreshState.error.message.orEmpty()
                    tv_main_info.text = errorMessage

                    tv_main_info.showSingle()
                }
            }
        }

        lifecycleScope.launch {
            userAdapter.dataRefreshFlow.collect {
                rv_main_users.scrollToPosition(0)
            }
        }

        lifecycleScope.launch {
            viewModel.users.collectLatest {
                userAdapter.submitData(it)
            }
        }
    }

}
package com.example.myvk.presentation.ui.view.fragments.news

import android.content.Context
import android.graphics.Point
import android.os.Bundle
import android.view.*
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.myvk.databinding.FragmentNewsBinding
import com.example.myvk.presentation.ui.view.fragments.news.adapter.NewsAdapter

class NewsFragment : Fragment() {

    private val newsViewModel: NewsViewModel by viewModels()

    private lateinit var binding: FragmentNewsBinding
    private var newsAdapter: NewsAdapter = NewsAdapter()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        binding =
            FragmentNewsBinding.inflate(LayoutInflater.from(container?.context), container, false)

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        binding.recyclerNews.apply {
            layoutManager = LinearLayoutManager(context)

            val windowManager = this.context.getSystemService(Context.WINDOW_SERVICE) as WindowManager

            val display: Display = windowManager.defaultDisplay
            val point = Point()
            display.getSize(point)
            newsAdapter.setScreenWidth(point.x)
            adapter = newsAdapter
            /*val itemDecoration = DividerItemDecoration(this.context, DividerItemDecoration.VERTICAL)
            getDrawable(this.context, R.drawable.divider)?.let {
                itemDecoration.setDrawable(it)
                addItemDecoration(itemDecoration)
            }*/
        }
        super.onViewCreated(view, savedInstanceState)
    }

    override fun onHiddenChanged(hidden: Boolean) {
        if (!hidden) {
            newsViewModel.loadNews()
            newsViewModel.listNewsLiveData.observe(this, Observer {
                newsAdapter.setItems(it)
            })
        }
        super.onHiddenChanged(hidden)
    }
}
package com.fevziomurtekin.hackernewsapp.ui.news

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.fevziomurtekin.hackernewsapp.R
import com.fevziomurtekin.hackernewsapp.data.domain.ItemModel
import com.fevziomurtekin.hackernewsapp.ui.adapter.NewsAdapter
import com.fevziomurtekin.hackernewsapp.ui.main.MainActivity
import com.fevziomurtekin.hackernewsapp.ui.newdetails.NewDetailsFragment
import com.fevziomurtekin.hackernewsapp.ui.newdetails.NewsDetailsViewModel
import com.google.gson.GsonBuilder
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.naju_fragments.*
import org.greenrobot.eventbus.EventBus
import org.greenrobot.eventbus.Subscribe
import org.greenrobot.eventbus.ThreadMode
import org.koin.android.architecture.ext.viewModel
import timber.log.Timber

class NewsFragment : Fragment(), NewsAdapter.OnItemClickListener{

    private var onItemClick : NewsAdapter.OnItemClickListener? = null
    private val viewModel by viewModel<NewsViewModel>()

    override fun onAttach(context: Context?) {
        super.onAttach(context)
        EventBus.getDefault().register(this)
    }

    override fun onDetach() {
        super.onDetach()
        EventBus.getDefault().unregister(this)
    }

    /**
     * Defined and created EventBus.
     * Fetch Items and Update Recyclerview from usage EventBus.
     * @return MutableList<ItemModel>
     */

    @Subscribe(threadMode = ThreadMode.MAIN)
    fun onEventItems(items : MutableList<ItemModel>){
        Timber.d("${items.toString()}")
        recyclerview.adapter = NewsAdapter(context!!,items,onItemClick)
        progressbars.hide()

    }

    /**
     * replace new Activity when onClick in News adapter
     */

    override fun onItemClick(item: ItemModel) {
        Timber.d("${item.text}")
        (activity as MainActivity).replaceFragment(true,R.id.framelayout,NewDetailsFragment.newInstance(item))
    }



    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.naju_fragments,container,false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        progressbars.show()
        onItemClick = this
    }



    companion object {

        fun newInstance() : NewsFragment {
            val args = Bundle()
            return NewsFragment()
        }

    }
}
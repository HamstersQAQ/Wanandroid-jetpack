package com.yppcat.wanandroid.view.project

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.chad.library.adapter.base.BaseQuickAdapter
import com.yppcat.wanandroid.R
import kotlinx.android.synthetic.main.project_fragment.*

class ProjectFragment : Fragment() {

    companion object {
        fun newInstance() = ProjectFragment()
        private const val TAG = "ProjectFragment"
    }

    private lateinit var viewModel: ProjectViewModel
    private lateinit var mAdapter: ProjectAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.project_fragment, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProviders.of(this).get(ProjectViewModel::class.java)
        activity?.let {
            val layoutManager = StaggeredGridLayoutManager(2,RecyclerView.VERTICAL)
            mAdapter = ProjectAdapter(R.layout.item_project, viewModel.projectList)
            mAdapter.openLoadAnimation(BaseQuickAdapter.SCALEIN)
            mAdapter.setOnLoadMoreListener({
                viewModel.getCurrentPage()?.let { page ->
                    viewModel.loadProject(page + 1)
                }
            }, recyclerview)
            mAdapter.setOnItemClickListener { adapter, view, position ->
                val action = ProjectFragmentDirections.projectToDetail(viewModel.projectList[position].projectLink)
                findNavController().navigate(action)
            }
            recyclerview.layoutManager = layoutManager
            recyclerview.adapter = mAdapter
            recyclerview.addItemDecoration(GridSpaceItemDecoration())
            viewModel.projectLiveData.observe(viewLifecycleOwner, Observer { result ->
                val projectList  = result.getOrNull()
                if (viewModel.getCurrentPage() == 1) {
                    viewModel.projectList.clear()
                    viewModel.projectList.addAll(projectList?.datas!!)
                } else {
                    viewModel.projectList.addAll(projectList?.datas!!)
                }
                mAdapter.notifyDataSetChanged()
                mAdapter.loadMoreComplete()
            })
        }
    }

    override fun onResume() {
        super.onResume()
        viewModel.projectList.let {list ->
            if (list.isEmpty()){
                viewModel.loadProject(1)
            }
        }
    }
}

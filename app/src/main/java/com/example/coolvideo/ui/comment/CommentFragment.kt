package com.example.coolvideo.ui.comment

import androidx.lifecycle.ViewModelProviders
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager

import com.example.coolvideo.R
import com.example.coolvideo.data.repository.CommentRepository
import com.example.coolvideo.data.network.CoolVideoNetwork
import com.example.coolvideo.databinding.FragmentCommentBinding
import com.example.coolvideo.ui.video.VideoActivity

class CommentFragment : Fragment() {

    private lateinit var binding: FragmentCommentBinding
    private lateinit var commentViewModel: CommentViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding=
            DataBindingUtil.inflate(inflater,R.layout.fragment_comment,container,false)

        return binding.root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        commentViewModel=ViewModelProviders.of(this, CommentModelFactory(context!!,
            CommentRepository.getInstance(CoolVideoNetwork.getInstance())))
            .get(CommentViewModel::class.java)
        binding.commentViewModel=commentViewModel

        val adapter=CommentListAdapter()
        binding.commentList.adapter=adapter
        binding.commentList.layoutManager=LinearLayoutManager(activity)

        binding.setLifecycleOwner(this)
        commentViewModel.dataChanged.observe(this, Observer {
            // Update the cached copy of the words in the adapter.
            adapter.commentList=commentViewModel.comments
        })

        commentViewModel.favor.observe(this, Observer { favor->
            if (favor){
                binding.videoLike.setImageResource(R.drawable.favor_selected)
            }else{
                binding.videoLike.setImageResource(R.drawable.favor)
            }
        })

        commentViewModel.commentSubmit.observe(this, Observer{
//            binding.commentInput.setText("")
        })
        binding.videoTitle.text=(activity as VideoActivity).getVideoTitle()
        commentViewModel.videoId=(activity as VideoActivity).videoId
        commentViewModel.videoName=(activity as VideoActivity).videoTitle
        commentViewModel.videoUrl=(activity as VideoActivity).videoUrl
        commentViewModel.videoImgUrl=(activity as VideoActivity).videoImgUrl
        commentViewModel.getComment()
        commentViewModel.initFavor()
    }

    fun onSubmitComment(s: String) {
        commentViewModel.submitComment(s)
    }

}

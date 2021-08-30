package com.privexec.activity.presenter;

/**
 * Created by user28 on 5/1/18.
 */

public interface BasePostView extends BaseRecyclerView {

    void removePost(Integer adapterPosition);

    void clearList();

    void likePost(Integer postIndex, Integer likeStatus, Integer likeCount);
    void  updateFollowStatus(Integer postIndex, Integer status, String s);
  //  void followStatus(Integer postIndex, Integer status, String s);
}

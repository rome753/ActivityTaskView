package cc.rome753.activitytask.model;

import android.support.v4.app.Fragment;

import java.util.LinkedList;

import cc.rome753.activitytask.AUtils;

/**
 * Created by rome753 on 2017/3/31.
 */
public class FragmentInfo extends TaskInfo {

    private LinkedList<String> fragments;

    public FragmentInfo(int life, Fragment fragment){
        super(life, AUtils.getSimpleName(fragment.getActivity()), AUtils.getSimpleName(fragment));
        this.fragments = getAllFragments(fragment);
    }

    public LinkedList<String> getFragments() {
        return fragments;
    }

    private LinkedList<String> getAllFragments(Fragment fragment){
        LinkedList<String> res = new LinkedList<>();
        while(fragment != null){
            res.add(AUtils.getSimpleName(fragment));
            fragment = fragment.getParentFragment();
        }
        return res;
    }
}
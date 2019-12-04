package cc.rome753.demo;


import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.fragment.app.Fragment;


/**
 * A simple {@link Fragment} subclass.
 * Use the {@link DemoFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class DemoFragment extends Fragment {
    public DemoFragment() {
        // Required empty public constructor
    }

    public static DemoFragment newInstance(int index) {
        DemoFragment fragment = new DemoFragment();
        Bundle args = new Bundle();
        args.putInt("index", index);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            int index = getArguments().getInt("index");
            if (index == 2) {
                ChildFragment childFragment = ChildFragment.newInstance();
                getChildFragmentManager().beginTransaction().replace(R.id.fl, childFragment).commitAllowingStateLoss();
            }
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_demo, container, false);
        ((TextView)view.findViewById(R.id.tv)).setText(getClass().getSimpleName() + "@0x" + Integer.toHexString(hashCode()));
        return view;
    }

}

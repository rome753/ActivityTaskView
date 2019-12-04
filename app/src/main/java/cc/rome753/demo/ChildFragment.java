package cc.rome753.demo;


import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.fragment.app.Fragment;


/**
 * A simple {@link Fragment} subclass.
 * Use the {@link ChildFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class ChildFragment extends Fragment {
    public ChildFragment() {
        // Required empty public constructor
    }

    public static ChildFragment newInstance() {
        ChildFragment fragment = new ChildFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_child, container, false);
        ((TextView)view.findViewById(R.id.tv)).setText(getClass().getSimpleName() + "@x" + Integer.toHexString(hashCode()));
        return view;
    }

}

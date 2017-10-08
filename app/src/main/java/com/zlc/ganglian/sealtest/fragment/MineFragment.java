package com.zlc.ganglian.sealtest.fragment;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.zlc.ganglian.sealtest.R;
import com.zlc.ganglian.sealtest.constant.Constant;
import com.zlc.ganglian.sealtest.login.LoginActivity;

import io.rong.imkit.RongIM;


public class MineFragment extends Fragment implements View.OnClickListener {

    public static final String SHOW_RED = "SHOW_RED";

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private Button logout;

    public static MineFragment newInstance(String param1, String param2) {
        MineFragment fragment = new MineFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_mine, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        view.findViewById(R.id.logout).setOnClickListener(this);
    }

    @Override
    public void onDetach() {
        super.onDetach();
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.logout:
                RongIM.getInstance().disconnect();
                getActivity().getSharedPreferences("config", Context.MODE_PRIVATE)
                        .edit()
                        .putString(Constant.SP_TOKEN, "")
                        .apply();
                getActivity().getSharedPreferences("config", Context.MODE_PRIVATE)
                        .edit()
                        .putString(Constant.SP_CURR_UID, "")
                        .apply();
                getActivity().getSharedPreferences("config", Context.MODE_PRIVATE)
                        .edit()
                        .putString(Constant.SP_CURR_NAME, "")
                        .apply();
                startActivity(LoginActivity.createIntent(getActivity(), LoginActivity.class));
                getActivity().finish();
                break;
        }
    }
}

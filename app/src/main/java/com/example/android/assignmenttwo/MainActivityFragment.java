package com.example.android.assignmenttwo;

import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * A placeholder fragment containing a simple view.
 */
public class MainActivityFragment extends Fragment implements AsyncTaskListener {
    private static final String TAG = MainActivityFragment.class.getSimpleName();


    private TextView txtUserName;
    private TextView txtPostTime;
    private ImageView imgUserPic;
    private ImageView imgPostImage;
    private TextView txtPostText;
    private TextView txtLike;
    private TextView txtComment;
    private TextView txtShare;

    public MainActivityFragment() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        CustomAsyncTask customAsyncTask = new CustomAsyncTask(this);
        customAsyncTask.execute();


    }

    private void parseData(String data) {
        try {
            JSONObject jsonObject = new JSONObject(data);
            txtUserName.setText(jsonObject.getString("userName"));
            txtPostTime.setText(jsonObject.getString("postTime"));
            txtPostText.setText(jsonObject.getString("postText"));

            Picasso.with(getActivity()).load(jsonObject.getString("userPic")).fit().centerCrop()
                    .placeholder(R.drawable.abc_ic_star_black_36dp)
                    .error(R.drawable.abc_ic_star_black_36dp).into(imgUserPic);
            Picasso.with(getActivity()).load(jsonObject.getString("postImage")).fit().centerCrop()
                    .placeholder(R.drawable.abc_ic_star_black_36dp)
                    .error(R.drawable.abc_ic_star_black_36dp).into(imgPostImage);

            txtLike.setText(jsonObject.getString("likes"));
            txtComment.setText(jsonObject.getString("comments"));
            txtShare.setText(jsonObject.getString("shares"));

        } catch (JSONException e) {
            Toast.makeText(getActivity(), "There is something wrong in parsing: " + e.getMessage(), Toast.LENGTH_LONG).show();
//            e.printStackTrace();
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_main, container, false);
        txtUserName = (TextView) v.findViewById(R.id.txtUserName);
        txtPostTime = (TextView) v.findViewById(R.id.txtPostTime);
        txtPostText = (TextView) v.findViewById(R.id.txtPostText);
        imgUserPic = (ImageView) v.findViewById(R.id.imgUserPic);
        imgPostImage = (ImageView) v.findViewById(R.id.imgPostImage);

        txtLike = (TextView) v.findViewById(R.id.txtLike);
        txtComment = (TextView) v.findViewById(R.id.txtComment);
        txtShare = (TextView) v.findViewById(R.id.txtShare);
        return v;

    }


    @Override
    public void notifyUpdate(String data) {
        Log.v(TAG, "Implementing Interface to use AsyncTASK: " + data);
        parseData(data);
    }

    public void message(String data) {
        Toast.makeText(getActivity(), "There is something wrong in parsing: " + data, Toast.LENGTH_LONG).show();
    }
}

package com.example.seyaha;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;


public class HelpFeedbackFragment extends Fragment implements View.OnClickListener {

    TextView emergency_num, mot_num, mot_mail;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View mView = inflater.inflate(R.layout.fragment_helpfeedback, container, false);
        prepareView(mView);
        return mView;
    }

    public void prepareView(View mView) {
        emergency_num = mView.findViewById(R.id.emergency_num);
        mot_num = mView.findViewById(R.id.mot_num);
        mot_mail = mView.findViewById(R.id.mot_mail);

    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.emergency_num:

                String uri = "tel:" + "197777";
                Intent intent = new Intent(Intent.ACTION_DIAL);
                intent.setData(Uri.parse(uri));
                startActivity(intent);
                break;

            case R.id.mot_num:

                String uriNum = "tel:" + "064603360";
                Intent numIntent = new Intent(Intent.ACTION_DIAL);
                numIntent.setData(Uri.parse(uriNum));
                startActivity(numIntent);
                break;

            case R.id.mot_mail:

                Intent emailIntent = new Intent(Intent.ACTION_SENDTO, Uri.fromParts("mailto", "contact@mota.gov.jo", null));
                emailIntent.putExtra(Intent.EXTRA_SUBJECT, "Subject");
                emailIntent.putExtra(Intent.EXTRA_TEXT, "Body");
                startActivity(Intent.createChooser(emailIntent, "Send email..."));
                break;

        }

    }

}

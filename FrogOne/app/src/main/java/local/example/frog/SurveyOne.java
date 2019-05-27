/**
 *
 * Copyright 2019 paolo mococci
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * 	   http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 *
 */

package local.example.frog;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RadioGroup;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import static android.view.View.INVISIBLE;
import static android.view.View.VISIBLE;
import static local.example.frog.R.id;
import static local.example.frog.R.layout;
import static local.example.frog.R.string;


public class SurveyOne extends Fragment {

    private static final int YES = 0;
    private static final int NO = 1;

    public SurveyOne() {}

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container,
                             Bundle savedInstanceState) {

        final View rootView = inflater
                .inflate(layout.survey_one, container, false);
        final RadioGroup radioGroup = rootView
                .findViewById(id.radio_group);
        final TextView fragmentHeaderTwo = rootView
                .findViewById(id.fragment_header_two);
        final RatingBar ratingBar = rootView
                .findViewById(id.rating_bar);

        radioGroup.setOnCheckedChangeListener(
                new RadioGroup.OnCheckedChangeListener() {
                    @Override
                    public void onCheckedChanged(RadioGroup group, int checkedId) {
                        View radioButton = radioGroup.findViewById(checkedId);
                        int index = radioGroup.indexOfChild(radioButton);
                        TextView textView = rootView.findViewById(id.fragment_header_one);
                        switch (index) {
                            case YES:
                                textView.setText(string.yes_message);
                                fragmentHeaderTwo.setVisibility(VISIBLE);
                                ratingBar.setVisibility(VISIBLE);
                                break;
                            case NO:
                                textView.setText(string.no_message);
                                fragmentHeaderTwo.setVisibility(INVISIBLE);
                                ratingBar.setVisibility(INVISIBLE);
                                break;
                            default:
                                /* do nothing */
                                break;
                        }
                    }
                });

        ratingBar.setOnRatingBarChangeListener(new RatingBar.OnRatingBarChangeListener() {
            @Override
            public void onRatingChanged(RatingBar ratingBar, float rating, boolean fromUser) {
                String actualRating = (getString(string.rating_message) + ratingBar.getRating());
                Toast.makeText(getContext(), actualRating, Toast.LENGTH_SHORT).show();
            }
        });

        return rootView;
    }
}

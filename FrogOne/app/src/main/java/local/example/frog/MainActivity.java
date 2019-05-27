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
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import static local.example.frog.R.id;
import static local.example.frog.R.id.button_survey;
import static local.example.frog.R.layout;
import static local.example.frog.R.string;

public class MainActivity extends AppCompatActivity {

    private Button surveyButton;
    private Boolean isSurveyDisplayed = false;
    static final String STATE_OF_SURVEY = "state_of_survey";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(layout.activity_main);

        setActionContainerVisibility(false);

        surveyButton = findViewById(button_survey);

        if (savedInstanceState != null) {
            isSurveyDisplayed = savedInstanceState.getBoolean(STATE_OF_SURVEY);
            if (isSurveyDisplayed) {
                surveyButton.setText(string.close_text);
            }
        }

        surveyButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!isSurveyDisplayed) {
                    setActionContainerVisibility(true);
                    surveyButton.setText(string.close_text);
                    isSurveyDisplayed = true;
                } else {
                    setActionContainerVisibility(false);
                    surveyButton.setText(string.open_text);
                    isSurveyDisplayed = false;
                }
            }
        });
    }

    private void setActionContainerVisibility(boolean visibility) {
        LinearLayout linearLayout = findViewById(id.action_container);
        if (visibility) {
            linearLayout.setVisibility(View.VISIBLE);
        } else {
            linearLayout.setVisibility(View.INVISIBLE);
        }
    }

    public void onSaveInstanceState(@NonNull Bundle savedInstanceState) {
        savedInstanceState.putBoolean(STATE_OF_SURVEY, isSurveyDisplayed);
        super.onSaveInstanceState(savedInstanceState);
    }
}

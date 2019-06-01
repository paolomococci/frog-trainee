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

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.app.NavUtils;

import local.example.frog.content.ArticleContent;

import static local.example.frog.R.id.article_detail_container;
import static local.example.frog.R.id.detail_toolbar;
import static local.example.frog.R.layout.activity_article_detail;

public class ArticleDetailActivity
        extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(activity_article_detail);
        Toolbar toolbar = findViewById(detail_toolbar);
        setSupportActionBar(toolbar);

        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
        }

        if (savedInstanceState == null) {
            int articleItemSelected =
                    getIntent().getIntExtra(
                            ArticleContent.ARTICLE_ID_KEY,
                            0
                    );
            ArticleDetailFragment fragment =
                    ArticleDetailFragment.newInstance(articleItemSelected);
            getSupportFragmentManager()
                    .beginTransaction()
                    .add(article_detail_container, fragment)
                    .commit();
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem menuItem) {
        int id = menuItem.getItemId();
        if (id == android.R.id.home) {
            NavUtils.navigateUpTo(
                    this,
                    new Intent(this, MainActivity.class)
            );
            return true;
        }
        return super.onOptionsItemSelected(menuItem);
    }
}

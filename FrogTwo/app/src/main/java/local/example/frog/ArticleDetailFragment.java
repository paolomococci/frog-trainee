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
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import java.util.Objects;

import local.example.frog.content.ArticleContent;

import static local.example.frog.R.id.article_detail;
import static local.example.frog.R.layout;

public class ArticleDetailFragment
        extends Fragment {

    private ArticleContent.Article article;

    public ArticleDetailFragment() {}

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (Objects.requireNonNull(getArguments())
                .containsKey(ArticleContent.ARTICLE_ID_KEY)) {
            article = ArticleContent.ARTICLE_ITEMS.get(getArguments()
                    .getInt(ArticleContent.ARTICLE_ID_KEY));
        }
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(layout.article_detail,
                container, false);

        if (article != null) {
            ((TextView) rootView.findViewById(article_detail))
                    .setText(article.articleDetail);
        }

        return rootView;
    }

    static ArticleDetailFragment newInstance(int articleItemSelected) {
        ArticleDetailFragment articleDetailFragment = new ArticleDetailFragment();
        Bundle bundle = new Bundle();
        bundle.putInt(
                ArticleContent.ARTICLE_ID_KEY,
                articleItemSelected
        );
        articleDetailFragment.setArguments(bundle);
        return articleDetailFragment;
    }
}

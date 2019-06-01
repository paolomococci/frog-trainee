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

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import local.example.frog.content.ArticleContent;

import static local.example.frog.R.id;
import static local.example.frog.R.id.article_content;
import static local.example.frog.R.id.article_detail_container;
import static local.example.frog.R.id.article_id;
import static local.example.frog.R.id.article_item_list;
import static local.example.frog.R.layout;

public class MainActivity
        extends AppCompatActivity {

    private boolean panel = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(layout.activity_main);

        Toolbar toolbar = findViewById(id.toolbar);
        setSupportActionBar(toolbar);
        toolbar.setTitle(getTitle());

        RecyclerView recyclerView = findViewById(article_item_list);
        recyclerView.setAdapter
                (new SimpleItemRecyclerViewAdapter(ArticleContent.ARTICLE_ITEMS));

        if (findViewById(article_detail_container) != null) {
            panel = true;
        }
    }


    class SimpleItemRecyclerViewAdapter
            extends RecyclerView.Adapter
            <SimpleItemRecyclerViewAdapter.ViewHolder> {

        private final List<ArticleContent.Article> articleContentItems;

        SimpleItemRecyclerViewAdapter(List<ArticleContent.Article> items) {
            articleContentItems = items;
        }


        @NonNull
        @Override
        public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(parent.getContext())
                    .inflate(layout.article_list_content, parent, false);
            return new ViewHolder(view);
        }


        @Override
        public void onBindViewHolder(@NonNull final ViewHolder holder, int position) {
            holder.articleContentItem = articleContentItems.get(position);
            holder.idTextView.setText(String.valueOf(position + 1));
            holder.contentTextView.setText(articleContentItems.get(position).articleTitle);
            holder.view.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (panel) {
                        int articleItemSelected = holder.getAdapterPosition();
                        ArticleDetailFragment fragment =
                                ArticleDetailFragment.newInstance(articleItemSelected);
                        getSupportFragmentManager()
                                .beginTransaction()
                                .replace(article_detail_container, fragment)
                                .addToBackStack(null)
                                .commit();
                    } else {
                        Context context = v.getContext();
                        Intent intent = new Intent(context,
                                ArticleDetailActivity.class);
                        intent.putExtra(ArticleContent.ARTICLE_ID_KEY,
                                holder.getAdapterPosition());
                        context.startActivity(intent);
                    }
                }
            });
        }


        @Override
        public int getItemCount() {
            return articleContentItems.size();
        }

        class ViewHolder extends RecyclerView.ViewHolder {
            final View view;
            final TextView idTextView;
            final TextView contentTextView;
            ArticleContent.Article articleContentItem;

            ViewHolder(View view) {
                super(view);
                this.view = view;
                idTextView = view.findViewById(article_id);
                contentTextView = view.findViewById(article_content);
            }
        }
    }
}

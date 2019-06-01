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

package local.example.frog.content;

import java.util.ArrayList;
import java.util.List;

public class ArticleContent {

    /* This part should be replaced to retrieve content directly from the Internet. */

    public static final List<Article> ARTICLE_ITEMS = new ArrayList<>();
    public static final String ARTICLE_ID_KEY = "article_id_key";
    private static final String CONTENT_TO_WRITE = "\nContent to write...";
    private static final int COUNT = 8;

    public static class Article {
        public final String articleTitle;
        public final String articleDetail;

        private Article(String articleTitle, String articleDetail) {
            this.articleTitle = articleTitle;
            this.articleDetail = articleDetail;
        }
    }

    private static void addItem(Article article) {
        ARTICLE_ITEMS.add(article);
    }

    static {
        for (int i = 0; i < COUNT; i++) {
            addItem(listArticle(i));
        }
    }

    private static Article listArticle(int number) {
        String titleTemp;
        String detailTemp;
        switch (number) {
            case 0:
                titleTemp = "Title of chapter One";
                detailTemp = "This is some content of chapter one." + CONTENT_TO_WRITE;
                break;
            case 1:
                titleTemp = "Title of chapter Two";
                detailTemp = "This is some content of chapter two." + CONTENT_TO_WRITE;
                break;
            case 2:
                titleTemp = "Title of chapter Three";
                detailTemp = "This is some content of chapter three." + CONTENT_TO_WRITE;
                break;
            case 3:
                titleTemp = "Title of chapter Four";
                detailTemp = "This is some content of chapter four." + CONTENT_TO_WRITE;
                break;
            case 4:
                titleTemp = "Title of chapter Five";
                detailTemp = "This is some content of chapter five." + CONTENT_TO_WRITE;
                break;
            case 5:
                titleTemp = "Title of chapter Six";
                detailTemp = "This is some content of chapter six." + CONTENT_TO_WRITE;
                break;
            case 6:
                titleTemp = "Title of chapter Seven";
                detailTemp = "This is some content of chapter seven." + CONTENT_TO_WRITE;
                break;
            default:
                titleTemp = "Title of chapter Eight";
                detailTemp = "This is some content of chapter eight." + CONTENT_TO_WRITE;
                break;
        }
        return new Article(titleTemp, detailTemp);
    }
}

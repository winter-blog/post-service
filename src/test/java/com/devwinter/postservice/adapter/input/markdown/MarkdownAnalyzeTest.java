package com.devwinter.postservice.adapter.input.markdown;

import com.devwinter.postservice.application.markdown.MarkdownAnalyze;
import org.junit.jupiter.api.Test;

import java.util.List;


class MarkdownAnalyzeTest {

    MarkdownAnalyze markdownAnalyze = new MarkdownAnalyze();
    @Test
    void getImages() {
        String markdown = "![alt text](https://winter-blog-bucket.s3.ap-northeast-2.amazonaws.com/post/3fd928e9-4ff1-4338-829a-5be4cf7a14a0.png)\n" +
                "asdfadsjflkajsdlfjklasdf\n" +
                "adjsfjalsdkfj@@DSaflsadkjflkasdjfadsf\n" +
                "\n" +
                "--- adsfjadsf\n" +
                "##df adsfadsf\n" +
                "\n" +
                "# dfjaslkfjlasdjlfkas\n" +
                "\n" +
                "![alt text](https://winter-blog-bucket.s3.ap-northeast-2.amazonaws.com/post/59912fd9-26b0-4029-80ae-46787650c67a.png)\n" +
                "\n" +
                "adsfasdfasdf";
        List<String> images = markdownAnalyze.getImages(markdown);
        System.out.println();
    }

    @Test
    void getPlainContentsTest() {
        String markdown = "![alt text](https://winter-blog-bucket.s3.ap-northeast-2.amazonaws.com/post/3fd928e9-4ff1-4338-829a-5be4cf7a14a0.png)\n" +
                "asdfadsjflkajsdlfjklasdf\n" +
                "adjsfjalsdkfj@@DSaflsadkjflkasdjfadsf\n" +
                "\n" +
                "--- adsfjadsf\n" +
                "##df adsfadsf\n" +
                "\n" +
                "# dfjaslkfjlasdjlfkas\n" +
                "\n" +
                "![alt text](https://winter-blog-bucket.s3.ap-northeast-2.amazonaws.com/post/59912fd9-26b0-4029-80ae-46787650c67a.png)\n" +
                "\n" +
                "adsfasdfasdf";
        String contents = markdownAnalyze.getPlainContents(markdown);
        System.out.println();
    }
}
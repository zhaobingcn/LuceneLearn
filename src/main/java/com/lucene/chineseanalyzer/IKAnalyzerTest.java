package com.lucene.chineseanalyzer;

import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.analysis.TokenStream;
import org.apache.lucene.analysis.tokenattributes.OffsetAttribute;
import org.apache.lucene.analysis.tokenattributes.TermToBytesRefAttribute;
import org.wltea.analyzer.lucene.IKAnalyzer;

import java.io.StringReader;

/**
 * Created by zhzy on 2016/11/20.
 */
public class IKAnalyzerTest {
    //测试数据
    public static String testData = "基于Neo4j的大规模数据检索技术";

    public void analyzer() throws Exception{
        Analyzer analyzer = new IKAnalyzer();
        TokenStream tokenStream = analyzer.tokenStream("", new StringReader(testData));
        tokenStream.addAttribute(TermToBytesRefAttribute.class);
        System.out.println("分次数据" + testData);
        System.out.println("IK分词结果");
        OffsetAttribute offsetAttribute = tokenStream.addAttribute(OffsetAttribute.class);
        tokenStream.reset();
        while (tokenStream.incrementToken()){
            System.out.println(offsetAttribute.toString());
        }
        tokenStream.end();
        tokenStream.close();
    }

    public static void main(String[] args) throws Exception{
        IKAnalyzerTest ikAnalyzerTest = new IKAnalyzerTest();
        ikAnalyzerTest.analyzer();
    }
}

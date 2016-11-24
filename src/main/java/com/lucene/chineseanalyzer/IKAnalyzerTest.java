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
    public static String testData = "从2006年12月推出1.0版开始，IKAnalyzer已经推出 了3个大版本。最初，它是以开源项目Lucene为应用主体的，结合词典分词和文法分析算法的中文分词组件。新版本的IKAnalyzer3.0则发展为 面向Java的公用分词组件，独立于Lucene项目，同时提供了对Lucene的默认优化实现。";

    public void analyzer() throws Exception{
        Analyzer analyzer = new IKAnalyzer();
        TokenStream tokenStream = analyzer.tokenStream("", new StringReader(testData));
        tokenStream.addAttribute(TermToBytesRefAttribute.class);
        System.out.println("分次数据" + testData);
        System.out.println("IK分词结果");
        OffsetAttribute offsetAttribute = tokenStream.addAttribute(OffsetAttribute.class);
        tokenStream.reset();
        while (tokenStream.incrementToken()){
            System.out.print("[" + offsetAttribute.toString() + "]");
        }
        tokenStream.end();
        tokenStream.close();
    }

    public static void main(String[] args) throws Exception{
        IKAnalyzerTest ikAnalyzerTest = new IKAnalyzerTest();
        ikAnalyzerTest.analyzer();
    }
}

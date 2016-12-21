package com.lucene.chineseanalyzer;

import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.document.Document;
import org.apache.lucene.document.Field;
import org.apache.lucene.document.TextField;
import org.apache.lucene.index.DirectoryReader;
import org.apache.lucene.index.IndexReader;
import org.apache.lucene.index.IndexWriter;
import org.apache.lucene.index.IndexWriterConfig;
import org.apache.lucene.queryparser.classic.QueryParser;
import org.apache.lucene.search.IndexSearcher;
import org.apache.lucene.search.Query;
import org.apache.lucene.search.ScoreDoc;
import org.apache.lucene.search.TopDocs;
import org.apache.lucene.store.Directory;
import org.apache.lucene.store.FSDirectory;
import org.wltea.analyzer.lucene.IKAnalyzer;

import java.nio.file.Path;
import java.nio.file.Paths;

/**
 * Created by hexu on 2016/12/21.
 */
public class ChineseSearch {

    public void createIndex() throws Exception{
        /**
         * 选择语言分析器
         */
        Analyzer analyzer = new IKAnalyzer();
        /**
         * 索引文档存储地址
         */
        Directory directory = FSDirectory.open(Paths.get("./chineseindex"));
        /**
         * 配置索引库信息
         */
        IndexWriterConfig indexWriterConfig = new IndexWriterConfig(analyzer);
        /**
         * 创建IndexWriter,用来进行索引文件写入
         */
        IndexWriter indexWriter = new IndexWriter(directory, indexWriterConfig);
        /**
         * 准备写入的文档
         */
        String[] texts = new String[]{
                "Lucene是apache软件基金会4 jakarta项目组的一个子项目，是一个开放源代码的全文检索引擎工具包，",
                "但它不是一个完整的全文检索引擎，而是一个全文检索引擎的架构，提供了完整的查询引擎和索引引擎，",
                "部分文本分析引擎（英文与德文两种西方语言）。Lucene的目的是为软件开发人员提供一个简单易用的",
                "工具包，以方便的在目标系统中实现全文检索的功能，或者是以此为基础建立起完整的全文检索引擎",
                "。Lucene是一套用于全文检索和搜寻的开源程式库，由Apache软件基金会支持和提供。Lucene提供了",
                "一个简单却强大的应用程式接口，能够做全文索引和搜寻。在Java开发环境里Lucene是一个成熟的",
                "免费开源工具。就其本身而言，Lucene是当前以及最近几年最受欢迎的免费Java信息检",
                "索程序库。人们经常提到信息检索程序库，虽然与搜索引擎有关，但不应该将信息检索程序库与搜索引擎相混淆"
        };
        /**
         * 建立索引
         */
        for(String text: texts){
            Document document = new Document();
            document.add(new TextField("info", text, Field.Store.YES));
            indexWriter.addDocument(document);
        }
        indexWriter.close();
        directory.close();
    }

    public void query() throws Exception{
        /**
         * 选择词法分析和语法分析分析器
         */
        Analyzer analyzer = new IKAnalyzer();
        /**
         * 打开索引文件存放的位置
         */
        Directory directory = FSDirectory.open(Paths.get("./chineseindex"));
        /**
         * 建立IndexReader
         */
        IndexReader reader = DirectoryReader.open(directory);
        IndexSearcher indexSearcher = new IndexSearcher(reader);
        QueryParser queryParser = new QueryParser("info", analyzer);
        Query query = queryParser.parse("一个子项目");
        TopDocs topDocs = indexSearcher.search(query, 1000);
        System.out.println("总共匹配多少个：" + topDocs.totalHits);
        ScoreDoc[] hits = topDocs.scoreDocs;
        System.out.println("总共多少条数据：" + hits.length);
        for(ScoreDoc hit: hits){
            System.out.println("匹配的分：" + hit.score);
            System.out.println("文档索引id：" + hit.doc);
            Document document = indexSearcher.doc(hit.doc);
            System.out.println(document.get("info"));
        }
        reader.close();
        directory.close();
    }

    public static  void main(String[] args) throws Exception{
        ChineseSearch chineseSearch = new ChineseSearch();
//        chineseSearch.createIndex();
        chineseSearch.query();
    }
}

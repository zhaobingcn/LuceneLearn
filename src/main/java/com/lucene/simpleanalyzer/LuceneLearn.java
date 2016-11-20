package com.lucene.simpleanalyzer;

import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.analysis.standard.StandardAnalyzer;
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

import java.io.IOException;
import java.nio.file.Paths;

public class LuceneLearn{

    public void CreateIndex() throws Exception{
        //选择语言分析器
        Analyzer analyzer = new StandardAnalyzer();
        //索引文档的存储位置
        Directory directory = FSDirectory.open(Paths.get("./index"));
        //配置索引库信息
        IndexWriterConfig indexWriterConfig = new IndexWriterConfig(analyzer);
        //创建IndexWriter，用来进行索引文件的写入
        IndexWriter indexWriter = new IndexWriter(directory, indexWriterConfig);
        //准备写入Document的文档
        String[] texts = new String[]{
                "Apache Lucene is a high-performance",
                "full-featured text search engine library written entirely in Java",
                "It is a technology suitable for nearly any application that requires full-text search.",
                "Apache Lucene is an open source project available for free download. ",
                "Please use the links on the right to access Lucene."
        };
        //建立索引文档
        for(String text: texts){
            Document document = new Document();
            document.add(new TextField("info", text, Field.Store.YES));
            indexWriter.addDocument(document);
        }
        indexWriter.close();
        directory.close();
    }

    public void Query() throws Exception{
        //选择词法分析和语法分析分析器
        Analyzer analyzer = new StandardAnalyzer();
        //打开索引文件存放位置
        Directory directory = FSDirectory.open(Paths.get("./index"));
        //建立IndexReader
        IndexReader reader = DirectoryReader.open(directory);
        IndexSearcher indexSearcher = new IndexSearcher(reader);
        QueryParser queryParser = new QueryParser("info", analyzer);
        Query query = queryParser.parse("lucene");
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

    public static void main(String[] args) throws Exception{
        LuceneLearn lucene = new LuceneLearn();
//        lucene.CreateIndex();
        lucene.Query();
    }
}
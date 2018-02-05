package tk.chuanjing.elasticsearch_springdata.domain;

import org.springframework.data.annotation.Id;
import org.springframework.data.elasticsearch.annotations.Document;
import org.springframework.data.elasticsearch.annotations.Field;
import org.springframework.data.elasticsearch.annotations.FieldIndex;
import org.springframework.data.elasticsearch.annotations.FieldType;

@Document(indexName = "blog3", type = "article")
public class Article {
	/*
	 * 注解作用说明： 
		@Field(index = FieldIndex.not_analyzed, store = true, type = FieldType.Long) 
			index： 
				FieldIndex.not_analyzed：不会分词，只能根据原词索引Field; 
				FieldIndex.analyzed：根据分词器分词，可以根据原词和分词后的词条索引Field; 
				FieldIndex.no：该字段不会被索引，查不到;
			store：es是否会存储这个Field的内容;
			type：es按照什么数据类型解析这个Field;
	 */
	@Id
	@Field(index = FieldIndex.not_analyzed, store = true, type = FieldType.Integer)
	private Integer id;
	
	@Field(index = FieldIndex.analyzed, analyzer = "ik", store = true, searchAnalyzer = "ik", type = FieldType.String)
	private String title;
	
	@Field(index = FieldIndex.analyzed, analyzer = "ik", store = true, searchAnalyzer = "ik", type = FieldType.String)
	private String content;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	@Override
	public String toString() {
		return "Article [id=" + id + ", title=" + title + ", content="
				+ content + "]";
	}

}

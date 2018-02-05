package tk.chuanjing.elasticsearch_springdata.service.test;

import org.elasticsearch.client.Client;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.elasticsearch.core.ElasticsearchTemplate;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import tk.chuanjing.elasticsearch_springdata.domain.Article;
import tk.chuanjing.elasticsearch_springdata.service.ArticleService;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = "classpath:applicationContext.xml")
public class ArticleServiceTest {
	@Autowired
	private ArticleService articleService;

	@Autowired
	private Client client; // 基于原生API

	@Autowired
	private ElasticsearchTemplate elasticsearchTemplate;

	@Test
	public void createIndex() {
		elasticsearchTemplate.createIndex(Article.class);
		elasticsearchTemplate.putMapping(Article.class);
	}

	@Test
	public void testSave() {
		Article article = new Article();
		article.setId(1001);
		article.setTitle("香山居士");
		article.setContent("白居易（772年－846年），字乐天，号香山居士，又号醉吟先生，祖籍太原，到其曾祖父时迁居下邽，生于河南新郑。是唐代伟大的现实主义诗人，唐代三大诗人之一。白居易与元稹共同倡导新乐府运动，世称“元白”，与刘禹锡并称“刘白”");

		articleService.save(article);
	}

	@Test
	public void testDelete() {
		Article article = new Article();
		article.setId(1001);

		articleService.delete(article);
	}

	@Test
	public void testFindOne() {
		System.out.println(articleService.findOne(1001));
	}

	/**
	 * 批量添加点数据，方便测试  排序和分页查询
	 */
	@Test
	public void testSaveBatch() {
		for (int i = 1; i <= 100; i++) {
			Article article = new Article();
			article.setId(i);
			article.setTitle(i + "香山居士");
			article.setContent(i + "白居易（772年－846年），字乐天，号香山居士，又号醉吟先生，祖籍太原，到其曾祖父时迁居下邽，生于河南新郑。是唐代伟大的现实主义诗人，唐代三大诗人之一。白居易与元稹共同倡导新乐府运动，世称“元白”，与刘禹锡并称“刘白”");

			articleService.save(article);
		}
	}

	/**
	 * 排序分页查询
	 */
	@Test
	public void testSortAndPaging() {
		// 排序
//		Iterable<Article> articles = articleService.findAll();
//		for (Article article : articles) {
//			System.out.println(article);
//		}

		// 分页
		Pageable pageable = new PageRequest(0, 10);
		Page<Article> pageData = articleService.findAll(pageable);
		for (Article article : pageData.getContent()) {
			System.out.println(article);
		}
	}

	/**
	 * 条件查询
	 */
	@Test
	public void testConditionQuery() {
		// 查询 标题中含有 “居士”
//		List<Article> articles = articleService.findByTitle("居士");
//		for (Article article : articles) {
//			System.out.println(article);
//		}

		// 查询 标题中含有 “居士” 1-10条
		Pageable pageable = new PageRequest(0, 10);
		Page<Article> pageData = articleService.findByTitle("居士", pageable);
		System.out.println("总记录数：" + pageData.getTotalElements());
		for (Article article : pageData.getContent()) {
			System.out.println(article);
		}
	}
}
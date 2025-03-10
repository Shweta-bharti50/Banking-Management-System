package com.Blog.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.Blog.entity.Article;
import com.Blog.service.ArticleService;

@RestController
@RequestMapping("/article")
public class ArticleController
{
	@Autowired
    private ArticleService articleService;
	
	@GetMapping
	public List<Article> getAllArticles()
	{
		return articleService.getAllArticles();
	}
	
	@GetMapping("/{id}")
	public Article getArticle(@PathVariable Long id)
	{
		return articleService.getArticleById(id);
	}
	
	@PostMapping
	public Article createArticle(@RequestBody Article article)
	{
		return articleService.saveArticle(article);
	}
	
	@PutMapping("/{id}")
	public Article updateArticle(@PathVariable Long id,@RequestBody Article article)
	{
		article.setId(id);
		return articleService.saveArticle(article);
	}
	
	@DeleteMapping("/{id}")
	public void deleteArticle(@PathVariable Long id)
	{
		articleService.deleteArticle(id);
	}
}

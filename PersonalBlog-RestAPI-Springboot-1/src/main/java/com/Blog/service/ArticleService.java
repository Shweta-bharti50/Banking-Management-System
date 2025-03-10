package com.Blog.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.Blog.entity.Article;
import com.Blog.repository.ArticleRepository;

@Service
public class ArticleService 
{
	@Autowired
    private ArticleRepository articleRepository;
	
	public List<Article> getAllArticles()
	{
		return articleRepository.findAll();
	}
	
	public Article getArticleById(Long id)
	{
		return articleRepository.findById(id).orElse(null);
	}
	
	public Article saveArticle(Article article)
	{
		return articleRepository.save(article);
	}
	
	public void deleteArticle(Long id)
	{
		articleRepository.deleteById(id);
	}
}

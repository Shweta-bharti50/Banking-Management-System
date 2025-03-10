package com.Blog.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.Blog.entity.Article;

public interface ArticleRepository extends JpaRepository<Article, Long>
{

}

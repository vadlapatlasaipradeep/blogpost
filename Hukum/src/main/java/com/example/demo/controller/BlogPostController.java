package com.example.demo.controller;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.BlogPost;
import com.example.demo.BlogPostRepository;

@RestController
@RequestMapping("/api/blogposts")
public class BlogPostController {
	private final BlogPostRepository blogPostRepository;
	
	public BlogPostController(BlogPostRepository blogPostRepository) {
		this.blogPostRepository=blogPostRepository;
	}
	
	@PostMapping
	public BlogPost createBlogPost(@RequestBody BlogPost blogPost) {
		return blogPostRepository.save(blogPost);
	}
	
	@GetMapping("/{id}")
	public ResponseEntity<BlogPost> getBlogPostById(@PathVariable Long id){
		 java.util.Optional<BlogPost> optionalBlogPost = blogPostRepository.findById(id);
	        return optionalBlogPost.map(ResponseEntity::ok).orElse(ResponseEntity.notFound().build());
	}
	@PutMapping("/{id}")
	public ResponseEntity<BlogPost> updateBlogPost(@PathVariable Long id, @RequestBody BlogPost updatedBlogPost) {
        java.util.Optional<BlogPost> optionalBlogPost = blogPostRepository.findById(id);
        if (optionalBlogPost.isPresent()) {
            BlogPost existingBlogPost = optionalBlogPost.get();
            existingBlogPost.setTitle(updatedBlogPost.getTitle());
            existingBlogPost.setContent(updatedBlogPost.getContent());
            return ResponseEntity.ok(blogPostRepository.save(existingBlogPost));
        } else {
            return ResponseEntity.notFound().build();
        }
    }
	@DeleteMapping("/{id}")
	public ResponseEntity<Void> deleteBlogPost(@PathVariable Long id) {
        blogPostRepository.deleteById(id);
        return ResponseEntity.noContent().build();
    }
	

	

}

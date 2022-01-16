package org.training.campus.blog;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import java.util.List;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.training.campus.blog.api.PostController;
import org.training.campus.blog.model.Post;
import org.training.campus.blog.service.PostService;

class LuxCampus17ApplicationTests {
	
	@Mock
	private PostService postService;
	
	@InjectMocks
	private PostController postController;
	
	private MockMvc mvc;
	private AutoCloseable closeable;
	
	@BeforeEach
	private void init() {
		closeable = MockitoAnnotations.openMocks(this);	
		mvc = MockMvcBuilders.standaloneSetup(postController).build();
	}
	
	@AfterEach
	private void destroy() throws Exception {
		closeable.close();
	}

	@Test
	void testListOfAllPosts() throws Exception {
		final List<Post> sampleData = List.of(
				Post.builder().id(1).title("Most talented person I've ever met").content("I've met her today while walking in the street.").build(),
				Post.builder().id(2).title("Most valuable dish I've ever served").content("Cold salmon topped with fried onions and soaked with white wine").build());
		
		when(postService.findAll()).thenReturn(sampleData);
		
		mvc.perform(get("/api/v1/posts")).
			andExpectAll(
					status().isOk(),					
					content().contentType(MediaType.APPLICATION_JSON),
					content().json("""
						[
						    {
						        "id": 1,
						        "title": "Most talented person I've ever met",
						        "content": "I've met her today while walking in the street."
						    },
						    {
						        "id": 2,
						        "title": "Most valuable dish I've ever served",
						        "content": "Cold salmon topped with fried onions and soaked with white wine"
						    }
						]							
							"""));
	}

}

package org.training.campus.blog;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doAnswer;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.mockito.Mockito.anyMap;
import static org.mockito.Mockito.anyList;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.junit.jupiter.api.Assertions.assertEquals;
import java.util.List;
import java.util.Optional;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
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

	@Captor
	private ArgumentCaptor<Post> postCaptor;
	@Captor
	private ArgumentCaptor<Long> idCaptor;

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
	@DisplayName("test for all posts listing")
	void testListOfAllPosts() throws Exception {
		final List<Post> sampleData = List.of(
				Post.builder().id(1L).title("Most talented person I've ever met")
						.content("I've met her today while walking in the street.").build(),
				Post.builder().id(2L).title("Most valuable dish I've ever served")
						.content("Cold salmon topped with fried onions and soaked with white wine").build());

		when(postService.findAll()).thenReturn(sampleData);

		mvc.perform(get("/api/v1/posts")).andExpectAll(status().isOk(),
				content().contentType(MediaType.APPLICATION_JSON), content().json("""
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

	@Test
	@DisplayName("test for all posts with given title")
	void testListOfPostsWithGivenTitle() throws Exception {
		final List<Post> sampleData = List.of(
				Post.builder().id(1L).title("Most talented person I've ever met")
						.content("I've met her today while walking in the street.").build());

		when(postService.findAll(anyMap(),anyList())).thenReturn(sampleData);

		final String sampleTitle = "Most talented person I've ever met";
		mvc.perform(get("/api/v1/posts?title={sampleTitle}", sampleTitle)).andExpectAll(status().isOk(),
				content().contentType(MediaType.APPLICATION_JSON), content().json("""
						    {
						        "id": 1,
						        "title": "Most talented person I've ever met",
						        "content": "I've met her today while walking in the street."
						    }
							"""));
	}

	@Test
	@DisplayName("test for all posts sorted by title")
	void testListOfPostsSortedByTitle() throws Exception {
		final List<Post> sampleData = List.of(
				Post.builder().id(1L).title("Most valuable dish I've ever served")
				.content("Cold salmon topped with fried onions and soaked with white wine").build(),
				Post.builder().id(2L).title("Most talented person I've ever met")
						.content("I've met her today while walking in the street.").build());

		when(postService.findAll(anyMap(),anyList())).thenReturn(sampleData);

		mvc.perform(get("/api/v1/posts?sort=title")).andExpectAll(status().isOk(),
				content().contentType(MediaType.APPLICATION_JSON), content().json("""
						[
						    {
						        "id": 2,
						        "title": "Most talented person I've ever met",
						        "content": "I've met her today while walking in the street."
						    },
						    {
						        "id": 1,
						        "title": "Most valuable dish I've ever served",
						        "content": "Cold salmon topped with fried onions and soaked with white wine"
						    }
						]
							"""));
	}

	@Test
	@DisplayName("test for one post listing that's found successfully")
	void testListOfOnePostSuccess() throws Exception {
		final Long id = 1L;
		final Post samplePost = Post.builder().id(id).title("Most talented person I've ever met")
				.content("I've met her today while walking in the street.").build();

		when(postService.findById(id)).thenReturn(Optional.of(samplePost));

		mvc.perform(get("/api/v1/posts/{id}", id)).andExpectAll(status().isOk(),
				content().contentType(MediaType.APPLICATION_JSON), content().json("""
						   {
						       "id": 1,
						       "title": "Most talented person I've ever met",
						       "content": "I've met her today while walking in the street."
						   }
						"""));

	}

	@Test
	@DisplayName("test for one post listing that's not found")
	void testListOfOnePostFail() throws Exception {
		final Long id = 1L;
		when(postService.findById(id)).thenReturn(Optional.empty());

		mvc.perform(get("/api/v1/posts/{id}", id)).andExpectAll(status().isOk(), content().bytes("".getBytes()));
	}

	@Test
	@DisplayName("test for post addition")
	void testAddPost() throws Exception {
		final Long id = 1L;

		doAnswer(invocation -> {
			Post p = (Post) invocation.getArgument(0);
			p.setId(id);
			return p;
		}).when(postService).save(any(Post.class));

		mvc.perform(post("/api/v1/posts").contentType(MediaType.APPLICATION_JSON).content("""
				   {
				      "title": "Top-ranking story",
				      "content": "This is most amazing story I've ever read in my life!"
				  }
				""").accept(MediaType.APPLICATION_JSON)).andExpectAll(status().isOk(),
				content().string(String.valueOf(id)));

		verify(postService).save(postCaptor.capture());
		assertEquals("Top-ranking story", postCaptor.getValue().getTitle());
		assertEquals("This is most amazing story I've ever read in my life!", postCaptor.getValue().getContent());
		assertEquals(id, postCaptor.getValue().getId());
	}

	@Test
	@DisplayName("test for post modification")
	void testModifyPost() throws Exception {
		final Long id = 1L;

		doAnswer(invocation -> {
			Long postId = (Long) invocation.getArgument(0);
			Post p = (Post) invocation.getArgument(1);
			p.setId(postId);
			return p;
		}).when(postService).save(any(Long.class), any(Post.class));

		mvc.perform(put("/api/v1/posts/{id}", id).contentType(MediaType.APPLICATION_JSON).content("""
				   {
				      "title": "Top-ranking story",
				      "content": "This is most amazing story I've ever read in my life!"
				  }
				""").accept(MediaType.APPLICATION_JSON)).andExpectAll(status().isOk(), content().bytes("".getBytes()));

		verify(postService).save(idCaptor.capture(), postCaptor.capture());
		assertEquals("Top-ranking story", postCaptor.getValue().getTitle());
		assertEquals("This is most amazing story I've ever read in my life!", postCaptor.getValue().getContent());
		assertEquals(id, idCaptor.getValue());
		assertEquals(id, postCaptor.getValue().getId());
	}

	@Test
	@DisplayName("test for post deletion")
	void testDeletePost() throws Exception {
		final Long id = 1L;

		mvc.perform(delete("/api/v1/posts/{id}", id).contentType(MediaType.APPLICATION_JSON).content("")
				.accept(MediaType.APPLICATION_JSON)).andExpectAll(status().isOk(), content().bytes("".getBytes()));

		verify(postService).deleteById(idCaptor.capture());
		assertEquals(id, idCaptor.getValue());
	}

}

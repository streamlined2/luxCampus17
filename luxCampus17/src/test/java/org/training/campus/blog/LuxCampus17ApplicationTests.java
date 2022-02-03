package org.training.campus.blog;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.mockito.Mockito.eq;
import static org.mockito.Mockito.anyLong;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.junit.jupiter.api.Assertions.assertEquals;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Answers;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.training.campus.blog.api.CommentController;
import org.training.campus.blog.api.PostController;
import org.training.campus.blog.api.TagController;
import org.training.campus.blog.dto.CommentDto;
import org.training.campus.blog.dto.CommentMapper;
import org.training.campus.blog.dto.PostCommentMapper;
import org.training.campus.blog.dto.PostDto;
import org.training.campus.blog.dto.PostMapper;
import org.training.campus.blog.dto.TagDto;
import org.training.campus.blog.dto.TagMapper;
import org.training.campus.blog.model.Comment;
import org.training.campus.blog.model.Post;
import org.training.campus.blog.service.CommentService;
import org.training.campus.blog.service.PostService;
import org.training.campus.blog.service.TagService;

class LuxCampus17ApplicationTests {

	@Mock(answer = Answers.CALLS_REAL_METHODS)
	private PostMapper postMapper;
	@Mock(answer = Answers.CALLS_REAL_METHODS)
	private CommentMapper commentMapper;
	@Mock(answer = Answers.CALLS_REAL_METHODS)
	private TagMapper tagMapper;
	@InjectMocks
	private PostCommentMapper postCommentMapper;

	@Mock
	private PostService postService;
	@Mock
	private CommentService commentService;
	@Mock
	private TagService tagService;

	@InjectMocks
	private PostController postController;
	@InjectMocks
	private CommentController commentController;
	@InjectMocks
	private TagController tagController;

	private MockMvc mvc;
	private AutoCloseable mocksClosableResource;

	@Captor
	private ArgumentCaptor<Long> postIdCaptor;
	@Captor
	private ArgumentCaptor<Long> tagIdCaptor;
	@Captor
	private ArgumentCaptor<PostDto> postCaptor;
	@Captor
	private ArgumentCaptor<CommentDto> commentCaptor;
	@Captor
	private ArgumentCaptor<TagDto> tagCaptor;

	@BeforeEach
	private void init() {
		mocksClosableResource = MockitoAnnotations.openMocks(this);
		mvc = MockMvcBuilders.standaloneSetup(postController, commentController, tagController).build();
	}

	@AfterEach
	private void destroy() throws Exception {
		mocksClosableResource.close();
	}

	@Test
	@DisplayName("test for all posts listing")
	void testListOfAllPosts() throws Exception {
		final List<PostDto> sampleData = List.of(
				new PostDto(1L, "Most talented person I've ever met", "I've met her today while walking in the street.",
						false),
				new PostDto(2L, "Most valuable dish I've ever served",
						"Cold salmon topped with fried onions and soaked with white wine", false));

		when(postService.findAll(any(), any())).thenReturn(sampleData);

		mvc.perform(get("/api/v1/posts")).andExpectAll(status().isOk(),
				content().contentType(MediaType.APPLICATION_JSON), content().json("""
						[
						    {
						        "id": 1,
						        "title": "Most talented person I've ever met",
						        "content": "I've met her today while walking in the street.",
						        "star": false
						    },
						    {
						        "id": 2,
						        "title": "Most valuable dish I've ever served",
						        "content": "Cold salmon topped with fried onions and soaked with white wine",
						        "star": false
						    }
						]
							"""));
	}

	@Test
	@DisplayName("test for all posts with given title")
	void testListOfPostsWithGivenTitle() throws Exception {
		final String sampleTitle = "Most talented person I've ever met";
		final List<PostDto> sampleData = List
				.of(new PostDto(1L, sampleTitle, "I've met her today while walking in the street.", false));

		when(postService.findAll(any(), any())).thenReturn(sampleData);

		mvc.perform(get("/api/v1/posts?title={sampleTitle}", sampleTitle)).andExpectAll(status().isOk(),
				content().contentType(MediaType.APPLICATION_JSON), content().json("""
						[
						   {
						       "id": 1,
						       "title": "Most talented person I've ever met",
						       "content": "I've met her today while walking in the street.",
						        "star": false
						   }
						]
						"""));
	}

	@Test
	@DisplayName("test for all posts sorted by title")
	void testListOfPostsSortedByTitle() throws Exception {
		final List<PostDto> sampleData = List.of(
				new PostDto(1L, "Most valuable dish I've ever served",
						"Cold salmon topped with fried onions and soaked with white wine", false),
				new PostDto(2L, "Most talented person I've ever met", "I've met her today while walking in the street.",
						false));

		when(postService.findAll(any(), any())).thenReturn(sampleData);

		mvc.perform(get("/api/v1/posts?sort=title")).andExpectAll(status().isOk(),
				content().contentType(MediaType.APPLICATION_JSON), content().json("""
						[
						    {
						        "id": 2,
						        "title": "Most talented person I've ever met",
						        "content": "I've met her today while walking in the street.",
						        "star": false
						    },
						    {
						        "id": 1,
						        "title": "Most valuable dish I've ever served",
						        "content": "Cold salmon topped with fried onions and soaked with white wine",
						        "star": false
						    }
						]
							"""));
	}

	@Test
	@DisplayName("test for one post listing that's found successfully")
	void testListOfOnePostSuccess() throws Exception {
		final Long id = 1L;
		final PostDto samplePost = new PostDto(id, "Most talented person I've ever met",
				"I've met her today while walking in the street.", false);

		when(postService.findById(id)).thenReturn(Optional.of(samplePost));

		mvc.perform(get("/api/v1/posts/{id}", id)).andExpectAll(status().isOk(),
				content().contentType(MediaType.APPLICATION_JSON), content().json("""
						   {
						       "id": 1,
						       "title": "Most talented person I've ever met",
						       "content": "I've met her today while walking in the street.",
						       "star": false
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
		final PostDto postDto = new PostDto(id, "Top-ranking story",
				"This is most amazing story I've ever read in my life!", false);
		when(postService.save(any(PostDto.class))).thenReturn(postDto);

		mvc.perform(post("/api/v1/posts").contentType(MediaType.APPLICATION_JSON).content("""
				   {
				      "title": "Top-ranking story",
				      "content": "This is most amazing story I've ever read in my life!",
					  "star": false
				  }
				""").accept(MediaType.APPLICATION_JSON)).andExpectAll(status().isOk(),
				content().string(String.valueOf(id)));

		verify(postService).save(postCaptor.capture());
		assertEquals("Top-ranking story", postCaptor.getValue().title());
		assertEquals("This is most amazing story I've ever read in my life!", postCaptor.getValue().content());
		assertEquals(false, postCaptor.getValue().star());
	}

	@Test
	@DisplayName("test for post modification")
	void testModifyPost() throws Exception {
		final Long id = 1L;
		final PostDto postDto = new PostDto(id, "Top-ranking story",
				"This is most amazing story I've ever read in my life!", false);
		when(postService.save(any(Long.class), any(PostDto.class))).thenReturn(postDto);

		mvc.perform(put("/api/v1/posts/{id}", id).contentType(MediaType.APPLICATION_JSON).content("""
				   {
				      "title": "Top-ranking story",
				      "content": "This is most amazing story I've ever read in my life!",
					  "star": false
				  }
				""").accept(MediaType.APPLICATION_JSON)).andExpectAll(status().isOk(), content().bytes("".getBytes()));

		verify(postService).save(postIdCaptor.capture(), postCaptor.capture());
		assertEquals(id, postIdCaptor.getValue());
		assertEquals("Top-ranking story", postCaptor.getValue().title());
		assertEquals("This is most amazing story I've ever read in my life!", postCaptor.getValue().content());
		assertEquals(false, postCaptor.getValue().star());
	}

	@Test
	@DisplayName("test for post deletion")
	void testDeletePost() throws Exception {
		final Long id = 1L;

		mvc.perform(delete("/api/v1/posts/{id}", id).contentType(MediaType.APPLICATION_JSON).content("")
				.accept(MediaType.APPLICATION_JSON)).andExpectAll(status().isOk(), content().bytes("".getBytes()));

		verify(postService).deleteById(postIdCaptor.capture());
		assertEquals(id, postIdCaptor.getValue());
	}

	@Test
	@DisplayName("test for starred posts listing")
	void testGetAllTops() throws Exception {
		final List<PostDto> sampleData = List.of(new PostDto(1L, "Most talented person I've ever met",
				"I've met her today while walking in the street.", true));

		when(postService.findAllStarred()).thenReturn(sampleData);

		mvc.perform(get("/api/v1/posts/star")).andExpectAll(status().isOk(),
				content().contentType(MediaType.APPLICATION_JSON), content().json("""
						[
						    {
						        "id": 1,
						        "title": "Most talented person I've ever met",
						        "content": "I've met her today while walking in the street.",
						        "star": true
						    }
						]
							"""));
	}

	@Test
	@DisplayName("test for post marking as starred")
	void testMarkAsTop() throws Exception {
		final Long id = 1L;
		when(postService.placeMark(eq(id), eq(true))).thenReturn(true);

		mvc.perform(put("/api/v1/posts/{id}/star", id).contentType(MediaType.APPLICATION_JSON).content("")
				.accept(MediaType.APPLICATION_JSON))
				.andExpectAll(status().isOk(), content().string(Boolean.TRUE.toString()));

		verify(postService).placeMark(postIdCaptor.capture(), eq(true));
		assertEquals(id, postIdCaptor.getValue());
	}

	@Test
	@DisplayName("test for removing starred marking")
	void testRemoveTopMark() throws Exception {
		final Long id = 1L;
		when(postService.placeMark(eq(id), eq(false))).thenReturn(true);

		mvc.perform(delete("/api/v1/posts/{id}/star", id).contentType(MediaType.APPLICATION_JSON).content("")
				.accept(MediaType.APPLICATION_JSON))
				.andExpectAll(status().isOk(), content().string(Boolean.TRUE.toString()));

		verify(postService).placeMark(postIdCaptor.capture(), eq(false));
		assertEquals(id, postIdCaptor.getValue());
	}

	@Test
	@DisplayName("test all comments listing for given post")
	void testListOfCommentsForGivenPost() throws Exception {
		final PostDto samplePost = new PostDto(1L, "Most talented person I've ever met",
				"I've met her today while walking in the street.", false);
		final List<CommentDto> comments = List.of(new CommentDto(1L, "Comment#1", LocalDate.of(2020, 10, 01)),
				new CommentDto(2L, "Comment#2", LocalDate.of(2021, 01, 02)),
				new CommentDto(3L, "Comment#3", LocalDate.of(2021, 03, 05)));

		when(commentService.getCommentsForPost(samplePost.id())).thenReturn(comments);

		mvc.perform(get("/api/v1/posts/{postId}/comments", samplePost.id())).andExpectAll(status().isOk(),
				content().contentType(MediaType.APPLICATION_JSON), content().json("""
						[
						   {
						       "id": 1,
						       "text": "Comment#1",
						       "creationDate": "2020-10-01"
						   },
						   {
						       "id": 2,
						       "text": "Comment#2",
						       "creationDate": "2021-01-02"
						   },
						   {
						       "id": 3,
						       "text": "Comment#3",
						       "creationDate": "2021-03-05"
						   }
						]
						"""));
	}

	@Test
	@DisplayName("test comment listing with given post and comment ids")
	void testCommentForGivenPostCommentIds() throws Exception {
		final PostDto samplePost = new PostDto(1L, "Most talented person I've ever met",
				"I've met her today while walking in the street.", false);
		final Optional<CommentDto> comment = Optional.of(new CommentDto(1L, "Comment#1", LocalDate.of(2020, 10, 01)));

		when(commentService.getCommentForPost(samplePost.id(), comment.get().id())).thenReturn(comment);

		mvc.perform(get("/api/v1/posts/{postId}/comments/{commentId}", samplePost.id(), comment.get().id()))
				.andExpectAll(status().isOk(), content().contentType(MediaType.APPLICATION_JSON), content().json("""
						   {
						       "id": 1,
						       "text": "Comment#1",
						       "creationDate": "2020-10-01"
						   }
						"""));
	}

	@Test
	@DisplayName("test for comment addition")
	void testAddComment() throws Exception {
		final Long postId = 1L;
		final Long commentId = 10L;
		final LocalDate creationDate = LocalDate.of(2020, 01, 01);
		final CommentDto commentDto = new CommentDto(commentId, "Never-ending story about happy life...", creationDate);
		when(commentService.add(anyLong(), any(CommentDto.class))).thenReturn(Optional.of(commentDto));

		mvc.perform(post("/api/v1/posts/{postId}/comments", postId).contentType(MediaType.APPLICATION_JSON).content("""
				   {
				      "text": "Never-ending story about happy life..."
				  }
				""").accept(MediaType.APPLICATION_JSON)).andExpectAll(status().isOk(),
				content().string(String.valueOf(commentId)));

		verify(commentService).add(anyLong(), commentCaptor.capture());
		assertEquals("Never-ending story about happy life...", commentCaptor.getValue().text());
		assertEquals(creationDate, commentDto.creationDate());
		assertEquals(commentId, commentDto.id());
	}

	@Test
	@DisplayName("test for comment modification")
	void testModifyComment() throws Exception {
		final Long postId = 1L;
		final Post post = Post.builder().id(postId).build();
		final Long commentId = 10L;
		final LocalDate creationDate = LocalDate.of(2020, 01, 01);

		final CommentDto commentDto = new CommentDto(commentId, "Never-ending story about happy life...", creationDate);
		when(commentService.save(anyLong(), any(CommentDto.class))).thenReturn(Optional.of(commentDto));

		mvc.perform(put("/api/v1/posts/{postId}/comments/{commentId}", postId, commentId)
				.contentType(MediaType.APPLICATION_JSON).content("""
						   {
						      "text": "Never-ending story about happy life..."
						  }
						""").accept(MediaType.APPLICATION_JSON))
				.andExpectAll(status().isOk(), content().bytes("".getBytes()));

		verify(commentService).save(anyLong(), commentCaptor.capture());
		assertEquals("Never-ending story about happy life...", commentCaptor.getValue().text());

	}

	@Test
	@DisplayName("test for one post & comments full listing that's found successfully")
	void testFullListingOfOnePostCommentsSuccess() throws Exception {
		final Long postId = 1L;
		final Post samplePost = Post.builder().id(postId).title("Title").content("Content").star(true).build();
		final var today = LocalDate.of(2020, 01, 01);
		final Comment[] comments = {
				Comment.builder().id(1L).text("comment #1").creationDate(today).post(samplePost).build(),
				Comment.builder().id(2L).text("comment #2").creationDate(today).post(samplePost).build(),
				Comment.builder().id(3L).text("comment #3").creationDate(today).post(samplePost).build() };
		var postCommentDto = postCommentMapper.toDto(samplePost, comments);

		when(postService.getPostComments(postId)).thenReturn(Optional.of(postCommentDto));

		mvc.perform(get("/api/v1/posts/{postId}/full", postId)).andExpectAll(status().isOk(),
				content().contentType(MediaType.APPLICATION_JSON), content().json("""
						   {
						       "id": 1,
						       "title": "Title",
						       "content": "Content",
						       "star": true,
						       "comments": [
						       		{
								       "id": 1,
								       "text": "comment #1",
								       "creationDate": "2020-01-01"
						       		},
						       		{
								       "id": 2,
								       "text": "comment #2",
								       "creationDate": "2020-01-01"
						       		},
						       		{
								       "id": 3,
								       "text": "comment #3",
								       "creationDate": "2020-01-01"
						       		}
						       ]
						   }
						"""));

	}

	@Test
	@DisplayName("test for one post & comments full listing that's not found")
	void testFullListingOfOnePostCommentsFail() throws Exception {
		final Long postId = 1L;
		when(postService.getPostComments(postId)).thenReturn(Optional.empty());

		mvc.perform(get("/api/v1/posts/{postId}/full", postId)).andExpectAll(status().isOk(),
				content().bytes("".getBytes()));
	}

	@Test
	@DisplayName("test for all tags listing")
	void testListOfAllTags() throws Exception {
		final List<TagDto> sampleData = List.of(new TagDto(1L, "java"), new TagDto(2L, "spring"),
				new TagDto(3L, "interview"));

		when(tagService.findAll()).thenReturn(sampleData);

		mvc.perform(get("/api/v1/tags")).andExpectAll(status().isOk(),
				content().contentType(MediaType.APPLICATION_JSON), content().json("""
						[
						    {
						        "id": 1,
						        "name": "java"
						    },
						    {
						        "id": 2,
						        "name": "spring"
						    },
						    {
						        "id": 3,
						        "name": "interview"
						    }
						]
							"""));
	}

	@Test
	@DisplayName("test for one tag found successfully")
	void testListOfOneTagSuccess() throws Exception {
		final Long id = 1L;
		final TagDto sampleTag = new TagDto(id, "java");

		when(tagService.findById(id)).thenReturn(Optional.of(sampleTag));

		mvc.perform(get("/api/v1/tags/{id}", id)).andExpectAll(status().isOk(),
				content().contentType(MediaType.APPLICATION_JSON), content().json("""
						   {
						       "id": 1,
						       "name": "java"
						   }
						"""));

	}

	@Test
	@DisplayName("test for one tag that's not found")
	void testListOfOneTagFail() throws Exception {
		final Long id = 1L;
		when(tagService.findById(id)).thenReturn(Optional.empty());

		mvc.perform(get("/api/v1/tags/{id}", id)).andExpectAll(status().isOk(), content().bytes("".getBytes()));
	}

	@Test
	@DisplayName("test for post marked with tag")
	void testMarkWithTag() throws Exception {
		final Long postId = 1L;
		final Long tagId = 2L;
		when(postService.markWithTag(postId, tagId)).thenReturn(true);

		mvc.perform(put("/api/v1/posts/{postId}/tag/{tagId}", postId, tagId).contentType(MediaType.APPLICATION_JSON)
				.content("").accept(MediaType.APPLICATION_JSON))
				.andExpectAll(status().isOk(), content().string(Boolean.TRUE.toString()));

		verify(postService).markWithTag(postIdCaptor.capture(), tagIdCaptor.capture());
		
		assertEquals(postId, postIdCaptor.getValue());
		assertEquals(tagId, tagIdCaptor.getValue());
	}

	@Test
	@DisplayName("test for removing tag from post")
	void testRemoveTag() throws Exception {
		final Long postId = 1L;
		final Long tagId = 2L;
		when(postService.removeTag(postId, tagId)).thenReturn(true);

		mvc.perform(delete("/api/v1/posts/{postId}/tag/{tagId}", postId, tagId).contentType(MediaType.APPLICATION_JSON)
				.content("").accept(MediaType.APPLICATION_JSON))
				.andExpectAll(status().isOk(), content().string(Boolean.TRUE.toString()));

		verify(postService).removeTag(postIdCaptor.capture(), tagIdCaptor.capture());
		
		assertEquals(postId, postIdCaptor.getValue());
		assertEquals(tagId, tagIdCaptor.getValue());
	}

}

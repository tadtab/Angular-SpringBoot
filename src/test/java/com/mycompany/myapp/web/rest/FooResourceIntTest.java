package com.mycompany.myapp.web.rest;

import com.mycompany.myapp.Jhipster10062018App;

import com.mycompany.myapp.domain.Foo;
import com.mycompany.myapp.repository.FooRepository;
import com.mycompany.myapp.web.rest.errors.ExceptionTranslator;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.web.PageableHandlerMethodArgumentResolver;
import org.springframework.http.MediaType;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.List;


import static com.mycompany.myapp.web.rest.TestUtil.createFormattingConversionService;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Test class for the FooResource REST controller.
 *
 * @see FooResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = Jhipster10062018App.class)
public class FooResourceIntTest {

    private static final String DEFAULT_BAR = "AAAAAAAAAA";
    private static final String UPDATED_BAR = "BBBBBBBBBB";

    @Autowired
    private FooRepository fooRepository;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    private MockMvc restFooMockMvc;

    private Foo foo;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final FooResource fooResource = new FooResource(fooRepository);
        this.restFooMockMvc = MockMvcBuilders.standaloneSetup(fooResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setControllerAdvice(exceptionTranslator)
            .setConversionService(createFormattingConversionService())
            .setMessageConverters(jacksonMessageConverter).build();
    }

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Foo createEntity() {
        Foo foo = new Foo()
            .bar(DEFAULT_BAR);
        return foo;
    }

    @Before
    public void initTest() {
        fooRepository.deleteAll();
        foo = createEntity();
    }

    @Test
    public void createFoo() throws Exception {
        int databaseSizeBeforeCreate = fooRepository.findAll().size();

        // Create the Foo
        restFooMockMvc.perform(post("/api/foos")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(foo)))
            .andExpect(status().isCreated());

        // Validate the Foo in the database
        List<Foo> fooList = fooRepository.findAll();
        assertThat(fooList).hasSize(databaseSizeBeforeCreate + 1);
        Foo testFoo = fooList.get(fooList.size() - 1);
        assertThat(testFoo.getBar()).isEqualTo(DEFAULT_BAR);
    }

    @Test
    public void createFooWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = fooRepository.findAll().size();

        // Create the Foo with an existing ID
        foo.setId("existing_id");

        // An entity with an existing ID cannot be created, so this API call must fail
        restFooMockMvc.perform(post("/api/foos")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(foo)))
            .andExpect(status().isBadRequest());

        // Validate the Foo in the database
        List<Foo> fooList = fooRepository.findAll();
        assertThat(fooList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    public void getAllFoos() throws Exception {
        // Initialize the database
        fooRepository.save(foo);

        // Get all the fooList
        restFooMockMvc.perform(get("/api/foos?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(foo.getId())))
            .andExpect(jsonPath("$.[*].bar").value(hasItem(DEFAULT_BAR.toString())));
    }
    
    @Test
    public void getFoo() throws Exception {
        // Initialize the database
        fooRepository.save(foo);

        // Get the foo
        restFooMockMvc.perform(get("/api/foos/{id}", foo.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(foo.getId()))
            .andExpect(jsonPath("$.bar").value(DEFAULT_BAR.toString()));
    }

    @Test
    public void getNonExistingFoo() throws Exception {
        // Get the foo
        restFooMockMvc.perform(get("/api/foos/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    public void updateFoo() throws Exception {
        // Initialize the database
        fooRepository.save(foo);

        int databaseSizeBeforeUpdate = fooRepository.findAll().size();

        // Update the foo
        Foo updatedFoo = fooRepository.findById(foo.getId()).get();
        updatedFoo
            .bar(UPDATED_BAR);

        restFooMockMvc.perform(put("/api/foos")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedFoo)))
            .andExpect(status().isOk());

        // Validate the Foo in the database
        List<Foo> fooList = fooRepository.findAll();
        assertThat(fooList).hasSize(databaseSizeBeforeUpdate);
        Foo testFoo = fooList.get(fooList.size() - 1);
        assertThat(testFoo.getBar()).isEqualTo(UPDATED_BAR);
    }

    @Test
    public void updateNonExistingFoo() throws Exception {
        int databaseSizeBeforeUpdate = fooRepository.findAll().size();

        // Create the Foo

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restFooMockMvc.perform(put("/api/foos")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(foo)))
            .andExpect(status().isBadRequest());

        // Validate the Foo in the database
        List<Foo> fooList = fooRepository.findAll();
        assertThat(fooList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    public void deleteFoo() throws Exception {
        // Initialize the database
        fooRepository.save(foo);

        int databaseSizeBeforeDelete = fooRepository.findAll().size();

        // Get the foo
        restFooMockMvc.perform(delete("/api/foos/{id}", foo.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<Foo> fooList = fooRepository.findAll();
        assertThat(fooList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Foo.class);
        Foo foo1 = new Foo();
        foo1.setId("id1");
        Foo foo2 = new Foo();
        foo2.setId(foo1.getId());
        assertThat(foo1).isEqualTo(foo2);
        foo2.setId("id2");
        assertThat(foo1).isNotEqualTo(foo2);
        foo1.setId(null);
        assertThat(foo1).isNotEqualTo(foo2);
    }
}

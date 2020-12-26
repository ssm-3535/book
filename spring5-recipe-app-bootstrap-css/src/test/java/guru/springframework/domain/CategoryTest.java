package guru.springframework.domain;

import org.junit.Before;
import org.junit.Test;

import java.io.IOException;

import static org.junit.Assert.*;

public class CategoryTest {
    Category category;

    @Before
    public void setUp() throws Exception {
        category = new Category();
    }

    @Test
    public void getId() throws IOException{
        Long idValue = 4l;

        category.setId(4l);

        assertEquals(idValue,category.getId());
    }
}
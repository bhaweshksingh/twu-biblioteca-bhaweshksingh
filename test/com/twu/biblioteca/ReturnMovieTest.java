package com.twu.biblioteca;

import org.junit.Before;
import org.junit.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.MockitoAnnotations;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.Is.is;
import static org.mockito.Mockito.*;

public class ReturnMovieTest {
    private Movies movies;
    private ReturnMovie returnMovie;
    ConsoleView consoleViewStub;

    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);
        ArrayList<Movie> availableMovieList = new ArrayList<Movie>();
        availableMovieList.add(new Movie("Movie 1", 2003, "JK Rowling", 1));
        availableMovieList.add(new Movie("Movie 2", 1886, "Arthur Conan Doyle", 2));

        ArrayList<Movie> checkedOutMovieList = new ArrayList<Movie>();
        movies = new Movies(availableMovieList, checkedOutMovieList);
        consoleViewStub = mock(ConsoleView.class);
        returnMovie = new ReturnMovie(consoleViewStub, movies);
    }

    @Test
    public void shouldPromptUserToEnterMovieNameToReturn() throws IOException {
        when(consoleViewStub.getName()).thenReturn("Movie 1");
        returnMovie.performAction();

        verify(consoleViewStub).print(Messages.MOVIE_RETURN_PROMPT);
    }

    @Test
    public void shouldInputMovieNameFromUser() throws IOException {
        when(consoleViewStub.getName()).thenReturn("Movie 1");
        returnMovie.performAction();

        verify(consoleViewStub).getName();
    }

    @Test
    public void shouldBeAbleToReturnMovie() throws IOException {
        Movies Movies = mock(Movies.class);
        returnMovie = new ReturnMovie(consoleViewStub, Movies);
        when(consoleViewStub.getName()).thenReturn("Movie 1");

        returnMovie.performAction();

        verify(Movies).returnMovie("Movie 1");
    }

    @Test
    public void shouldNotBeAbleToReturnInvalidMovie() throws IOException {
        returnMovie = new ReturnMovie(consoleViewStub, movies);
        when(consoleViewStub.getName()).thenReturn("Movie 1");

        returnMovie.performAction();

        ArgumentCaptor<String> stringArgumentCaptor = ArgumentCaptor.forClass(String.class);
        verify(consoleViewStub, times(2)).print(stringArgumentCaptor.capture());

        List<String> capturedStrings = stringArgumentCaptor.getAllValues();

        String actualMessage = capturedStrings.get(1);
        String expectedMessage = Messages.UNSUCCESSFULL_RETURN_MOVIE;

        assertThat(expectedMessage, is(actualMessage));
    }

}
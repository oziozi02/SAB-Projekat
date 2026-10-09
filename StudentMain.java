package rs.ac.bg.etf.sab;

import rs.ac.bg.etf.sab.operations.*;
import rs.ac.bg.etf.sab.tests.TestHandler;
import rs.ac.bg.etf.sab.tests.TestRunner;
import student.*;

public class StudentMain {
    public static void main(String[] args) throws Exception {
        GeneralOperations generalOperations = new jo210476_GeneralOperations();
        GenresOperations genresOperations = new jo210476_GenresOperations();
        MoviesOperations moviesOperations = new jo210476_MoviesOperations();
        RatingsOperations ratingsOperation = new jo210476_RatingsOperations();
        TagsOperations tagsOperations = new jo210476_TagsOperations();
        UsersOperations usersOperations = new jo210476_UsersOperations();
        WatchlistsOperations watchlistsOperations = new jo210476_WatchlistsOperations();

        TestHandler.createInstance(
                genresOperations,
                moviesOperations,
                ratingsOperation,
                tagsOperations,
                usersOperations,
                watchlistsOperations,
                generalOperations);
        TestRunner.runTests();
    }
}
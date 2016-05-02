# Movie-Recommendation-System
Movie Recommendation system allows a user to see a list of recommended movies based on (s)he movie ratings.
Goal:
Given a movie (represented by movie number in a list of movies), find the 20 movies in the list that are most similar to it. Measure similarity by using the Pearson r coefficient among same reviewers.

// Specs:

Read the files movie-matrix.txt and movie-names.txt and store the data.
Prompt the user for a movie number. Repeat the following algorithm until the user replies ‘q’ or ‘quit’.
If the reply is ‘q’ or ‘quit’, end the program.
If the reply is non-numeric or out of range, display a message and ask again.
Print the movie number and name.
Compare the target movie to all of the movies in the matrix, including itself, as follows:
	1.	Find all people who have rated both movies. If there are less than 10, don’t use this movie for comparison purposes (i.e., skip the rest of this loop).
	2.	Computer Pearson r between the target movie and the comparison movie. (Make a function for this.)
If there are less than 20 comparison movies, print “Insufficient comparison movies” and return to the prompt for a new movie.
Sort the comparison movies by decreasing Pearson r value.
Print the top 20 comparison movies with their Pearson r value. Print headers on your list, number the entries, and make sure that the r values are decimal-aligned.

// Data:

The files movie-matrix.txt and movie-names.txt

Each record in movie-matrix.txt represents people’s ratings of one movie, i.e., the first reviewer’s rating, the second reviewer’s rating, etc. Each rating is followed by a ‘;’. When you see two semicolons in a row, that is effectively a null rating followed by a semicolon, i.e., it indicates that the given reviewer didn’t rate the movie. This type of layout is frequently used to save space in a sparse matrix, i.e., a file with a lot of zeroes.

The movie-names.txt file gives the names of the movies, i.e, the first record gives the name of the first movie in movie-matrix.txt, etc.

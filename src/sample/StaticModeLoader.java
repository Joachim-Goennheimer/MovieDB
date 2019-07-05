package sample;

import sample.datamodel.Movie;
import sample.datamodel.MovieData;
import sample.datamodel.RecommendationHandler;
import sample.datamodel.RegisteredUserData;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.*;

/**
 * Class that is responsible for loading the data in static mode. In addition to loading the basic data from the DataLoader class
 * it validates and processes the arguments the user has given the program when starting it in the console.
 * Depending on which parameters were passed it will either give the user an error message if the input was invalid run the
 * program in Test-mode or display the data filtered according to his inputs.
 *
 * This class does its job but can be improved to increase code readability.
 */
public class StaticModeLoader extends DataLoader {

//    these booleans and String Arrays are filled when the processUserArguments() method runs and will then be used in the
//    filter methods to filter the data based on the arguments that were given.
//    If a boolean is true that means that arguments for the respective filter were given and the filter methods
//    need to check for them. If not the filer methods will ignore it and pass on to the next test.
    private static boolean movieFilterActive = false;
    private static String[] movieArgs;
    private static boolean genreFilterActive = false;
    private static String[] genreArgs;
    private static boolean directorFilterActive = false;
    private static String[] directorArgs;
    private static boolean actorFilterActive = false;
    private static String[] actorArgs;
//    Necessary because the keyword limit could be part of a movie title or a director/actor name
    private static int limit = 200;
//    number of Tests that are run in test mode
    private static int numberOfTests = 3;

    private static List<Movie> unfilteredMovies;

    private static List<Movie> rcmdtionsAfterPreFilter;
    private static List<Movie> filteredMovies;




    public static void loadDataStaticMode(String[] userArguments){

        RecommendationHandler.setSimilarTasteValueThreshold(1);
//        more than in interactive mode because there are no additional restrictions on gerne, actors, directors.
//        The +1 is because oneself is always part of the similar users.
        RecommendationHandler.setSimilarUsersLimit(25+1);

        if (checkIfTestmode(userArguments)){
            try {
                loadTestMode();
            }
            catch (IOException e){
                System.out.println(e.getMessage());
            }
        }
        else {
            loadNormalMode(userArguments);
        }

    }

    /**
     * Organises and calls other methods in the class in the correct order.
     * Calls different filters depending on whether the user entered a --film argument or not.
     * Also displays the data in the correct way and lets the user decide whether he wants additional recommendations
     * if he entered a --film parameter.
     * @param userArguments The arguments that were entered by the user into the console when calling the program.
     */
    private static void loadNormalMode(String[] userArguments){

        processUserArguments(userArguments);

//        If no valid input parameter is given it does not make sense to start filtering.
        if (checkForInvalidInput()){
//            If the user has entered at least one argument for --film the preFilter has to be run in order to receive
//            recommendations.
            if (movieFilterActive){
                loadUserData();
                loadBaseData();
                MovieData.getInstance().loadMovies();
                preFilter();
                filteredMovies = postFilter();
            }
//            If the user has not entered an argument for --film only the postFilter will be run that strictly filters
//            only the movies that meet the requirements. Could also extend the preFilter for genres to for example rate
//            all movies with a particular genre or actor with 5.0 and then get the recommendations based on that but
//            this might not make much sense as there are too many movies per genre in order to really make a sensible
//            decision based on such a system.
//            In this version it is not guaranteed that the limit is met but one could just fill the rest with other random
//            movies with a high Imdb rating.
            else {
                loadBaseData();
                MovieData.getInstance().loadMovies();
                filteredMovies = postFilter();
            }


//            Here the output logic begins.

//            This for loop will always be run provided there was any valid user input. It applies the postFilter and
//            if --film parameters were given both the pre and the postFilter.
            for (Movie movie: filteredMovies){
                System.out.println("********************************************");
                System.out.println("Movie Title: " + movie.getTitle());
                System.out.println("Genres: " + movie.getGenreNames());
                System.out.println("Directors: " + movie.getDirectorNames());
                System.out.println("Actors: " + movie.getActorNames());
                System.out.println("********************************************");
            }

//            This part of the code will only be run if the user entered any --film parameters. In hat case it will provide
//            the user with the decision to get more recommendations even though they do not fulfill all of his other parameters.
            if (movieFilterActive){
                try (Scanner scanner = new Scanner(System.in)) {

                    System.out.print("\nWould you like to receive more recommendations only considering the --film parameter?\n");
                    System.out.print("The recommendations are all movies that are recommended but do not match the ");
                    System.out.print("other parameters you entered.\n\n ");
                    System.out.print("Y/N\n");

                    String input = scanner.nextLine();  // Read user input

                    input.toLowerCase();
                    if (input.contains("yes") || input.equals("y")){
                        System.out.println("\n------------------------------------------------\n");
                        System.out.println("Additional recommendations only based on --film parameter\n");
                        System.out.println("------------------------------------------------\n\n");

                        for (Movie movie: rcmdtionsAfterPreFilter){
                            if (!filteredMovies.contains(movie)){
                                System.out.println("*******************************************");
                                System.out.println("Movie Title: " + movie.getTitle());
                                System.out.println("Genres: " + movie.getGenreNames());
                                System.out.println("Directors: " + movie.getDirectorNames());
                                System.out.println("Actors: " + movie.getActorNames());
                                System.out.println("*******************************************");
                            }

                        }
                    }

                } catch (Exception e){
                    System.out.println("No decision was made. Exciting program.");
                }
            }



        }
        else {
            System.out.println("Unfortunately no valid arguments were entered.");
            System.out.println("A valid argument has the form --film='myfilm'.");
            System.out.println("Please try again.");
        }



    }

    /**
     * Organises and calls other methods in the class in the correct order.
     * Responsible for loading the data and calling the setTestParameters() method consecutively to conduct all tests.
     * Also writes the results of the test runs into the result.txt file.
     * Code in method is not optimal and can be improved for better readability.
     * @throws IOException
     */
    private static void loadTestMode() throws IOException{

        loadUserData();
        loadBaseData();
        MovieData.getInstance().loadMovies();

//        this boolean will be switched on after the first test run so that the contents of the file won't be overriden
//        by each consecutive test.
        boolean appending = false;

//        Test 1 and 2
        for (int i = 1; i < numberOfTests; i++){
            if (i == 2){
                appending = true;
            }

            setTestParameters(i);

            preFilter();
            filteredMovies = postFilter();
            Set<Integer> verifyDuplicateSet = new HashSet<>();

            int limitcounter = 0;
            try(BufferedWriter logFile = new BufferedWriter(new FileWriter("result.txt", appending))){

                logFile.write("\n******************************************************************\n");
                logFile.write("Movie recommendations based on the movie: " + movieArgs[0] + " that passed the genre parameter\n");
                logFile.write("******************************************************************\n\n");

                for (Movie movie: filteredMovies){
                    if (limitcounter < limit){
                        verifyDuplicateSet.add(movie.getMovieID());
                        logFile.write(movie.getTitle() + "  ||  Genres: " + movie.getGenreNames() + "  ||  Directors: " + movie.getDirectorNames() + "  ||  Actors: " + movie.getActorNames() + "\n");
                        limitcounter++;
                    }

                }

                logFile.write("\n******************************************************************\n");
                logFile.write("Movie recommendations based on the movie: " + movieArgs[0] + " that did not pass the genre parameter\n");
                logFile.write("******************************************************************\n\n");


                for (Movie movie: rcmdtionsAfterPreFilter){
                    if (limitcounter < limit && !verifyDuplicateSet.contains(movie.getMovieID())){
                        logFile.write(movie.getTitle() + "  ||  Genres: " + movie.getGenreNames() + "  ||  Directors: " + movie.getDirectorNames() + "  ||  Actors: " + movie.getActorNames() + "\n");
                        limitcounter++;
                    }
                }

            }

        }

//        Test 3 begins here. Could also manage the logic in loops to make code more understandable but would have to include
//        some conditions for what should be printed and added to verifyDuplicateSet.

        setTestParameters(3);

        filteredMovies = postFilter();
        Set<Integer> verifyDuplicateSet = new HashSet<>();

        int limitcounter = 0;
        try(BufferedWriter logFile = new BufferedWriter(new FileWriter("result.txt", true))){

            logFile.write("\n******************************************************************\n");
            logFile.write("Movie recommendations based on actor parameters Jason Statham and Keanu Reeves that passed the genre parameter\n");
            logFile.write("******************************************************************\n\n");

            for (Movie movie: filteredMovies){
                if (limitcounter < limit){
                    verifyDuplicateSet.add(movie.getMovieID());
                    logFile.write(movie.getTitle() + "  ||  Genres: " + movie.getGenreNames() + "  ||  Directors: " + movie.getDirectorNames() + "  ||  Actors: " + movie.getActorNames() + "\n");
                    limitcounter++;
                }

            }


        }

        setTestParameters(3+1);

        filteredMovies = postFilter();

        try(BufferedWriter logFile = new BufferedWriter(new FileWriter("result.txt", true))){

            for (Movie movie: filteredMovies){
                if (limitcounter < limit && !verifyDuplicateSet.contains(movie.getMovieID())){
                    logFile.write(movie.getTitle() + "  ||  Genres: " + movie.getGenreNames() + "  ||  Directors: " + movie.getDirectorNames() + "  ||  Actors: " + movie.getActorNames() + "\n");
                    limitcounter++;
                }

            }


        }

        setTestParameters(3+2);

        filteredMovies = postFilter();

        try(BufferedWriter logFile = new BufferedWriter(new FileWriter("result.txt", true))){

            logFile.write("\n******************************************************************\n");
            logFile.write("Movie recommendations based on actor parameters Jason Statham and Keanu Reeves that did not pass the genre parameter\n");
            logFile.write("******************************************************************\n\n");

            for (Movie movie: filteredMovies){
                if (limitcounter < limit && !verifyDuplicateSet.contains(movie.getMovieID())){
                    logFile.write(movie.getTitle() + "  ||  Genres: " + movie.getGenreNames() + "  ||  Directors: " + movie.getDirectorNames() + "  ||  Actors: " + movie.getActorNames() + "\n");
                    limitcounter++;
                }

            }


        }

        setTestParameters(3+3);

        filteredMovies = postFilter();

        limitcounter = 0;
        try(BufferedWriter logFile = new BufferedWriter(new FileWriter("result.txt", true))){

            for (Movie movie: filteredMovies){
                if (limitcounter < limit){
                    logFile.write(movie.getTitle() + "  ||  Genres: " + movie.getGenreNames() + "  ||  Directors: " + movie.getDirectorNames() + "  ||  Actors: " + movie.getActorNames() + "\n");
                    limitcounter++;
                }

            }


        }

//        End of Test 3


    }

    /**
     * Checks whether the user called the program in Test-mode. If this is the case all other arguments will be ignored
     * and the fixed test-parameters from the setTestParameters() method will be used.
     * @param userArguments The arguments that were entered by the user into the console when calling the program.
     * @return Returns true if program is called in Test-mode - Returns false if program is not called in Test-mode.
     */
    private static boolean checkIfTestmode(String[] userArguments){

        boolean inTestmode = false;

        for (String argument: userArguments) {
            argument = argument.toLowerCase();

            if(argument.contains("test=true") || argument.contains("testmodus=true")){
                inTestmode = true;

                }

        }
        return inTestmode;
    }

    /**
     * Sets the test parameters for the Test-mode. These parameters can be changed if the tests should be conducted for
     * other parameters.
     * @param numberOfCurrentTest The number of the test-run that is currently conducted. Depending on the run the parameters
     *                            are set to the appropriate values.
     */
    private static void setTestParameters(int numberOfCurrentTest){

        if (numberOfCurrentTest == 1){
            movieFilterActive = true;
            movieArgs = new String[1];
            movieArgs[0] = "matrix revolutions";

            genreFilterActive = true;
            genreArgs = new String[1];
            genreArgs[0] = "thriller";

            limit = 10;
        }
        else if(numberOfCurrentTest == 2){

            movieFilterActive = true;

            movieArgs[0] = "indiana jones and the temple of doom";

            genreFilterActive = true;

            genreArgs[0] = "adventure";

            limit = 15;

        }
        else if (numberOfCurrentTest == 3){
            movieFilterActive = false;
            actorFilterActive = true;

            actorArgs = new String[1];
            actorArgs[0] = "jason statham";
//            actorArgs[1] = "keanu reeves";

            genreArgs[0] = "action";
            limit = 50;

        }
        else if (numberOfCurrentTest == 4){
            actorArgs[0] = "keanu reeves";

        }
        else if (numberOfCurrentTest == 5){

            genreFilterActive = false;
            actorArgs[0] = "jason statham";

        }
        else {
            actorArgs[0] = "keanu reeves";
        }

    }

    /**
     * Processes the arguments entered by the user into the console and sets filterBooleans and String Arrays to the
     * correct values.
     * @param userArguments The arguments that were entered by the user into the console when calling the program.
     */
    private static void processUserArguments(String[] userArguments){

        for (String argument: userArguments){
            argument = argument.toLowerCase();

            if (argument.contains("film=")|| argument.contains("movie=")){
                movieFilterActive = true;

                int cutIndex = argument.indexOf("=");
                argument = argument.substring(cutIndex + 1);
                movieArgs = argument.split(",");

//                The following block only takes care of removing all "The ", ", the" from the arguments as they cause
//                problems when searching for movies later on.
                ArrayList<String> movieArgsList = new ArrayList<>(Arrays.asList(movieArgs));
                ArrayList<String> movieArgsListCopy = new ArrayList<>();

                boolean checkForSubstring = false;
                for (String movieArg : movieArgsList){
                    movieArg = movieArg.trim();
                    checkForSubstring = false;

                    if (movieArg.length() >= 5){
                        checkForSubstring = true;
                    }
                    if (checkForSubstring && movieArg.substring(0, 4).equals("the ")){
                        movieArgsListCopy.add(movieArg.substring(4));
                    }
                    else if (!movieArg.equals("the")){
                        movieArgsListCopy.add(movieArg);
                    }
                }

                movieArgs = movieArgsListCopy.toArray(new String[movieArgsListCopy.size()]);
//                End of block that only takes care of removing all "The ", ", the" from the arguments.

                for (int i = 0; i < movieArgs.length; i++){
                    System.out.println("Movie argument passed: " + movieArgs[i]);
                }


            }
            else if(argument.contains("genre=")){
                genreFilterActive = true;

                int cutIndex = argument.indexOf("=");
                argument = argument.substring(cutIndex + 1);
                genreArgs = argument.split(",");

                for (int i = 0; i < genreArgs.length; i++){
                    genreArgs[i] = genreArgs[i].trim();
                    System.out.println("Passed genre argument for genre: " + genreArgs[i]);

                }

            }
            else if(argument.contains("director=")|| argument.contains("direktor=")){
                directorFilterActive = true;

                int cutIndex = argument.indexOf("=");
                argument = argument.substring(cutIndex + 1);
                directorArgs = argument.split(",");

                for (int i = 0; i < directorArgs.length; i++){
                    directorArgs[i] = directorArgs[i].trim();
                    System.out.println("Passed actor argument for director: " + directorArgs[i]);

                }
            }
            else if(argument.contains("actor=")|| argument.contains("actress=") || argument.contains("schauspieler=") || argument.contains("schauspielerin=") ){
                actorFilterActive = true;

                int cutIndex = argument.indexOf("=");
                argument = argument.substring(cutIndex + 1);
                actorArgs = argument.split(",");

                for (int i = 0; i < actorArgs.length; i++){
                    actorArgs[i] = actorArgs[i].trim();
                    System.out.println("Passed actor argument for actor: " + actorArgs[i]);

                }
            }
            else if (argument.contains("limit=")){

                int cutIndex = argument.indexOf("=");
                argument = argument.substring(cutIndex + 1);

                try {
                    limit = Integer.parseInt(argument);

                } catch (NumberFormatException e){
                    e.printStackTrace();
                    System.out.println("The limit has to be a numerical value");
                }

                System.out.println("The limit you entered is: " + limit);
            }

        }



    }

    /**
     * Checks whether all of the user arguments were invalid. In that case it doesn't make sense to even load the data.
     * Instead it will return false to the loadStaticMode() method which will then prompt an  error message to the user.
     * @return True if at least one valid input parameter was entered by the user - False if no valid input parameter was
     *         entered by the user.
     */
    private static boolean checkForInvalidInput(){
//
        boolean checkPassed = true;
        if (!movieFilterActive && !genreFilterActive && !directorFilterActive && !actorFilterActive && limit==200){
            checkPassed = false;
        }
        return checkPassed;

    }

    /**
     * Gets recommendations for the --film parameters that were entered. The recommendations are based on the same
     * recommendation algorithm used in the interactive mode with a few tweaks.
     * The preFilter() does not take into consideration the other parameters that were entered. They will later be
     * processed by the postFilter().
     * The recommendations based on the preFilter() will be assigned to the rcmdtionsAfterPreFilter attribute.
     */
    private static void preFilter(){
        RegisteredUserData registeredUserData = RegisteredUserData.getInstance();
        registeredUserData.removeUser("staticUser");
        registeredUserData.registerNewUser("staticUser", "OnlyAPlaceholder");
        registeredUserData.loginUser("staticUser", "OnlyAPlaceholder");

        MovieData movieData = MovieData.getInstance();
        List<Movie> preferredMovies = movieData.getMoviesByTitle(movieArgs);

        for (Movie movie: preferredMovies){
            registeredUserData.addRating(movie.getMovieID(), 5.0);
        }

        RecommendationHandler.loadRecommendations();
        rcmdtionsAfterPreFilter = RecommendationHandler.getRecommendations();

    }


    /**
     * Filters a List of movies so that the resulting list will fulfill all the criteria the user has entered as input parameters
     * (except the --film parameter which is already processed in the preFilter()).
     * @return A List of Movie objects that all fulfill every criteria (except the --film parameter) the user has entered as an input parameter.
     */
    private static List<Movie> postFilter(){

        if (movieFilterActive){
            unfilteredMovies = rcmdtionsAfterPreFilter;
        }
        else {
            unfilteredMovies = MovieData.getInstance().getMovies();
        }

        List<Movie> filteredMovies = new ArrayList<>();


        int limitCounter = 0;

        for (Movie movie: unfilteredMovies){

            if (limitCounter >= limit){
                break;
            }

            boolean passesFilterGenre = true;
            boolean passesFilterDirector = true;
            boolean passesFilterActor = true;

            if (genreFilterActive){

                int filterCounterGenre = 0;

                String genresToCheck = movie.getGenreNames().toLowerCase();
                for (String genre: genreArgs){
                    if (genresToCheck.contains(genre)){
                        filterCounterGenre++;
                    }

                }

                if (filterCounterGenre < genreArgs.length){
                    passesFilterGenre = false;
                }

            }
            if (directorFilterActive){

                int filterCounterDirector = 0;

                String directorsToCheck = movie.getDirectorNames().toLowerCase();
                for (String director: directorArgs) {
                    if (directorsToCheck.contains(director)) {
                        filterCounterDirector++;
                    }
                }

                if (filterCounterDirector < directorArgs.length){
                    passesFilterDirector = false;
                }
            }
            if (actorFilterActive){

                int filterCounterActor = 0;

                String actorsToCheck = movie.getActorNames().toLowerCase();
                for (String actor: actorArgs){
                    if (actorsToCheck.contains(actor)){
                        filterCounterActor++;
                    }
                }

                if (filterCounterActor < actorArgs.length){
                    passesFilterDirector = false;
                }

            }



            if (passesFilterGenre && passesFilterDirector && passesFilterActor){
                filteredMovies.add(movie);
                limitCounter++;

            }

        }

//        }
        return filteredMovies;


    }


    public static boolean testProcessUserArguments(String[] argumentstring){
        processUserArguments(argumentstring);
        if (movieFilterActive && genreFilterActive && !actorFilterActive && !directorFilterActive){
            if (movieArgs[0].equals("lord of the rings") && genreArgs[0].equals("adventure")){
                return true;
            }
        }
        return false;
    }

    public static boolean testPreFilter(String[] argumentstring) {

        RecommendationHandler.setSimilarTasteValueThreshold(1);
//        more than in interactive mode because there are no additional restrictions on gerne, actors, directors.
//        The +1 is because oneself is always part of the similar users.
        RecommendationHandler.setSimilarUsersLimit(25+1);

        processUserArguments(argumentstring);
        loadUserData();
        loadBaseData();
        MovieData.getInstance().loadMovies();
        preFilter();

        if(!rcmdtionsAfterPreFilter.isEmpty()){
            return true;
        }
        else {
            return false;
        }
    }

    public static boolean testPostFilter(String[] argumentString) {

        RecommendationHandler.setSimilarTasteValueThreshold(1);
//        more than in interactive mode because there are no additional restrictions on gerne, actors, directors.
//        The +1 is because oneself is always part of the similar users.
        RecommendationHandler.setSimilarUsersLimit(25+1);

        processUserArguments(argumentString);
        loadUserData();
        loadBaseData();
        MovieData.getInstance().loadMovies();
        preFilter();
        filteredMovies = postFilter();

        if(!filteredMovies.isEmpty()){
            return true;
        }
        else {
            return false;
        }
    }

    public static boolean testForInvalidInput(String[] argumentString){

        movieFilterActive = false;
        genreFilterActive = false;
        processUserArguments(argumentString);
        if (!checkForInvalidInput()){
            return true;
        }
        else {
            return false;
        }

    }
}

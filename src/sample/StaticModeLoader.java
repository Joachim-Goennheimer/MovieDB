package sample;

import sample.datamodel.Movie;
import sample.datamodel.MovieData;

import java.util.ArrayList;
import java.util.List;

public class StaticModeLoader extends DataLoader {

    private static boolean loadMovie = false;
    private static String[] movieArgs;
    private static boolean loadGenre = false;
    private static String[] genreArgs;
    private static boolean loadDirector = false;
    private static String[] directorArgs;
    private static boolean loadActor = false;
    private static String[] actorArgs;
    //        Necessary because the keyword limit could be part of a movie title or a director/actor name
    private static boolean limitWithinOtherKey = false;
    private static int limit = 200;




    private static void processUserArguments(String[] userArguments){
//        method that processes the arguments a user has passed in static mode and sets the instance variables to the appropriate values.


        for (String argument: userArguments){
             argument = argument.toLowerCase();

            if (argument.contains("film=")|| argument.contains("movie=")){
                loadMovie = true;

                int cutIndex = argument.indexOf("=");
                argument = argument.substring(cutIndex + 1);
                movieArgs = argument.split(",");

                for (int i = 0; i < movieArgs.length; i++){
                    movieArgs[i] = movieArgs[i].trim();
                    System.out.println("Passed genre argument for movie: " + movieArgs[i]);

                }

            }
            else if(argument.contains("genre=")){
                loadGenre = true;

                int cutIndex = argument.indexOf("=");
                argument = argument.substring(cutIndex + 1);
                genreArgs = argument.split(",");

                for (int i = 0; i < genreArgs.length; i++){
                    genreArgs[i] = genreArgs[i].trim();
                    System.out.println("Passed genre argument for genre: " + genreArgs[i]);

                }

            }
            else if(argument.contains("director=")|| argument.contains("direktor=")){
                loadDirector = true;

                int cutIndex = argument.indexOf("=");
                argument = argument.substring(cutIndex + 1);
                directorArgs = argument.split(",");

                for (int i = 0; i < directorArgs.length; i++){
                    directorArgs[i] = directorArgs[i].trim();
                    System.out.println("Passed actor argument for director: " + directorArgs[i]);

                }
            }
            else if(argument.contains("actor=")|| argument.contains("actress=") || argument.contains("schauspieler=") || argument.contains("schauspielerin=") ){
                loadActor = true;

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

    public static void loadDataStaticMode(String[] userArguments){

        processUserArguments(userArguments);

        if (checkForInvalidInput()){
            loadBaseData();

            List<Movie> filteredMovies = filterMovies();
            for (Movie movie: filteredMovies){
                System.out.println("********************************************");
                System.out.println("Movie Title: " + movie.getTitle());
                System.out.println("Genres: " + movie.getGenreNames());
                System.out.println("Directors: " + movie.getDirectorNames());
                System.out.println("********************************************");

            }


        }
        else {
            System.out.println("Unfortunately no valid arguments were entered.");
            System.out.println("A valid argument has the form --film='myfilm'.");
            System.out.println("Please try again.");
        }




    }

    private static boolean checkForInvalidInput(){
//        method is called to check whether all of the user arguments were invalid. In that case it doesn't make sense to even load the data.
//        Instead an error message will be prompted for the user.
        boolean checkPassed = true;
        if (!loadMovie && !loadGenre && !loadDirector && !loadActor && limit==200){
            checkPassed = false;
        }
        return checkPassed;

    }

    private static List<Movie> filterMovies(){

        List<Movie> unfilteredMovies = MovieData.getMovies();
        List<Movie> filteredMovies = new ArrayList<>();


        int limitCounter = 0;

            for (Movie movie: unfilteredMovies){

                if (limitCounter >= limit){
                    break;
                }

                boolean passesFilterGenre = true;
                boolean passesFilterDirector = true;
                boolean passesFilterActor = true;

                if (loadGenre){

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
                if (loadDirector){

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
                if (loadActor){

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


    public static List displayDataStaticMode(List displayData){

        return null;
    }
}

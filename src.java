/* *******************************************************************
Author:    Khaja Anwar Ali Siddiqui

Purpose:   Movie Recommendation System using pearson similarity co-efficient.
*********************************************************************/
//	Including the necessary packages	//
import java.io.IOException;
import java.lang.IllegalStateException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.NoSuchElementException;
import java.util.Scanner;
import java.util.StringTokenizer;
import java.util.ArrayList;
import java.util.Collections;
/////////////////////////////////////////////////////////////////////////////////////////////
public class src{
/////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	public static void main(String[] args){
                src mov=new src();
		ArrayList<String> movies= new ArrayList<String>();	//To store movie names
		ArrayList<ArrayList<Integer>> reviewers=new ArrayList<ArrayList<Integer>>();	//2D array to store reviewer with their ratings.
		ArrayList<Integer> rating=new ArrayList<Integer>();				//To store ratings of each reviewer.
		mov.getMovieNames(movies);				//Function call to read/store moveis.
                mov.getMovieRatings(reviewers,rating);	//Function call to read/store ratings.
                int choice=0;
                do{
                choice=mov.getUsrChoice(movies);		//Function call to get the user choice.
                mov.movieRecommendations(choice, reviewers, movies);	//Function call to display recommended movies, after calculating r values.
                }while((choice>=0) || (choice<movies.size()));
                System.exit(0);
       }
///////////////////////////////////////////////////////////////////////////////////////////
//			Function: int getUsrChoice()
//	The following function accepts movies array and retunrs if the user choice is valid.
/////////////////////////////////////////////////////////////////////////////////////////////
	int getUsrChoice(ArrayList<String> movies){
                   int choice=0;
                   Scanner usrChoice= new Scanner(System.in);
                        choice=0;
                        do{
                                System.out.printf("\n%s\n","Movie number:");
                                String usrMov=usrChoice.nextLine();
                                if((usrMov.equals("q"))||(usrMov.equals("quit"))) System.exit(0);	//If the input is 'q' or 'quit' exit the program.
                        else try{
                                choice=Integer.parseInt(usrMov);	//To check if the user input is an integer
                        }
                        catch(NumberFormatException e ){
                       	 	System.out.println("Invalied entry, try again.\n");
			        continue;							//If the input is not a valid number of 'q' or 'quit', the continue.
                        }
                        if((choice<=0) || (choice>movies.size()))	//Setting the range.
                                System.out.printf("%s %d %s\n","Out of range: Number should be less than",movies.size(),".");
                        }while(choice<=0 || choice>movies.size());
                System.out.printf("%s %s %s %d\n","Movie Choosen: ",movies.get(choice-1),"Movie Number: ",choice);
                return choice;
        }
////////////////////////////////////////////////////////////////////////////////////////////////////
//			Function: void getMovieRatings()
//	The following function reads the ratings matrix and stores the reviewer ratigs into 2D array.
//	called reweivers, with each reviewer's rating into rating array.
////////////////////////////////////////////////////////////////////////////////////////////////////
        void getMovieRatings(ArrayList<ArrayList<Integer>> reviewers, ArrayList<Integer> rating){
		Scanner readRatings=null;
                try{
                        readRatings=new Scanner(Paths.get("/home/turing/t90rkf1/d470/dhw/hw2-movies/movie-matrix.txt"));
                }
                catch(IOException ioException){
                        System.err.println("Error opening file");	//Trying to open the file.
                        System.exit(1);
                }
                try
                {
                while (readRatings.hasNext()) // while there is more to read
                {
                        String Str = readRatings.nextLine();
                        rating= new ArrayList<Integer>();
                        for (String retval: Str.split(";")){	//Split the string based on ';' as delimiter.
                        int i;
                        if(retval.isEmpty()) i=0;		//If the string is empty(no rating) set the rating as 0(assuming rating range to be 1-5).
                        else i=Integer.parseInt(retval);
                        rating.add(i);				//Add the rating to the particular reviewer.
                                }
                        reviewers.add(rating);			//Add the reviewer to the array of all reviewers.
                        }
                }
                catch (NoSuchElementException elementException){
                        System.err.println("File improperly formed. Terminating.");
                }
                catch (IllegalStateException stateException){
                        System.err.println("Error reading from file. Terminating.");
                }
        }
//////////////////////////////////////////////////////////////////////////////////////////////////////////
//			Function: void getMovieNames()
//	The following function accepts the movies array, reads movie names and store them into movie array.
//////////////////////////////////////////////////////////////////////////////////////////////////////////
        void getMovieNames(ArrayList<String> movies){
                Scanner readMovies=null;
		try{
                        readMovies=new Scanner(Paths.get("/home/turing/t90rkf1/d470/dhw/hw2-movies/movie-names.txt"));	//Trying to open the file.
                }
                catch(IOException ioException){
                        System.err.println("Error opening file");
                        System.exit(1);
                }
                try{
                        int alt=2;					//This is to ignore the first string.
                        while(readMovies.hasNext()){
                                String strm= readMovies.nextLine();	//Reading the next string.
                                for(String retvalm: strm.split("\\|")){	//Splitting the string based on the delimiter '|'
                                        if(!((alt%2)==0))
                                        movies.add(retvalm);		//Adding the movie name to the movies array.
                                        alt++;
                                }
                        }
                }
                catch(NoSuchElementException elementException){
                        System.out.println("File format is wrong!");
                }
                System.out.printf("%s %d\n","Total Movies:",movies.size());
        }
///////////////////////////////////////////////////////////////////////////////////////////////////////////////////
//			Function: void movieRecommendations()
//	The following function gives a list of recommended movies upto 20.It accepts Reviweres, movies, and choice.
//	and it calls various functions to calculate R and display movies.
////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	void movieRecommendations(int choice, ArrayList<ArrayList<Integer>> reviewers, ArrayList<String> movies){
		ArrayList<Integer> compare=new ArrayList<Integer>(reviewers.get(choice-1));	//To is to store the movie under comparision.
                ArrayList<Double> rValues=new ArrayList<Double>();				//To store the r values.
                        for(int i=0;i<movies.size();i++){
                                if(compare.size()<10){						//If the movie has less than 10 reviewers then break the loop.
                                        System.out.println("Insufficient comparison movies.");
                                        break;
                                }
                                ArrayList<Integer> temp1=new ArrayList<Integer>();		//To store the array of non-zero reviewers.
                                ArrayList<Integer> temp2=new ArrayList<Integer>();
                                ArrayList<Integer> target=new ArrayList<Integer>(reviewers.get(i));
                                        for(int j=0;((j<compare.size()) & (j<target.size()));j++){
                                                int revRtng1,revRtng2;
                                                revRtng1=compare.get(j);
                                                revRtng2=target.get(j);
                                                if((revRtng1!=0) & (revRtng2!=0)){		//Ensures that a particular reviwer has reviwed/seen both movies
                                                        temp1.add(revRtng1);			//Add the review to the temp array.
                                                        temp2.add(revRtng2);
                                                }
                                        }
                                if((temp1.size())<10){						//If the reviewers are less than 10, set the R value as -1 and continue.
                                        rValues.add(-2.0);	//Since r values range from -1 to +1, so taking -2 to differentiate.
                                        continue;
                                }
				double avgRt1=avgRating(temp1);		//Function call to calculate averages.
				double avgRt2=avgRating(temp2);
         			double sd1=stdDeviation(temp1,avgRt1);	//Function call to calculate standard deviation.
				double sd2=stdDeviation(temp2,avgRt2);
				rArray(rValues, temp1, temp2, avgRt1, avgRt2, sd1, sd2);	//Function call to calculate r value for the two movies.
                        }	
			displayRecs(rValues, movies);	//Finally display the movies based on R values.
	}
///////////////////////////////////////////////////////////////////////////////////////////////////
//			Function: double avgRatins()
//	The following function is to caculate the average ratings, it accepts an array, 
//	and calculates the average of its elements.
///////////////////////////////////////////////////////////////////////////////////////////////////
	double avgRating(ArrayList<Integer> temp){
                int sumRt=0;
                for(int n: temp)       sumRt+=n;	//Adding the elements.
                return ((double)sumRt)/(temp.size());	//Returning the average.
        }
////////////////////////////////////////////////////////////////////////////////////////////////////
//			Function: double stdDeviation()
//	The following function calculates standard deviation, it accepts an array of Integers 
//	and average of its elements to calculate standard deviation.
/////////////////////////////////////////////////////////////////////////////////////////////////////
        double stdDeviation(ArrayList<Integer> temp, double avgRt){
                double sumDiffRt=0;
                for(int n: temp){
                        sumDiffRt+=((n-avgRt)*(n-avgRt));	//Finding the summation of distances from average.
                }
                return Math.sqrt(sumDiffRt/(temp.size()-1));	//Returning the standard deviation.
        }
/////////////////////////////////////////////////////////////////////////////////////////////////
//			Function: void createRArray()
//	The following function accepts and empty rValeus array, comparision array and target array 
//	with their average ratings and standard deviations, to calculate r value and store them into rvalues array.
//////////////////////////////////////////////////////////////////////////////////////////////////////
	void rArray(ArrayList<Double> rValues, ArrayList<Integer> temp1, ArrayList<Integer> temp2, double avgRt1, double avgRt2, double sd1, double sd2){
                double r=0, summRt=0;
                if((sd1<=0) || (sd2<=0) || ((temp1.size()-1)<=0)) r=-2.0;		//To avoid division by zero.
                else for(int h=0;((h<temp1.size()) && (h<temp2.size()));h++){
                        summRt+=((temp1.get(h)-avgRt1)*(temp2.get(h)-avgRt2));	//Finding the product of summation of distances of two movies.
                        r=(summRt/((temp1.size()-1)*sd1*sd2));			//Calculating the r values.
                     }
                 rValues.add(r);	//Adding the R value(compare vs target) to the r values array.
        }
/////////////////////////////////////////////////////////////////////////////////////////////////////
//			Function: void displayRecs()
//	The following function accepts rValues arrays, and movies arrays. It sorts rValues based on the 
//	pearson coeffecient and displays all the recommended movies.
///////////////////////////////////////////////////////////////////////////////////////////////////////
       void displayRecs(ArrayList<Double> rValues, ArrayList<String> movies){
		double r1, r2;			//Variables to read, display and sort the r values.
		int top=0, count=0;			//To count untill 20 movies.
		String movie;
		ArrayList<Double> rSorted=new ArrayList<Double>(rValues);	//To store the sorted array of R values.
		Collections.sort(rSorted);			//Sorting the R values.
		Collections.reverse(rSorted);			//To ensure the descending order.
		for(int x=0;((top<20)&(x<rSorted.size()));x++){	//Looping to display 20 movies from the sorted r values array.
			r1=rSorted.get(x);
			for(int s=0;s<rSorted.size();s++)
				if(rSorted.get(s)!=(-2))	count++; 
			if(count<20){
				System.out.println("Insufficient comparison movies.");
				break;
			}
			for(int y=0;((y<rValues.size())&(top<20));y++){
				r2=rValues.get(y);
				if(r1==r2){	//Finding a match for r value to retreive the index.
					top++;			//Once a movie is found, increasing the recommendation number.
					movie=movies.get(y);	//Retreiving the movie.
					System.out.printf("%s %f %s %d %s\n","R value:",r1,"Movie Rec: #",top,movie);	//Displaying the movies with R values.
				}
			}
		}
	}
/////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
}

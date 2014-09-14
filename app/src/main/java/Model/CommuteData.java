package Model;

/**
 * Created by erikkamp on 9/14/14.
 */
public class CommuteData {

    //Main road taken to get to work
    private String summary;
    //Time taken returned by the Google api, already formatted by the API itself
    private String timeTaken;
    //Distance it will take to get to work
    private String distance;


    public CommuteData(String summary, String timeTaken, String distance) {
        this.summary = summary;
        this.timeTaken = timeTaken;
        this.distance = distance;
    }

    public String getSummary() {
        return summary;
    }

    public String getTimeTaken() {
        return timeTaken;
    }

    public String getDistance() {
        return distance;
    }

    @Override
    public String toString() {
        return "CommuteData{" +
                "summary='" + summary + '\'' +
                ", timeTaken='" + timeTaken + '\'' +
                ", distance='" + distance + '\'' +
                '}';
    }
}

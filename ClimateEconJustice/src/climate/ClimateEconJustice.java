package climate;

import java.util.ArrayList;

import javax.print.DocFlavor.STRING;

/**
 * This class contains methods which perform various operations on a layered
 * linked list structure that contains USA communitie's Climate and Economic
 * information.
 * 
 * @author Navya Sharma
 */

public class ClimateEconJustice {

    private StateNode firstState;

    /*
     * Constructor
     * 
     * **** DO NOT EDIT *****
     */
    public ClimateEconJustice() {
        firstState = null;
    }

    /*
     * Get method to retrieve instance variable firstState
     * 
     * @return firstState
     * 
     * **** DO NOT EDIT *****
     */
    public StateNode getFirstState() {
        // DO NOT EDIT THIS CODE
        return firstState;
    }

    /**
     * Creates 3-layered linked structure consisting of state, county,
     * and community objects by reading in CSV file provided.
     * 
     * @param inputFile, the file read from the Driver to be used for
     * @return void
     * 
     *         **** DO NOT EDIT *****
     */
    public void createLinkedStructure(String inputFile) {

        // DO NOT EDIT THIS CODE
        StdIn.setFile(inputFile);
        StdIn.readLine();

        // Reads the file one line at a time
        while (StdIn.hasNextLine()) {
            // Reads a single line from input file
            String line = StdIn.readLine();
            // IMPLEMENT these methods
            addToStateLevel(line);
            addToCountyLevel(line);
            addToCommunityLevel(line);
        }
    }

    /*
     * Adds a state to the first level of the linked structure.
     * Do nothing if the state is already present in the structure.
     * 
     * @param inputLine a line from the input file
     */
    public void addToStateLevel(String inputLine) {

        // WRITE YOUR CODE HERE
        String[] dataline = inputLine.split(",");
        StateNode holderforstate = new StateNode(dataline[2], null, null);

        if (firstState == null) {
            firstState = holderforstate;

        } else {
            // StateNode prev = firstState.item;
            StateNode pointer = firstState;
            while (pointer.next != null) {
                if (pointer.name.equals(dataline[2])) { // BREAK ALL DUPLICATES
                    return;
                }
                pointer = pointer.next; // this makes the previous pointer check foWARDSSS!!!
            }
            if (pointer.name.equals(dataline[2])) {
                return;
            }
            pointer.next = holderforstate;

        }
    }

    /*
     * Adds a county to a state's list of counties.
     * 
     * Access the state's list of counties' using the down pointer from the State
     * class.
     * Do nothing if the county is already present in the structure.
     * 
     * @param inputFile a line from the input file
     */
    public void addToCountyLevel(String inputLine) {

        // WRITE YOUR CODE HERE
        String[] dataline = inputLine.split(",");
        StateNode stateholder = firstState;
        CountyNode holderforcounty = new CountyNode(dataline[1], null, null);

        while (stateholder != null) {
            if (stateholder.getName().equals(dataline[2])) {
                break;
            } else {
                stateholder = stateholder.getNext();
              }
        }

        if (stateholder.getDown() == null) {
            stateholder.setDown(holderforcounty);
        } else {
            CountyNode pointer = stateholder.getDown();
            while (pointer.next != null) {
                if (pointer.name.equals(dataline[1])) { // BREAK ALL DUPLICATES
                    return;
                }
                pointer = pointer.next; // this makes the previous pointer check foWARDSSS!!!
            }
            if (pointer.name.equals(dataline[1])) {
                return;
            }
            pointer.next = holderforcounty;
        }
    }

    // using the get down for the community

    /*
     * Adds a community to a county's list of communities.
     * 
     * Access the county through its state
     * - search for the state first,
     * - then search for the county.
     * Use the state name and the county name from the inputLine to search.
     * 
     * Access the state's list of counties using the down pointer from the StateNode
     * class.
     * Access the county's list of communities using the down pointer from the
     * CountyNode class.
     * Do nothing if the community is already present in the structure.
     * 
     * @param inputFile a line from the input file
     */
    public void addToCommunityLevel(String inputLine) {

        // WRITE YOUR CODE HERE

        String[] dataline = inputLine.split(",");
        StateNode stateholder = firstState;
        Data dataholder = new Data(Double.parseDouble(dataline[3]),
                Double.parseDouble(dataline[4]),
                Double.parseDouble(dataline[5]),
                Double.parseDouble(dataline[8]),
                Double.parseDouble(dataline[9]),
                (dataline[19]),
                Double.parseDouble(dataline[49]),
                Double.parseDouble(dataline[37]),
                Double.parseDouble(dataline[121]));
        CountyNode holderforcounty = new CountyNode(dataline[1], null, null);
        CommunityNode holderforcommunity = new CommunityNode(dataline[0], null, dataholder);

        while (stateholder != null) {
            if (stateholder.getName().equals(dataline[2])) {
                break;
            } else {
                stateholder = stateholder.getNext();
            }
        }
        holderforcounty = stateholder.getDown();
        while (holderforcounty != null) {
            if (holderforcounty.getName().equals(dataline[1])) { // BREAK ALL DUPLICATES
                break;
            } else {
                holderforcounty = holderforcounty.getNext();
            }
        }

        if (holderforcounty.getDown() == null) {
            holderforcounty.setDown(holderforcommunity);
        } else {

            CommunityNode pointer = holderforcounty.getDown();
            while (pointer.next != null) {
                if (pointer.name.equals(dataline[0])) {
                    return;
                }
                pointer = pointer.next;

            }
            if (pointer.name.equals(dataline[0])) {
                return;
            }
            pointer.next = holderforcommunity;
        }

    }

    /**
     * Given a certain percentage and racial group inputted by user, returns
     * the number of communities that have that said percentage or more of racial
     * group
     * and are identified as disadvantaged
     * 
     * Percentages should be passed in as integers for this method.
     * 
     * @param userPrcntage the percentage which will be compared with the racial
     *                     groups
     * @param race         the race which will be returned
     * @return the amount of communities that contain the same or higher percentage
     *         of the given race
     */
    public int disadvantagedCommunities(double userPrcntage, String race) {

        // WRITE YOUR CODE HERE

        StateNode stateholder = firstState;

        int counter = 0;
        while (stateholder != null) {
            CountyNode countypointer = stateholder.getDown();
            while (countypointer != null) {
                CommunityNode communitypointer = countypointer.getDown();
                while (communitypointer != null) {

                    if (communitypointer.getInfo().getAdvantageStatus().equals("True")) {
                        if (race.equals("African American")) {
                            if ((userPrcntage) <= communitypointer.getInfo().getPrcntAfricanAmerican() * 100) {
                                counter++;
                            }
                        }
                        if (race.equals("Asian American")) {
                            if ((userPrcntage) <= communitypointer.getInfo().getPrcntAsian() * 100) {
                                counter++;
                            }
                        }
                        if (race.equals("Hispanic American")) {
                            if ((userPrcntage) <= communitypointer.getInfo().getPrcntHispanic() * 100) {
                                counter++;
                            }
                        }
                        if (race.equals("Native American")) {
                            if ((userPrcntage) <= communitypointer.getInfo().getPrcntNative() * 100) {
                                counter++;
                            }
                        }
                        if (race.equals("White American")) {
                            if ((userPrcntage) <= communitypointer.getInfo().getPrcntWhite() * 100) {
                                counter++;
                            }
                        }
                    }
                    communitypointer = communitypointer.getNext();
                }
                countypointer = countypointer.getNext();
            }
            stateholder = stateholder.getNext();
        }
        return counter; // replace this line
    }

    /**
     * Given a certain percentage and racial group inputted by user, returns
     * the number of communities that have that said percentage or more of racial
     * group
     * and are identified as non disadvantaged
     * 
     * Percentages should be passed in as integers for this method.
     * 
     * @param userPrcntage the percentage which will be compared with the racial
     *                     groups
     * @param race         the race which will be returned
     * @return the amount of communities that contain the same or higher percentage
     *         of the given race
     */
    public int nonDisadvantagedCommunities(double userPrcntage, String race) {

        StateNode stateholder = firstState;

        int counter = 0;
        while (stateholder != null) {
            CountyNode countypointer = stateholder.getDown();
            while (countypointer != null) {
                CommunityNode communitypointer = countypointer.getDown();
                while (communitypointer != null) {

                    if (communitypointer.getInfo().getAdvantageStatus().equals("False")) {
                        if (race.equals("African American")) {
                            if ((userPrcntage) <= communitypointer.getInfo().getPrcntAfricanAmerican() * 100) {
                                counter++;
                            }
                        }
                        if (race.equals("Asian American")) {
                            if ((userPrcntage) <= communitypointer.getInfo().getPrcntAsian() * 100) {
                                counter++;
                            }
                        }
                        if (race.equals("Hispanic American")) {
                            if ((userPrcntage) <= communitypointer.getInfo().getPrcntHispanic() * 100) {
                                counter++;
                            }
                        }
                        if (race.equals("Native American")) {
                            if ((userPrcntage) <= communitypointer.getInfo().getPrcntNative() * 100) {
                                counter++;
                            }
                        }
                        if (race.equals("White American")) {
                            if ((userPrcntage) <= communitypointer.getInfo().getPrcntWhite() * 100) {
                                counter++;
                            }
                        }
                    }
                    communitypointer = communitypointer.getNext();
                }
                countypointer = countypointer.getNext();
            }
            stateholder = stateholder.getNext();
        }

        return counter; // replace this line
    }

    /**
     * Returns a list of states that have a PM (particulate matter) level
     * equal to or higher than value inputted by user.
     * 
     * @param PMlevel the level of particulate matter
     * @return the States which have or exceed that level
     */
    public ArrayList<StateNode> statesPMLevels(double PMlevel) {

        // WRITE YOUR METHOD HERE .add(ptr)
        ArrayList<StateNode> AddtoStateList = new ArrayList<>();
        StateNode stateholder = firstState;
        while (stateholder != null) {
            CountyNode countypointer = stateholder.getDown();
            while (countypointer != null) {
                CommunityNode communitypointer = countypointer.getDown();
                while (communitypointer != null) {
                    if (communitypointer.getInfo().PMlevel >= (PMlevel)) {
                        AddtoStateList.add(stateholder);
                        countypointer = null;
                        break;
                    }

                    communitypointer = communitypointer.getNext();
                }
                if (countypointer != null) {

                    countypointer = countypointer.getNext();
                }
            }
            stateholder = stateholder.getNext();
        }

        return AddtoStateList; // replace this line
    }

    /**
     * Given a percentage inputted by user, returns the number of communities
     * that have a chance equal to or higher than said percentage of
     * experiencing a flood in the next 30 years.
     * 
     * @param userPercntage the percentage of interest/comparison
     * @return the amount of communities at risk of flooding
     */
    public int chanceOfFlood(double userPercntage) {

        // WRITE YOUR METHOD HERE
        int counter = 0;
        StateNode stateholder = firstState;
        while (stateholder != null) {
            CountyNode countypointer = stateholder.getDown();
            while (countypointer != null) {
                CommunityNode communitypointer = countypointer.getDown();
                while (communitypointer != null) {
                    if (communitypointer.getInfo().getChanceOfFlood() >= userPercntage) {
                        counter++;
                    }

                    communitypointer = communitypointer.getNext();
                }
                countypointer = countypointer.getNext();
            }
            stateholder = stateholder.getNext();
        }

        return counter; // replace this line
    }

    /**
     * Given a state inputted by user, returns the communities with
     * the 10 lowest incomes within said state.
     * 
     * @param stateName the State to be analyzed
     * @return the top 10 lowest income communities in the State, with no particular
     *         order
     */
    public ArrayList<CommunityNode> lowestIncomeCommunities(String stateName) {

        // WRITE YOUR METHOD HERE
        int j = 0;
        // int highest = 0;
        ArrayList<CommunityNode> TenLowestCom = new ArrayList<>();
        StateNode stateholder = firstState;
        // while (stateholder.getName().equals(stateName)){
        while (stateholder != null) {
            if (stateholder.getName().equals(stateName)) {
                break;
            } else {
                stateholder = stateholder.getNext();
            }
        }
        CountyNode countypointer = stateholder.getDown();
        while (countypointer != null) {
            CommunityNode communitypointer = countypointer.getDown();
            while (communitypointer != null) {
                if (TenLowestCom.size() < 10) {
                    TenLowestCom.add(communitypointer);
                } else {
                    double lowest =1001;
                    for (int i = 0; i < TenLowestCom.size(); i++) {
                        if (lowest > TenLowestCom.get(i).getInfo().getPercentPovertyLine()) {
                            lowest = TenLowestCom.get(i).getInfo().getPercentPovertyLine(); 
                            j = i;
                        }
                     } 
                if(TenLowestCom.get(j).getInfo().getPercentPovertyLine() < communitypointer.getInfo().getPercentPovertyLine()){
                    TenLowestCom.set(j, communitypointer);
                }
                }
                communitypointer = communitypointer.getNext();
            }  
            countypointer = countypointer.getNext();
        }
        return TenLowestCom; // replace this line
    }
}

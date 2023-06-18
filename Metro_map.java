import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;

public class Metro_map {

    private static final String[] stationNames = {
            "Kashmere Gate", "Tis Hazari", "Pulbangash", "Pratap Nagar", "Shastri Nagar",
            "Inderlok", "Kanhaiya Nagar", "Keshavpuram", "Netaji Subhash Place", "Chandni Chowk",
            "Chawri Bazaar", "New Delhi", "Rajiv Chowk", "Lal Quila", "Jama Masjid", "Delhi Gate",
            "ITO", "Mandi House", "Rajiv Chowk", "RK Ashram", "Jhandewalan", "Karol Bagh",
            "Rajendra Place", "Patel Nagar", "Shadipur", "Kirti Nagar", "Moti Nagar",
            "Ramesh Nagar", "Rajouri Garden", "ESI", "Punjabi Bagh West", "Shakurpur", "Punjabi Bagh",
            "Ashok Park Main", "Satguru Ram Singh Marg"
    };

    public static void main(String[] args) {
        int numStations = stationNames.length;

        // Create the graph
        List<List<Integer>> graph = new ArrayList<>();
        for (int i = 0; i < numStations; i++) {
            graph.add(new ArrayList<>());
        }

        // Add connections between stations
        graph.get(0).add(1); // Kashmere Gate -> Tis Hazari
        graph.get(1).add(2); // Tis Hazari -> Pulbangash
        graph.get(2).add(3); // Pulbangash -> Pratap Nagar
        graph.get(3).add(4); // Pratap Nagar -> Shastri Nagar
        graph.get(4).add(5); // Shastri Nagar -> Inderlok
        graph.get(5).add(6); // Inderlok -> Kanhaiya Nagar
        graph.get(6).add(7); // Kanhaiya Nagar -> Keshavpuram
        graph.get(7).add(8); // Keshavpuram -> Netaji Subhash Place

        graph.get(0).add(9); // Kashmere Gate -> Chandni Chowk (Yellow Line Transfer)
        graph.get(9).add(10); // Chandni Chowk -> Chawri Bazaar
        graph.get(10).add(11); // Chawri Bazaar -> New Delhi
        graph.get(11).add(12); // New Delhi -> Rajiv Chowk

        graph.get(0).add(13); // Kashmere Gate -> Lal Quila (Violet Line transfer)
        graph.get(13).add(14); // Lal Quila -> Jama Masjid
        graph.get(14).add(15); // Jama Masjid -> Delhi Gate
        graph.get(15).add(16); // Delhi Gate -> ITO
        graph.get(16).add(17); // ITO -> Mandi House
        graph.get(17).add(12); // Mandi House -> Rajiv Chowk

        graph.get(12).add(20); // Rajiv Chowk -> RK Ashram
        graph.get(20).add(21); // RK Ashram -> Jhandewalan
        graph.get(21).add(22); // Jhandewalan -> Karol Bagh
        graph.get(22).add(23); // Karol Bagh -> Rajendra Place
        graph.get(23).add(24); // Rajendra Place -> Patel Nagar
        graph.get(24).add(25); // Patel Nagar -> Shadipur
        graph.get(25).add(26); // Shadipur -> Kirti Nagar
        graph.get(26).add(27); // Kirti Nagar -> Moti Nagar
        graph.get(27).add(28); // Moti Nagar -> Ramesh Nagar
        graph.get(28).add(29); // Ramesh Nagar -> Rajouri Garden

        graph.get(29).add(30); // Rajouri Garden -> ESI
        graph.get(30).add(31); // ESI -> Punjabi Bagh West
        graph.get(31).add(32); // Punjabi Bagh West -> Shakurpur

        graph.get(31).add(33); // Punjabi Bagh West -> Punjabi Bagh
        graph.get(33).add(18); // Punjabi Bagh -> Ashok Park Main
        graph.get(18).add(5); // Ashok Park Main -> Inderlok
        graph.get(18).add(19); // Ashok Park Main -> Satguru Ram Singh Marg
        graph.get(19).add(26); // Satguru Ram Singh Marg -> Kirti Nagar

        // Print the list of stations for user to choose
        System.out.println("List of Stations:");
        for (int i = 0; i < numStations; i++) {
            System.out.println(i + ": " + stationNames[i]);
        }

        Scanner scanner = new Scanner(System.in);
        boolean continueFlag = true;

        while (continueFlag) {
            // Get source and destination from the user
            System.out.print("\nEnter the source station number: ");
            int source = scanner.nextInt();
            System.out.print("Enter the destination station number: ");
            int destination = scanner.nextInt();

            // Check if the source and destination are valid station numbers
            if (source >= 0 && source < numStations && destination >= 0 && destination < numStations) {
                // Calculate the shortest path
                calculateShortestPath(graph, source, destination);

                // Ask the user if they want to continue
                System.out.print("\nDo you want to continue? Enter '1' for Yes, any other number for No: ");
                int choice = scanner.nextInt();
                continueFlag = (choice == 1);
            } else {
                System.out.println("Invalid station number. Please try again.");
            }
        }
    }

    private static void calculateShortestPath(List<List<Integer>> graph, int source, int destination) {
        int numStations = graph.size();

        int[] distances = new int[numStations];
        Arrays.fill(distances, Integer.MAX_VALUE);
        distances[source] = 0;

        int[] previous = new int[numStations];
        Arrays.fill(previous, -1);

        boolean[] visited = new boolean[numStations];

        for (int i = 0; i < numStations - 1; i++) {
            int minDistance = Integer.MAX_VALUE;
            int minIndex = -1;

            for (int j = 0; j < numStations; j++) {
                if (!visited[j] && distances[j] < minDistance) {
                    minDistance = distances[j];
                    minIndex = j;
                }
            }

            if (minIndex == -1) {
                break;
            }

            visited[minIndex] = true;

            List<Integer> neighbors = graph.get(minIndex);
            for (int neighbor : neighbors) {
                int distance = distances[minIndex] + 1; // Assuming distance between stations is 1 km

                if (distance < distances[neighbor]) {
                    distances[neighbor] = distance;
                    previous[neighbor] = minIndex;
                }
            }
        }

        // Check if a path exists between the source and destination
        if (distances[destination] == Integer.MAX_VALUE) {
            System.out.println("No path found between the source and destination.");
            return;
        }

        // Build the shortest path
        List<Integer> shortestPath = new ArrayList<>();
        int currentNode = destination;
        while (currentNode != -1) {
            shortestPath.add(0, currentNode);
            currentNode = previous[currentNode];
        }

        // Print the shortest path
        System.out.println("\nShortest Path:");
        for (int i = 0; i < shortestPath.size(); i++) {
            int stationNumber = shortestPath.get(i);
            String stationName = stationNames[stationNumber];
            System.out.println(stationNumber + ": " + stationName);
        }

        // Calculate the total distance covered
        int distanceCovered = distances[destination];

        // Print the total distance covered
        System.out.println("\nTotal Distance Covered: " + distanceCovered + " km");
    }
}

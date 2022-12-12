import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

public class HashTable<T>{
    NGen<T>[] hashTable;
    int pos = 0;
    public HashTable(int length) {
        hashTable = new NGen[length];
    }



    //TODO: Create a constructor that takes in a length and initializes the hash table array

    //TODO: Implement a simple hash function
    public int hash1(T item) {
        // This line converts the item to a string
        String str = item.toString();
        int sum = 0;
        // This loop iterates through each character in the string
        for(int i = 0; i < str.length(); i++) {
            // adds the ASCII value of each character to the sum
            sum += str.charAt(i);
        }
        // return the remainder of the sum divided by the length of the hash table
        return sum % hashTable.length;
    }



    //TODO: Implement a second (different and improved) hash function
    public int hash2(T item) {
        String str = item.toString();
        int hash = 0;
        // iterates through each character in the string
        for(int i = 0; i < str.length(); i++) {
            // multiplies the hash by 31 and adds the ASCII value of the character
            hash = (hash * 31) + str.charAt(i);
        }
        // returns the remainder of the hash divided by the length of the hash table
        return Math.abs(hash % hashTable.length);
    }


    //TODO: Implement the add method which adds an item to the hash table using your best performing hash function
    // Does NOT add duplicate items
    public void add(T item) {
        int hash = hash2(item);
        // This checks if there is no element at the index determined by the hash in the hash table
        if(hashTable[hash] == null) {
            hashTable[hash] = new NGen<T>(item);
            NGen<T> current = hashTable[hash];
            // This loop iterates through the linked list at the index determined by the hash in the hash table until it reaches the end
            while(current.getNext() != null) {
                // This checks if the item is already present in the linked list
                if(current.getData().equals(item)) {
                    return;
                }
                current = current.getNext();
            }
//          // If the item is not already present in the linked list, this line inserts the item into the linked list
            current.setNext(new NGen<T>(item));
        }


    }

    // ** Already implemented -- no need to change **
    // Adds all words from a given file to the hash table using the add(T item) method above
    @SuppressWarnings("unchecked")
    public void addWordsFromFile(String fileName) {
        Scanner fileScanner = null;
        String word;
        try {
            fileScanner = new Scanner(new File(fileName));
        }
        catch (FileNotFoundException e) {
            System.out.println("File: " + fileName + " not found.");
            System.exit(1);
        }
        while (fileScanner.hasNext()) {
            word = fileScanner.next();
            word = word.replaceAll("\\p{Punct}", ""); // removes punctuation
            this.add((T) word);
        }
    }

    //TODO: Implement the display method which prints the indices of the hash table and the number of words "hashed"
    // to each index. Also prints:
    // - total number of unique words
    // - number of empty indices
    // - number of nonempty indices
    // - average collision length
    // - length of longest chain
    public void display() {
        int empty = 0, nonEmpty = 0;
        int total = 0;
        int max = 0;
        double avg = 0;
        for (int i=0; i<hashTable.length; i++) {
            // if the current element is null, increment the empty counter
            if (hashTable[i] == null) {
                empty++;
            } else {
                // otherwise, increment the nonempty counter
                nonEmpty++;
                int count = 1;
                // create a temporary reference to the first element in the current chain
                NGen<T> temp = hashTable[i];
                // iterate over the remaining elements in the chain
                while (temp.getNext() != null) {
                    count++;
                    temp = temp.getNext();
                }
                // if the count for the current chain is greater than the current longest chain, update the longest chain variable
                if (count > max) {
                    max = count;
                }
                // add the count for the current chain to the total count and the average collision length
                avg += count;
                total += count;

                System.out.println("Index " + i + " has " + count + " words.");
            }
        }
        System.out.println("Total unique words: " + total);
        System.out.println("Empty indices: " + empty);
        System.out.println("Nonempty indices: " + nonEmpty);
        System.out.println("Average collision length: " + avg/nonEmpty);
        System.out.println("Longest chain: " + max);
    }



    // TODO: Create a hash table, store all words from "canterbury.txt", and display the table
    //  Create another hash table, store all words from "keywords.txt", and display the table
    public static void main(String args[]) {
        // creates a new HashTable object with a capacity of 100
        HashTable<String> hash = new HashTable<String>(100);
        hash.addWordsFromFile("src/canterbury.txt");
        hash.display();
        System.out.println();
        hash = new HashTable<String>(100);
        hash.addWordsFromFile("src/keywords.txt");
        hash.display();
    }



}


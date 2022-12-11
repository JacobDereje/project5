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
        String str = item.toString();
        int sum = 0;
        for(int i = 0; i < str.length(); i++) {
            sum += str.charAt(i);
        }
        return sum % hashTable.length;

    }

    //TODO: Implement a second (different and improved) hash function
    public int hash2(T item) {
        String str = item.toString();
        int hash = 0;
        for(int i = 0; i < str.length(); i++) {
            hash = (hash * 31) + str.charAt(i);
        }
        return Math.abs(hash % hashTable.length);

    }


    //TODO: Implement the add method which adds an item to the hash table using your best performing hash function
    // Does NOT add duplicate items
    public void add(T item) {
        int hash = hash2(item);
        if(hashTable[hash] == null) {
            hashTable[hash] = new NGen<T>(item);
//        } else {
            NGen<T> current = hashTable[hash];
            while(current.getNext() != null) {
                if(current.getData().equals(item)) {
                    return;
                }
                current = current.getNext();
            }
//            if(current.getData().equals(item)) {
//                return;
//            }
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
        int empty = 0, nonEmpty = 0, total = 0, max = 0;
        double avg = 0;
        for (int i=0; i<hashTable.length; i++) {
            if (hashTable[i] == null) {
                empty++;
            } else {
                nonEmpty++;
                int count = 1;
                NGen<T> temp = hashTable[i];
                while (temp.getNext() != null) {
                    count++;
                    temp = temp.getNext();
                }
                if (count > max) {
                    max = count;
                }
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
        HashTable<String> hash = new HashTable<String>(100);
        hash.addWordsFromFile("src/canterbury.txt");
        hash.display();
        System.out.println();
        hash = new HashTable<String>(100);
        hash.addWordsFromFile("src/keywords.txt");
        hash.display();
    }



}


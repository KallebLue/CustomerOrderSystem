package project1.COS;

import java.io.*;
import java.util.ArrayList;

/**
 * A generic utility class for saving and loading {@link ArrayList}s of objects
 * to and from a file using Java's serialization mechanism.
 * This class handles the low-level file I/O operations, making it easier
 * to persist collections of serializable objects.
 *
 * @param <T> The type of objects contained within the ArrayList to be stored.
 * This type must implement the {@link Serializable} interface.
 */
public class FileStorage<T> {

    /**
     * The name of the file used for storing and loading the ArrayList.
     */
    private final String filename;

    /**
     * Constructs a new FileStorage instance.
     *
     * @param filename The name of the file where the data will be saved or loaded from.
     */
    public FileStorage(String filename) {
        this.filename = filename;
    }

    /**
     * Saves the provided {@link ArrayList} of objects to the file specified during construction.
     * The objects in the list must be {@link Serializable}.
     * Any {@link IOException} that occurs during saving will be caught and an error message will be printed.
     *
     * @param list The {@link ArrayList} of objects to be saved.
     */
    public void save(ArrayList<T> list) {
        try (ObjectOutputStream out = new ObjectOutputStream(new FileOutputStream(filename))) {
            out.writeObject(list);
        } catch (IOException e) {
            System.err.println("Error saving data to " + filename + ": " + e.getMessage());
        }
    }

    /**
     * Loads an {@link ArrayList} of objects from the file specified during construction.
     * If the file does not exist or an error occurs during loading (e.g., {@link IOException},
     * {@link ClassNotFoundException}), an empty {@link ArrayList} is returned.
     *
     * @return An {@link ArrayList} containing the loaded objects, or an empty {@link ArrayList}
     * if the file does not exist or loading fails.
     */
    public ArrayList<T> load() {
        try (ObjectInputStream in = new ObjectInputStream(new FileInputStream(filename))) {
            // Suppress unchecked cast warning as we are confident about the type from save method
            @SuppressWarnings("unchecked")
            ArrayList<T> loadedList = (ArrayList<T>) in.readObject();
            return loadedList;
        } catch (IOException | ClassNotFoundException e) {
            // If the file doesn't exist yet, or there's a problem loading, return an empty list
            // System.err.println("Error loading data from " + filename + ": " + e.getMessage()); // Optional: for debugging
            return new ArrayList<>();
        }
    }
}
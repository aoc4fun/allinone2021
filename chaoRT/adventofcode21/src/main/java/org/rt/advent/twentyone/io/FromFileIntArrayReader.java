package org.rt.advent.twentyone.io;

import java.io.*;
import java.util.ArrayList;
import java.util.function.Function;

public class FromFileIntArrayReader implements IntArrayReader{
    private final BufferedReaderFactory source;

    public FromFileIntArrayReader(BufferedReaderFactory source) {
        this.source=source;
    }

    @Override
    public int[] read() {
        ArrayList<Integer> result = new ArrayList<Integer>();

        try(BufferedReader reader = source.openReader()) {
            String line = reader.readLine();
            while (line != null) {
                result.add(Integer.valueOf(line));
                line = reader.readLine();
            }

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return result.stream().mapToInt(i->i).toArray();
    }
}

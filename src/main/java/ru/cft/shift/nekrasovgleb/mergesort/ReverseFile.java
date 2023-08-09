package ru.cft.shift.nekrasovgleb.mergesort;

import org.apache.commons.io.input.ReversedLinesFileReader;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.charset.StandardCharsets;

public class ReverseFile {
    public static void reverse(String fileName) {
        String tempOutputFileName = "temp" + fileName;

        try (ReversedLinesFileReader reader = ReversedLinesFileReader.builder()
                .setPath(fileName)
                .setCharset(StandardCharsets.UTF_8)
                .get()) {

            String currentLine = reader.readLine();

            if (currentLine != null) {
                try (BufferedWriter writer = new BufferedWriter(new FileWriter(tempOutputFileName))) {
                    while (currentLine != null) {
                        writer.write(currentLine);
                        currentLine = reader.readLine();

                        if (currentLine != null) {
                            writer.newLine();
                        }
                    }
                } catch (IOException e) {
                    System.out.println("Не удалось создать временный файл для разворота порядка строк в файле.");
                }
            }
        } catch (IOException e) {
            System.out.println("Не удалось прочитать временный выходной файл с данными, отсортированными по возрастанию.");
        }

        try {
            File unReversedFile = new File(fileName);
            boolean isUnReversedFileDeleted = unReversedFile.delete();

            if (!isUnReversedFileDeleted) {
                System.out.println("Не удалось развернуть порядок строк в файле.");
                return;
            }

            File reversedFile = new File(tempOutputFileName);
            boolean isSuccess = reversedFile.renameTo(unReversedFile);

            if (!isSuccess) {
                System.out.println("Не удалось создать выходной файл.");
            }
        } catch (Exception e) {
            System.out.println("Не удалось создать выходной файл.");
        }
    }
}
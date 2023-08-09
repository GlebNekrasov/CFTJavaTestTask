package ru.cft.shift.nekrasovgleb.mergesort;

import java.io.*;
import java.util.*;

public class MergeSort {

    public static void sortStringFiles(String[] inputFiles, String outputFile) {
        Queue<String> preparedInputFiles = prepareStringFiles(inputFiles);

        if (preparedInputFiles.size() == 0) {
            System.out.println("Ни один входной файл не содержит валидные данные. Поэтому выходной файл не был создан.");
            return;
        }

        int i = 0;

        while (preparedInputFiles.size() != 1) {
            String fileName1 = preparedInputFiles.remove();
            String fileName2 = preparedInputFiles.remove();
            String mergedFile = "mergedFile" + i + ".txt";

            try (Scanner scanner1 = new Scanner(new BufferedReader(new FileReader(fileName1)));
                 Scanner scanner2 = new Scanner(new BufferedReader(new FileReader(fileName2)));
                 BufferedWriter writer = new BufferedWriter(new FileWriter(mergedFile))) {
                String line1 = scanner1.nextLine();
                String line2 = scanner2.nextLine();

                while (line1 != null || line2 != null) {
                    if (line1 == null) {
                        writer.write(line2);

                        if (scanner2.hasNext()) {
                            line2 = scanner2.nextLine();
                        } else {
                            line2 = null;
                        }
                    } else if (line2 == null) {
                        writer.write(line1);

                        if (scanner1.hasNext()) {
                            line1 = scanner1.nextLine();
                        } else {
                            line1 = null;
                        }
                    } else if (line1.compareTo(line2) < 0) {
                        writer.write(line1);

                        if (scanner1.hasNext()) {
                            line1 = scanner1.nextLine();
                        } else {
                            line1 = null;
                        }
                    } else {
                        writer.write(line2);

                        if (scanner2.hasNext()) {
                            line2 = scanner2.nextLine();
                        } else {
                            line2 = null;
                        }
                    }

                    if (line1 != null || line2 != null) {
                        writer.newLine();
                    }
                }

                preparedInputFiles.add(mergedFile);
            } catch (IOException e) {
                System.out.println("Не удалось обработать часть входных файлов.");
            }

            deleteTempFile(fileName1);
            deleteTempFile(fileName2);

            ++i;
        }

        createSortedFile(preparedInputFiles.remove(), outputFile);
    }

    public static void sortIntFiles(String[] inputFiles, String outputFile) {
        Queue<String> preparedInputFiles = prepareIntFiles(inputFiles);

        if (preparedInputFiles.size() == 0) {
            System.out.println("Ни один входной файл не содержит валидные данные. Поэтому выходной файл не был создан.");
            return;
        }

        int i = 0;

        while (preparedInputFiles.size() != 1) {
            String fileName1 = preparedInputFiles.remove();
            String fileName2 = preparedInputFiles.remove();
            String mergedFile = "mergedFile" + i + ".txt";

            try (Scanner scanner1 = new Scanner(new BufferedReader(new FileReader(fileName1)));
                 Scanner scanner2 = new Scanner(new BufferedReader(new FileReader(fileName2)));
                 BufferedWriter writer = new BufferedWriter(new FileWriter(mergedFile))) {
                Integer int1 = scanner1.nextInt();
                Integer int2 = scanner2.nextInt();

                while (int1 != null || int2 != null) {
                    if (int1 == null) {
                        writer.write(int2.toString());

                        if (scanner2.hasNextInt()) {
                            int2 = scanner2.nextInt();
                        } else {
                            int2 = null;
                        }
                    } else if (int2 == null) {
                        writer.write(int1.toString());

                        if (scanner1.hasNextInt()) {
                            int1 = scanner1.nextInt();
                        } else {
                            int1 = null;
                        }
                    } else if (int1 < int2) {
                        writer.write(int1.toString());

                        if (scanner1.hasNextInt()) {
                            int1 = scanner1.nextInt();
                        } else {
                            int1 = null;
                        }
                    } else {
                        writer.write(int2.toString());

                        if (scanner2.hasNextInt()) {
                            int2 = scanner2.nextInt();
                        } else {
                            int2 = null;
                        }
                    }

                    if (int1 != null || int2 != null) {
                        writer.newLine();
                    }
                }

                preparedInputFiles.add(mergedFile);
            } catch (IOException e) {
                System.out.println("Не удалось обработать часть входных файлов.");
            }

            deleteTempFile(fileName1);
            deleteTempFile(fileName2);

            ++i;
        }

        createSortedFile(preparedInputFiles.remove(), outputFile);
    }

    private static Queue<String> prepareStringFiles(String[] inputFiles) {
        Queue<String> preparedInputFiles = new LinkedList<>();

        ArrayList<String> notEmptyFiles = getNotEmptyFiles(inputFiles, "string");

        int notEmptyFilesCount = notEmptyFiles.size();

        for (int i = 0; i < notEmptyFilesCount / 2; ++i) {
            String outFile = "preparedInputFile" + i + ".txt";
            String inputFile1 = notEmptyFiles.get(i);
            String inputFile2 = notEmptyFiles.get(notEmptyFilesCount / 2 + i);

            try (Scanner scanner1 = new Scanner(new BufferedReader(new FileReader(inputFile1)));
                 Scanner scanner2 = new Scanner(new BufferedReader(new FileReader(inputFile2)));
                 BufferedWriter writer = new BufferedWriter(new FileWriter(outFile))) {
                String line1 = scanner1.nextLine();
                String line2 = scanner2.nextLine();

                while (line1 != null || line2 != null) {
                    if (line1 == null) {
                        writer.write(line2);
                        line2 = getNextLine(scanner2, line2);
                    } else if (line2 == null) {
                        writer.write(line1);
                        line1 = getNextLine(scanner1, line1);
                    } else if (line1.compareTo(line2) < 0) {
                        writer.write(line1);
                        line1 = getNextLine(scanner1, line1);
                    } else {
                        writer.write(line2);
                        line2 = getNextLine(scanner2, line2);
                    }

                    if (line1 != null || line2 != null) {
                        writer.newLine();
                    }
                }

                preparedInputFiles.add(outFile);
            } catch (IOException e) {
                System.out.println("Не удалось обработать входные файлы " + inputFile1 + " и " + inputFile2);
            }
        }

        if (notEmptyFilesCount % 2 == 1) {
            String outFile = "preparedInputFile" + (notEmptyFilesCount / 2 + 1) + ".txt";
            String inputFile = notEmptyFiles.get(notEmptyFilesCount - 1);

            try (Scanner scanner = new Scanner(new BufferedReader(new FileReader(inputFile)));
                 BufferedWriter writer = new BufferedWriter(new FileWriter(outFile))) {
                String line = scanner.nextLine();

                while (line != null) {
                    writer.write(line);
                    line = getNextLine(scanner, line);

                    if (line != null) {
                        writer.newLine();
                    }
                }

                preparedInputFiles.add(outFile);
            } catch (IOException e) {
                System.out.println("Не удалось обработать входной файл " + inputFile);
            }
        }

        return preparedInputFiles;
    }

    private static Queue<String> prepareIntFiles(String[] inputFiles) {
        Queue<String> preparedInputFiles = new LinkedList<>();

        ArrayList<String> notEmptyFiles = getNotEmptyFiles(inputFiles, "int");

        int notEmptyFilesCount = notEmptyFiles.size();

        for (int i = 0; i < notEmptyFilesCount / 2; ++i) {
            String outFile = "preparedInputFile" + i + ".txt";
            String inputFile1 = notEmptyFiles.get(i);
            String inputFile2 = notEmptyFiles.get(notEmptyFilesCount / 2 + i);

            try (Scanner scanner1 = new Scanner(new BufferedReader(new FileReader(inputFile1)));
                 Scanner scanner2 = new Scanner(new BufferedReader(new FileReader(inputFile2)));
                 BufferedWriter writer = new BufferedWriter(new FileWriter(outFile))) {
                Integer int1 = Integer.parseInt(scanner1.nextLine());
                Integer int2 = Integer.parseInt(scanner2.nextLine());

                while (int1 != null || int2 != null) {
                    if (int1 == null) {
                        writer.write(int2.toString());
                        int2 = getNextInt(scanner2, int2);
                    } else if (int2 == null) {
                        writer.write(int1.toString());
                        int1 = getNextInt(scanner1, int1);
                    } else if (int1 < int2) {
                        writer.write(int1.toString());
                        int1 = getNextInt(scanner1, int1);
                    } else {
                        writer.write(int2.toString());
                        int2 = getNextInt(scanner2, int2);
                    }

                    if (int1 != null || int2 != null) {
                        writer.newLine();
                    }
                }

                preparedInputFiles.add(outFile);
            } catch (IOException e) {
                System.out.println("Не удалось обработать входные файлы " + inputFile1 + " и " + inputFile2);
            }
        }

        if (notEmptyFilesCount % 2 == 1) {
            String outFile = "preparedInputFile" + (notEmptyFilesCount / 2 + 1) + ".txt";
            String inputFile = notEmptyFiles.get(notEmptyFilesCount - 1);

            try (Scanner scanner = new Scanner(new BufferedReader(new FileReader(inputFile)));
                 BufferedWriter writer = new BufferedWriter(new FileWriter(outFile))) {
                Integer int1 = Integer.parseInt(scanner.nextLine());

                while (int1 != null) {
                    writer.write(int1.toString());
                    int1 = getNextInt(scanner, int1);

                    if (int1 != null) {
                        writer.newLine();
                    }
                }

                preparedInputFiles.add(outFile);
            } catch (IOException e) {
                System.out.println("Не удалось обработать входной файл " + inputFile);
            }
        }

        return preparedInputFiles;
    }

    private static ArrayList<String> getNotEmptyFiles(String[] inputFiles, String dataType) {
        ArrayList<String> notEmptyFiles = new ArrayList<>();

        for (String inputFile : inputFiles) {
            try (Scanner scanner = new Scanner(new FileReader(inputFile))) {
                String validationError = String.format("%s%n", "Файл " + inputFile +
                        " не прошел валидацию и не будет обработан.");

                if (!scanner.hasNext()) {
                    System.out.println(validationError);
                    continue;
                }

                String firstLine = scanner.nextLine();

                if (dataType.equals("string") && !firstLine.contains(" ") && !firstLine.equals("")) {
                    notEmptyFiles.add(inputFile);
                } else if (dataType.equals("int") && !firstLine.contains(" ") && !firstLine.equals("")) {
                    try {
                        Integer.parseInt(firstLine);
                        notEmptyFiles.add(inputFile);
                    } catch (NumberFormatException e) {
                        System.out.println(validationError);
                    }
                } else {
                    System.out.println(validationError);
                }
            } catch (IOException e) {
                System.out.println("Не удалось прочитать файл " + inputFile);
            }
        }

        return notEmptyFiles;
    }

    private static String getNextLine(Scanner scanner, String currentLine) {
        String nextLine;

        if (scanner.hasNextLine()) {
            nextLine = scanner.nextLine();

            if (nextLine != null && (nextLine.contains(" ") || nextLine.equals("") || nextLine.compareTo(currentLine) < 0)) {
                nextLine = null;
            }
        } else {
            nextLine = null;
        }

        return nextLine;
    }

    private static Integer getNextInt(Scanner scanner, Integer currentInt) {
        Integer nextInt;

        if (scanner.hasNextLine()) {
            String nextLine = scanner.nextLine();

            if (nextLine != null && (nextLine.contains(" ") || nextLine.equals(""))) {
                nextInt = null;
            } else if (nextLine != null) {
                try {
                    nextInt = Integer.parseInt(nextLine);

                    if (nextInt < currentInt) {
                        nextInt = null;
                    }
                } catch (NumberFormatException e) {
                    nextInt = null;
                }
            } else {
                nextInt = null;
            }
        } else {
            nextInt = null;
        }

        return nextInt;
    }

    private static void createSortedFile(String sortedFileName, String outputFileName) {
        try {
            boolean isFileCreated;
            File sortedFile = new File(sortedFileName);
            File finalFile = new File(outputFileName);

            if (!finalFile.exists()) {
                isFileCreated = sortedFile.renameTo(finalFile);

                if (!isFileCreated) {
                    System.out.println("Не удалось создать выходной файл с именем, указанным при запуске программы. " +
                            "Поэтому был создан выходной файл с другим именем - " + sortedFileName);
                } else {
                    System.out.println("Выходной файл успешно создан.");
                }
            } else {
                boolean isExistingFileDeleted = finalFile.delete();

                if (isExistingFileDeleted) {
                    isFileCreated = sortedFile.renameTo(finalFile);

                    if (!isFileCreated) {
                        System.out.println("Не удалось создать выходной файл с именем, указанным при запуске программы. " +
                                "Поэтому был создан выходной файл с другим именем - " + sortedFileName);
                    } else {
                        System.out.println("Выходной файл успешно создан.");
                    }
                } else {
                    System.out.println("При запуске программы в качестве выходного файла был указан уже существующий" +
                            " файл, который не удалось перезаписать. Поэтому был создан другой выходной файл - " +
                            sortedFileName);
                }
            }
        } catch (Exception e) {
            System.out.println("Не удалось создать выходной файл с именем, указанным при запуске программы. " +
                    "Поэтому был создан выходной файл с другим именем - " + sortedFileName);
        }
    }

    private static void deleteTempFile(String fileName) {
        File tempFile = new File(fileName);
        boolean isFileDeleted = tempFile.delete();

        if (!isFileDeleted) {
            System.out.println("Не удалось удалить промежуточный файл " + tempFile);
        }
    }
}
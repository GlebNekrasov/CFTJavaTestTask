package ru.cft.shift.nekrasovgleb.mergesort;

import java.util.ArrayList;
import java.util.Arrays;

public class MergeSortApp {
    public static void main(String[] args) {
        if (args.length == 0) {
            System.out.println("При запуске программы нужно указать параметры.");
            Help.printHelp();
            return;
        }

        if (args.length < 3) {
            System.out.println("При запуске программы нужно указать все необходимые параметры.");
            Help.printHelp();
            return;
        }

        boolean isAscendingSort = true;
        boolean isDataTypeEntered = false;
        boolean isOutputFileEntered = false;
        boolean isInputFileEntered = false;

        String dataType = null;
        String outputFile = "";
        ArrayList<String> inputFiles = new ArrayList<>();

        String arg0 = args[0];

        switch (arg0) {
            case "-a":
                break;
            case "-d":
                isAscendingSort = false;
                break;
            case "-s":
                dataType = "string";
                isDataTypeEntered = true;
                break;
            case "-i":
                dataType = "int";
                isDataTypeEntered = true;
                break;
            default:
                System.out.println("При запуске программы указаны некорректные параметры.");
                Help.printHelp();
                return;
        }

        String arg1 = args[1];

        if (isDataTypeEntered) {
            switch (arg1) {
                case "-a":
                    break;
                case "-d":
                    isAscendingSort = false;
                    break;
                default:
                    outputFile = arg1;
                    isOutputFileEntered = true;
            }
        } else {
            switch (arg1) {
                case "-s":
                    dataType = "string";
                    break;
                case "-i":
                    dataType = "int";
                    break;
                default:
                    System.out.println("При запуске программы указаны некорректные параметры.");
                    Help.printHelp();
                    return;
            }
        }

        String arg2 = args[2];

        if (isOutputFileEntered) {
            inputFiles.add(arg2);
            isInputFileEntered = true;
        } else {
            outputFile = arg2;
        }

        if (!isInputFileEntered && args.length == 3) {
            System.out.println("При запуске программы нужно указать все необходимые параметры.");
            Help.printHelp();
            return;
        }

        inputFiles.addAll(Arrays.asList(args).subList(3, args.length));

        String[] inputFilesNames = new String[inputFiles.size()];
        inputFilesNames = inputFiles.toArray(inputFilesNames);

        if (isAscendingSort) {
            switch (dataType) {
                case "string":
                    MergeSort.sortStringFiles(inputFilesNames, outputFile);
                    break;
                case "int":
                    MergeSort.sortIntFiles(inputFilesNames, outputFile);
                    break;
                default:
                    System.out.println("Не удалось обработать параметр <тип данных>.");
                    Help.printHelp();
            }
        } else {
            switch (dataType) {
                case "string":
                    MergeSort.sortStringFiles(inputFilesNames, outputFile);
                    ReverseFile.reverse(outputFile);
                    break;
                case "int":
                    MergeSort.sortIntFiles(inputFilesNames, outputFile);
                    ReverseFile.reverse(outputFile);
                    break;
                default:
                    System.out.println("Не удалось обработать параметр <тип данных>.");
                    Help.printHelp();
            }
        }
    }
}
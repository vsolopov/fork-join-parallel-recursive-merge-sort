package com.vsolopov.fj.prms;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.ThreadLocalRandom;

public class Main {

    public static void main(String[] args) {
        List<Integer> integers = generateList(11, 100);

        System.out.println("Before sort:");
        printInLine(integers);

        System.out.println("\n After sort:");
        printInLine(ForkJoinPool.commonPool().invoke(new MergeSort<Integer>(integers)));
    }


    private static void printInLine(List<Integer> integers) {
        Objects.requireNonNull(integers);
        integers.forEach(i -> System.out.print(i + " "));
    }

    public static List<Integer> generateList(int size, int range) {
        List<Integer> list = new ArrayList<>();
        for (int i = 0; i < size; i++) {
            list.add(ThreadLocalRandom.current().nextInt(range));
        }
        return list;
    }

}

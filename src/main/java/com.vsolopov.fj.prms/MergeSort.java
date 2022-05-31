package com.vsolopov.fj.prms;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.RecursiveTask;

public class MergeSort<T extends Comparable<T>> extends RecursiveTask<List<T>> {

    private final List<T> targetList;

    public MergeSort(List<T> targetList) {
        Objects.requireNonNull(targetList);
        this.targetList = targetList;
    }

    @Override
    protected List<T> compute() {
        int length = targetList.size();
        int middle = length / 2;

        if (length < 2) {
            return targetList;
        }

        var leftList = new ArrayList<>(targetList.subList(0, middle));
        var rightList = new ArrayList<>(targetList.subList(targetList.size() - middle, targetList.size()));

        var leftSortTask = new MergeSort<>(leftList);
        var rightSortTask = new MergeSort<>(rightList);

        leftSortTask.fork();
        rightSortTask.compute();
        leftSortTask.join();

        merge(leftList, rightList);

        return targetList;
    }

    private void merge(List<T> leftList, List<T> rightList) {
        int leftIndex = 0 ,rightIndex = 0 , targetIndex = 0;

        while (leftIndex < leftList.size() && rightIndex < rightList.size()) {
            //target list initialisation from the left
            if (leftList.get(leftIndex).compareTo(rightList.get(rightIndex)) < 0)  {
                targetList.set(targetIndex, leftList.get(leftIndex));
                leftIndex++;
            }
            //target list initialisation from the right
            else {
                targetList.set(targetIndex, rightList.get(rightIndex));
                rightIndex++;
            }
            targetIndex++;
        }

        //sorting for the left values. (if list has odd length)
        for (int l = leftIndex; l < leftList.size(); l++) {
            targetList.set(targetIndex++, leftList.get(l));
        }
        for (int r = rightIndex; r < rightList.size(); r++) {
            targetList.set(targetIndex++, rightList.get(r));
        }

    }
}

package com.xiongtian.sparsearray;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by xiongtian on 2022/2/14 14:16
 *
 * @describe: 稀疏数组
 */
public class SparseArray {

    public static void main(String[] args) throws Exception {

        // 创建一个原始的二维数组11 * 11
        // 0：表示没有棋子  1：表示黑子  2：表示蓝色的子
        int[][] chessArr1 = new int[11][11];
        chessArr1[1][2] = 1;
        chessArr1[2][3] = 2;
        chessArr1[3][4] = 2;

        // 输出原始的二维数组
        System.out.println("原始的二维数组：");
        printDyadicArray(chessArr1);

        // 将二维数组转换为稀疏数组
        int[][] sparseArray = transformDyadicArrToSpareArr(chessArr1);

        String fileName="D:\\mytest.txt";

        // 讲数组存储到文件中
        writeArrToFile(sparseArray,fileName);

        // 从文件中读取数组
        System.out.println("从文件中读取数组");
        int[][] ints = readArrFromFile(fileName);
        printDyadicArray(ints);
        // 输出稀疏数组的形式
        System.out.println();
        System.out.println("得到稀疏数组为：~~~~~");
        /*for (int i = 0; i < sparseArray.length; i++) {
            System.out.printf("%d\t%d\t%d\t\n", sparseArray[i][0], sparseArray[i][1], sparseArray[i][2]);
        }*/
        printDyadicArray(sparseArray);

        System.out.println();

        int[][] chessArr2 = transformSparseArrToDyadicArr(sparseArray);

        //输出恢复后的二维数组
        System.out.println("恢复后的二维数组：");
        printDyadicArray(chessArr2);

    }

    /**
     * 将二维数组转换位稀疏数组
     *
     * @param chessArr1
     * @return
     */
    private static int[][] transformDyadicArrToSpareArr(int[][] chessArr1) {
        // 1.先遍历二维数组，得到非零数据的个数
        int sum = 0;
        for (int[] row : chessArr1) {
            for (int data : row) {
                if (data != 0) {
                    sum++;
                }
            }
        }
        // 2. 创建对应的稀疏数组
        int sparseArray[][] = new int[sum + 1][3];

        // 给稀疏数组赋值
        sparseArray[0][0] = 11;
        sparseArray[0][1] = 11;
        sparseArray[0][2] = sum;

        // 遍历二维数组，给稀疏数组赋值
        int count = 0; // count 用于记录是第几个非零数据
        for (int i = 0; i < chessArr1.length; i++) {
            for (int j = 0; j < chessArr1[i].length; j++) {
                if (chessArr1[i][j] != 0) {
                    count++;

                    sparseArray[count][0] = i;
                    sparseArray[count][1] = j;
                    sparseArray[count][2] = chessArr1[i][j];
                }
            }
        }
        return sparseArray;
    }

    /**
     * 将稀疏数组转换成二维数组
     *
     * @param sparseArray
     * @return
     */
    private static int[][] transformSparseArrToDyadicArr(int[][] sparseArray) {
        // 将稀疏数组恢复成二维数组
        // 1. 先读取稀疏数组第一行，根据第一行的数据，创建原始的二维数组
        int[][] chessArr2 = new int[sparseArray[0][0]][sparseArray[0][1]];

        // 2.读取稀疏数组后几行的数据，并赋值给原始的二维数组即可
        for (int i = 1; i < sparseArray.length; i++) {
            chessArr2[sparseArray[i][0]][sparseArray[i][1]] = sparseArray[i][2];
        }
        return chessArr2;
    }

    /**
     * 将二维数组进行输出
     *
     * @param arr
     */
    private static void printDyadicArray(int[][] arr) {
        for (int[] row : arr) {
            for (int data : row) {
                System.out.printf("%d\t", data);
            }
            System.out.println();
        }
    }

    /**
     * 将二维数组存放到磁盘文件中
     *
     * @param arr 数组
     * @param fileName 文件名
     */
    private static void writeArrToFile(int[][] arr, String fileName) throws IOException {
        BufferedWriter writer = new BufferedWriter(new FileWriter(fileName, false));
        for (int[] row : arr) {
            for (int data : row) {
                writer.write(data + "\t");
            }
            writer.newLine();
        }
        writer.flush();
        System.out.println("Data Entered in to the file successfully!");
        writer.close();
    }

    /**
     * 从磁盘中读取文件
     *
     * @param fileName 文件
     * @return
     */
    private static int[][] readArrFromFile(String fileName) throws Exception {
        File file = new File(fileName);
        if (!file.exists())
            throw new RuntimeException("Not File!");
        List<int[]> arr = new ArrayList<>();
        int max =0;
        BufferedReader reader = new BufferedReader(new FileReader(file));
        String contentLine ;
        while (null !=(contentLine= reader.readLine())) {
            String[] strings = contentLine.split("\t");
            int[] tempArr = new int[strings.length];
            max = Math.max(tempArr.length, max);
            for (int i = 0; i < strings.length; i++) {
                tempArr[i] = Integer.parseInt(strings[i]);
            }
            arr.add(tempArr);
        }
        reader.close();
        int[][] resultArr = new int[arr.size()][max];
        for (int i = 0; i < arr.size(); i++) {
            for (int j = 0; j < arr.get(i).length; j++) {
                resultArr[i][j] = arr.get(i)[j];
            }
        }
        System.out.println("Data read from the file successfully!");

        return resultArr;
    }
}

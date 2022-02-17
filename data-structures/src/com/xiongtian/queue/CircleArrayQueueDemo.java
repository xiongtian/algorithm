package com.xiongtian.queue;

import java.util.Scanner;

/**
 * Created by xiongtian on 2022/2/15 10:44
 *
 * @desctibe 数组实现环形队列
 * front的含义：指向队列的第一个元素，初始值为0
 * rear的含义：rear指向队列的最后一个元素的后一个位置，初始值为0
 * 1. 判断队列满的条件： (rear + 1) % maxSize = front
 * 2. 判断队列为空的条件：rear = front
 * 3. 计算对队列中的有效个数：(rear + maxSize -front) % maxSize
 */
public class CircleArrayQueueDemo {
    public static void main(String[] args) {

        // 测试
        //创建一个队列
        CircleArrayQueue queue = new CircleArrayQueue(4); // 说明设置4，其有效数据最大的3
        char key = ' '; //接收用户输入
        Scanner scanner = new Scanner(System.in);//
        boolean loop = true;
        //输出一个菜单
        while (loop) {
            System.out.println("s(show): 显示队列");
            System.out.println("e(exit): 退出程序");
            System.out.println("a(add): 添加数据到队列");
            System.out.println("g(get): 从队列取出数据");
            System.out.println("h(head): 查看队列头的数据");
            key = scanner.next().charAt(0);//接收一个字符
            switch (key) {
                case 's':
                    queue.showQueue();
                    break;
                case 'a':
                    System.out.println("输出一个数");
                    int value = scanner.nextInt();
                    queue.addQueue(value);
                    break;
                case 'g': //取出数据
                    try {
                        int res = queue.getQueue();
                        System.out.printf("取出的数据是%d\n", res);
                    } catch (Exception e) {
                        // TODO: handle exception
                        System.out.println(e.getMessage());
                    }
                    break;
                case 'h': //查看队列头的数据
                    try {
                        int res = queue.headQueue();
                        System.out.printf("队列头的数据是%d\n", res);
                    } catch (Exception e) {
                        // TODO: handle exception
                        System.out.println(e.getMessage());
                    }
                    break;
                case 'e': //退出
                    scanner.close();
                    loop = false;
                    break;
                default:
                    break;
            }
        }

        System.out.println("程序退出~~");

    }
}

class CircleArrayQueue {
    // 表示数组的最大容量
    private int maxSize;

    // 队列头
    private int front;

    // 队列尾
    private int rear;

    // 该数组用于存放数据
    private int[] arr;


    // 创建队列的构造器
    public CircleArrayQueue(int arrMaxSize) {
        maxSize = arrMaxSize;
        arr = new int[maxSize];
        front = 0; // 表示队列的第一个元素
        rear = 0;  // 指向队列最后一个元素的后一个位置
    }

    // 判断队列是否满
    public boolean isFull() {
        return (rear + 1) % maxSize == front;
    }

    // 判断队列是否为空
    public boolean isEmpty() {
        return rear == front;
    }

    // 添加数据到队列
    public void addQueue(int data) {
        // 判断队列是否满
        if (isFull()) {
            System.out.println("队列已满不能加入数据！");
            return;
        }

        // TODO 注意rear的位置是指向最后一个元素的后一个所以这里直接加1
        // 直接将数据加入
        arr[rear] = data;
        // 将rear后移，这里必须取模
        rear = (rear + 1) % maxSize;
    }

    // 获取队列的数据，出队列
    public int getQueue() {
        // 判断队列是否空
        if (isEmpty()) {
            // 通过抛出异常来处理
            throw new RuntimeException("队列空，不能取数据！");
        }
        // TODO 由于front记录的是第一个元素，所以需要在front增加之前将其临时变量记录下来。
        // front是指向队列的第一个元素
        // 1. 先将front对应的值保留到一个临时变量
        int result = arr[front];
        // 2. 将front后移
        front = (front + 1) % maxSize;
        // 3. 将临时保存的变量返回
        return result;
    }

    // 显示队列的所有数据
    public void showQueue() {
        // 遍历队列
        if (isEmpty()) {
            System.out.println("队列空的没有数据");
            return;
        }
        // 思路：从front开始遍历，遍历多少个元素
        for (int i = front; i < front + getSize(); i++) {
            System.out.printf("arr[%d]=%d\n", i % maxSize, arr[i % maxSize]);
        }
    }

    // 显示队列的头部，注意不是取出数据
    public int headQueue() {
        // 判断
        if (isEmpty()) {
            throw new RuntimeException("队列为空，没有数据");
        }
        return arr[front];
    }

    // 求出当前队列有效数据的个数
    public int getSize() {
        return (rear + maxSize - front) % maxSize;
    }

}


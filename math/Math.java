package math;

class Math {

  public static void main(String[] args) {

    // Use the // to create single line comments to either add info or to take out
    // code for debugging

    // We will be using System.out.println and System.out.print throughout the year.
    // Try using both below to see what the difference is!

    // ANSWER:

    // Throughout the year this year we will need to store information. For that we
    // will be using VARIABLES!

    // Java is an Object-Oriented programming language. All variables we use this
    // year will either be OBJECTS or PRIMITIVES

    // There are 8 primitives in Java: int, byte, short, long, float, double,
    // boolean, char

    // For AP we need to know: int, double, boolean

    // List examples of the types below and give definition
    // int (integer):
    // double:
    // boolean:

    // For now we are just going to work with primitive

    // Create 3 variables of each of the above types (USE GOOD CODING PRACTICE WHEN
    // CREATING THE VARIABLES

    // MATH TIME!

    // What are the math operators that we can use?
    // + - * / %

    // Try doing some math operations with numbers. How can we check to see if the
    // math worked?

    // Create codes that will print the following:

    // 1 - 100 array
    int[] nums = new int[100];
    for (int i = 0; i < 100; i++) {
      nums[i] = i + 1;
    }

    // Odd integers from 1 to 100, inclusive of both

    for (int num : nums) {
      if (num % 2 == 1) {
        System.out.print(num + " ");
      }
    }

    System.out.println();

    // All multiples of 3 from 1 to 100

    for (int num : nums) {
      if (num % 3 == 0) {
        System.out.print(num + " ");
      }
    }

    System.out.println();

    // Starting at 1000, print on the same line (with a - [hypthen] between each)

    for (int i = 1000; i > 0; i--) {
      if (i == 0) {
        System.out.print(i);
      } else if (String.valueOf(i).endsWith("0")) {
        System.out.print(i + "-");
      } // or just use modulo instead of this (% 10 == 0)
      // or just -= 10
    }

    // all of the numbers that end in 0 going down to 0
  }
}

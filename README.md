ScalaSql
--------
>ScalaSql feature test and performance benchmark against H2 using Scala 3.

Test
----
1. sbt clean test

Benchmark
---------
>See Performance class for details.
1. sbt jmh:run

Results
-------
>OpenJDK Runtime Environment Zulu24.28+85-CA (build 24+36), **Scala 3.7.1**, Apple M1
1. addTodo - 4.592
2. updateTodo - 5.199
3. listTodos - 8.260
>Total time: 606 s (10:06), 10 warmups, 10 iterations, average time in microseconds, completed **2025.6.13**
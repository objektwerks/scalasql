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
>OpenJDK Runtime Environment Zulu22.28+91-CA (build 22+36), **Scala 3.7.1**, Apple M1
1. addTodo - 0.0
2. updateTodo - 0.0
3. listTodos - 0.0
>Total time: 602 s (10:02), 10 warmups, 10 iterations, average time in microseconds, completed **2025.6.13**
package objektwerks

import com.typesafe.config.ConfigFactory

import java.util.UUID
import java.util.concurrent.TimeUnit

import org.openjdk.jmh.annotations.*

@State(Scope.Thread)
@BenchmarkMode(Array(Mode.AverageTime))
@OutputTimeUnit(TimeUnit.MICROSECONDS)
@Warmup(iterations = 10)
@Measurement(iterations = 10)
@Fork(1)
class TodoPerformance():
  val store = TodoStore( ConfigFactory.load("store.conf") )
  var todo = Todo(task = "")

  @Benchmark
  def addTodo(): Todo =
    todo = Todo(task = UUID.randomUUID.toString)
    todo = store.addTodo(todo)
    todo

  @Benchmark
  def updateTodo(): Count =
    todo = todo.copy(task = UUID.randomUUID.toString)
    store.updateTodo(todo)

  @Benchmark
  def listTodos(): Seq[Todo] = store.listTodos()
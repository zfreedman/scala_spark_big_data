// for loops
for(something <- List(1,2,3)){
  println("hello " * something)
}

println()
for(num <- Array.range(0,5)){
  println(num)
}

println()
// can uses sets as well, but sets don't guarantee order
for(i <- Set(1,2,3,4,5)){
  println(i)
}

val evens = Range(0, 10)
for (i <- evens) {
  if (i % 2 == 0) {
    println(s"$i is Even")
  } else {
    println(s"$i is oDD")
  }
}

println()
val names = List("john", "abe", "cindy", "cat")
for (name <- names) {
  if (name.startsWith("c")) {
    println(s"$name starts with a c")
  }
}

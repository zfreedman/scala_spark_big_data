// functions
def simple(): Unit = {
  println("simple print")
}
simple()

// adding function
println()
def adder(num1: Int, num2: Int): Int = {
  return num1 + num2
}
adder(1, 2)

// greeting
def greet_name(name: String): String = {
  return s"Hello $name"
}
println(greet_name("jose"))

// prime checker
println()
def prime_check(num: Int): Boolean = {
  // base case
  if (num < 2) return false
  // usual case
  for (i <- Range(2, num / 2 + 1)) {
    if (num % i == 0) return false
  }
  return true
}
for (i <- Range(0, 20)) {
  printf("%d is prime %b\n", i, prime_check(i))
}

// using collections with functions
val numbers = List(1,2,3,7)
def check(nums: List[Int]): List[Int] = {
  return nums
}
println(check(numbers))

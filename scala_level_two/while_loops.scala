//while loops and breaking
import util.control.Breaks._

var x = 0
while(x < 5){
  println(s"x is currently $x")
  println("x is still < 5, adding 1")
  x += 1
}

var y = 0
while(y < 10){
  println(s"y is currently $y")
  println("y is still < 10, adding 1")
  y += 1
  if (y == 5) break
}

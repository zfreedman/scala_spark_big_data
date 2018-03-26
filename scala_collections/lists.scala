// lists
val evens = List(2,4,6,8,10)
val listhere = List(1, 2.2, true)

// indexing (0-based, unlike 1-based tuples)
evens(0)
evens(4)

// first item
evens.head
// everything but first item
evens.tail

// nested lists
val my_list = List(List(1,2,3), List(4,5,6))
val my_list = List(("a",1), ("b",2), ("c",3))

// sorting
val my_list = List(3,6,1,7,10)
my_list.sorted
// size
my_list.size
// max, min
my_list.max
my_list.min
// sum
my_list.sum
// product
my_list.product

// slice (1,2,3,4) to (3,4)
val x = List(1,2,3,4)
x.drop(2)

// get n elements from the right of the list (8,9,10)
val n = 3
val x = List(1,2,3,4,5,6,7,8,9,10)
x.takeRight(n)

// regular slicing
x slice (1, 8)

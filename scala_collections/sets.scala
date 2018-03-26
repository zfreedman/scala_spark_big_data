// sets are collections that contain no duplicate elements
// there are both mutable and immutable variants

// sets
val s = Set()
val s = Set(1,2,3)
// below line will create an identical set to the above line
val s = Set(1,2,3,2,1)

// creating mutable sets
val s = collection.mutable.Set(1,2,3)
s += 4

// immutable sets
val s = Set(1,2,3)
// s += 4 -> this will cause an error

// sets do not maintain order
val mut_s = collection.mutable.Set(1,2,3)
mut_s += 4
// another way to add to a set is with the .add method
mut_s.add(5)
mut_s // -> (1,5,2,3,4)

// max/min
mut_s.max
mut_s.min

// converting a list to a set (casting)
val mylist = List(1,2,3,1,2,3)
val mynewset = mylist.toSet

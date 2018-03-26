val mylist = List(1,2,3,4,5)
// figure out a method to see if a list contains an item
mylist.contains(3)

// sum all elements from the list
mylist.sum

// create array of all odds in range (0, 15)
Array.range(1, 15, 2)

// get unique elements of a list
val mylist = List(2,3,1,4,5,6,6,1,2)
val myset = mylist.toSet.toList.sorted

// create a mutable map mapping names to ages
val mutmap = collection.mutable.Map(
  ("Sammy", 3),
  ("Frankie", 7),
  ("John", 45)
)
mutmap.keys
mutmap += ("Mike" -> 27)

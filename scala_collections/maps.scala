// maps are dictionaries/kvp storages
val mymap = Map(("a", 1), ("b", 2), ("c", 3))

// lookups
mymap("a")
mymap("d")// exception
mymap get "a"
mymap get "d"// returns None

// mutable map
val mymutmap = collection.mutable.Map(
  ("x", 1), ("y", 2), ("z", 3))
// adding to a map
mymutmap += ("newkey" -> 999)

// get keys and values
mymap.keys
mymap.values

// printing (use double quotes)
println("hello")

// concatenation
val farewell = "good" + "bye"
// string multiplication
"dance" * 5

// length
val st = "hello"
st.length

// s insertion/interpolation
val name = "jose"
val greet1 = s"hello ${name}"
val greet2 = s"hello $name"

// f insertion/interpolation
val greet3 = f"hello $name"

// printf
printf("a string %s, an integer %d, a float %f\n", "hi", 10, 12.345)
printf("a float %1.2f", 1.2395)

// indexing
val st = "This is a long string"
st.charAt(0)
st.indexOf("a")

// slicing
st slice (10, 14)

// regex matching
val st = "term1 term2 term3"
st matches "term1 term2 term3"
st matches "term1"

// contains
st contains "term1"
st contains "term4"

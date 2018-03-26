import org.apache.spark.sql.SparkSession
val spark = SparkSession.builder().getOrCreate()
var df = spark.read.option(
  "header", "true"
).option(
  "inferSchema", "true"
).csv("ContainsNull.csv")
df.printSchema()
df.show()

// dropping data with null values
df.na.drop().show()
// dropping any rows with less than 2 non-null values
// ... only 2 values or less are actually specified/recorded
df.na.drop(2).show()

// filling in null values
// the below fills in 100 for any columns where 100 is a valid input
// ... for example, string columns will remain null, but int/double will not
df.na.fill(100).show()
df.na.fill("missing name").show()

// filling specific columns
df.na.fill("New Name", Array("Name")).show()
df.na.fill(200, Array("Sales")).show()

// filling multiple columns
val df1 = df.na.fill("no name", Array("Name"))
df1.na.fill(200, Array("Sales")).show()

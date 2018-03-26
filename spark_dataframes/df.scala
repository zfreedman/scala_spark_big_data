// create a spark session
import org.apache.spark.sql.SparkSession
val spark = SparkSession.builder().getOrCreate()

// read in CSV data
val df = spark.read.csv("CitiGroup2006_2008")

// show first 5 rows
df.head(5)

// more legible output
for (row <- df.head(5)) {
  println(row)
}

// read in first row as header
// infer types
val df = spark.read.option(
  "header", "true"
).option(
  "inferSchema", "true"
).csv("CitiGroup2006_2008")
for (row <- df.head(5)) {
  println(row)
}

// get columns
df.columns

// describe column values
df.describe().show()

// selecting a specific column
df.select("Volume").show()
// selecting multiple columns
df.select($"Date", $"Close").show()

// creating columns (here the edited result is saved into a new dataframe)
val df2 = df.withColumn("High + Low", df("High") + df("Low"))
df2.printSchema()

// renaming a column (this doesn't work, it's more like creating an alias
// for a query
df2.select(df2("High + Low").as("HPL")).show()

import org.apache.spark.sql.SparkSession
val spark = SparkSession.builder().getOrCreate()
var df = spark.read.option(
  "header", "true"
).option(
  "inferSchema", "true"
).csv("CitiGroup2006_2008")
df.printSchema()
df.show(5)

// selecting the month
df.select(month(df("Date"))).show(5)
// year
df.select(year(df("Date"))).show(5)

// append year column and store result in new dataframe
val df2 = df.withColumn("Year", year(df("Date")))
// group by year and calculate mean per grouping
val dfavgs = df2.groupBy("Year").mean()
// Select specific columns
dfavgs.select($"Year", $"avg(Close)").show(5)

val dfmins = df2.groupBy("Year").min()
dfmins.select($"Year", $"min(Close)").show(5)

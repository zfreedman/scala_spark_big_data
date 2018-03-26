import org.apache.spark.sql.SparkSession
val spark = SparkSession.builder().getOrCreate()
var df = spark.read.option(
  "header", "true"
).option(
  "inferSchema", "true"
).csv("Sales.csv")
df.printSchema()
df.show()

// group by
df.groupBy("Company").mean().show()
df.groupBy("Company").count().show()
df.groupBy("Company").min().show()
df.groupBy("Company").max().show()
df.groupBy("Company").sum().show()

// aggregate functions aggregate data from multiple rows into a single result
df.select(sum("Sales")).show()

df.select(countDistinct("Sales")).show()
df.select(sumDistinct("Sales")).show()
df.select(variance("Sales")).show()
df.select(stddev("Sales")).show()
df.select(collect_set("Sales")).show()

// ordering results
df.orderBy("Sales").show()
df.orderBy($"Sales".desc).show()

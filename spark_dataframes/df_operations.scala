import org.apache.spark.sql.SparkSession
val spark = SparkSession.builder().getOrCreate()
var df = spark.read.option(
  "header", "true"
).option(
  "inferSchema", "true"
).csv("CitiGroup2006_2008")
df.printSchema()

// filtering data using scala syntax (can also use SQL w/o importing anything)
import spark.implicits._
df.filter($"Close" > 480).show(5)
// using spark sql notation
df.filter("Close > 480").show(5)

// multiple filters w/ scala notation
df.filter($"Close" < 480 && $"High" < 480).show(5)
// spark sql notation
df.filter("Close < 480 AND High < 480").show(5)

// more actions

// collecting results
val CH_low = df.filter("Close < 480 AND High < 480").collect()

// counting results
df.filter("Close < 480 AND High < 480").count()

// filtering for equality
df.filter($"High" === 484.40).show()
df.filter("High = 484.40").show()

// getting pearson correlation between 2 columns
df.select(corr("High", "Low")).show()

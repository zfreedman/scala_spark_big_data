import org.apache.spark.sql.SparkSession

import org.apache.log4j._
Logger.getLogger("org").setLevel(Level.ERROR)

// create session
val spark = SparkSession.builder().getOrCreate()

// read in data
val data = spark.read.option(
  "header", "true"
).option(
  "inferSchema", "true"
).format(
  "csv"
).load(
  "Clean-USA-Housing.csv"
)
data.printSchema()

// setup data for linear regression
import org.apache.spark.ml.feature.VectorAssembler
import org.apache.spark.ml.linalg.Vectors

val df = data.select(data("Price").as("label"),
  $"Avg Area Income",
  $"Avg Area House Age",
  $"Avg Area Number of Rooms",
  $"Avg Area Number of Bedrooms",
  $"Area Population"
)

// aggregate dataframe columns into a single array
// rename the aggregated column to features
val assembler = new VectorAssembler().setInputCols(
  Array(
    "Avg Area Income",
    "Avg Area House Age",
    "Avg Area Number of Rooms",
    "Avg Area Number of Bedrooms",
    "Area Population"
  )
).setOutputCol("features")

/* the assembler.transform(df) actually seems to just append the label
  feature columns onto the original dataframe, which is why we select
  only the label and feature columns and disregard the rest
  */
val output = assembler.transform(df).select($"label", $"features")

// create linear regression
import org.apache.spark.ml.regression.LinearRegression
val lr = new LinearRegression()

// fit data
// (here we're fitting the entire dataset, so there's no test/validation)
val lr_model = lr.fit(output)

val training_summary = lr_model.summary
training_summary.residuals.show()

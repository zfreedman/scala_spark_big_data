import org.apache.spark.ml.evaluation.RegressionEvaluator
import org.apache.spark.ml.regression.LinearRegression
import org.apache.spark.ml.tuning.{ParamGridBuilder, TrainValidationSplit}

// set logging level
import org.apache.log4j._
Logger.getLogger("org").setLevel(Level.ERROR)

// create sample_linear_regression_data
import org.apache.spark.sql.SparkSession
val spark = SparkSession.builder().getOrCreate()

// read data
val data = spark.read.option("header", "true").option(
  "inferSchema", "true").format("csv").load("Clean-USA-Housing.csv")

// show schema
data.printSchema()

// setup vector assembler to compose dataframe for learner
import org.apache.spark.ml.feature.VectorAssembler
import org.apache.spark.ml.linalg.Vectors

// note $" " notation is not necessary, but depending on OS, the absence of this
// notation can sometimes yield errors
val df = data.select(data("Price").as("label"), $"Avg Area Income",
  $"Avg Area House Age", $"Avg Area Number of Rooms",
  $"Avg Area Number of Bedrooms", $"Area Population")
val cols = df.columns
val assembler = new VectorAssembler().setInputCols(
  (cols.slice(0, cols.indexOf("label")).toList ::: cols.slice(cols.indexOf("label") + 1, cols.size).toList).toArray
).setOutputCol("features")

// data to use for learner
val output = assembler.transform(df).select($"label", $"features")

// splitting data
val train_split = 0.7
val random_seed = 12345
val Array(train, test) = output.select("label", "features").randomSplit(
  Array(train_split, 1 - train_split), seed=random_seed)

// model
val lr = new LinearRegression()
// param grid and train/val split
// using an empty param grid allows you to utilize validation sets without
// setting specific parameters
/*
val param_grid  = new ParamGridBuilder().build()
*/
val param_grid = (new ParamGridBuilder()
  .addGrid(lr.regParam, Array(10000000, 0.1))
  .build())
val val_split = 0.2
val train_val_split = (new TrainValidationSplit()
  .setEstimator(lr)
  .setEvaluator(new RegressionEvaluator().setMetricName("r2"))
  .setEstimatorParamMaps(param_grid)
  .setTrainRatio(1 - val_split))

// fit model
val model = train_val_split.fit(train)
// use against test data
model.transform(test).select("features", "label", "prediction").show(10)

// measuring error

import org.apache.spark.ml.regression.LinearRegression
import org.apache.spark.sql.SparkSession

// http://spark.apache.org/docs/latest/ml-classification-regression.html#linear-regression
def main(): Unit = {
  // create sesssion app
  val spark = SparkSession.builder().appName(
    "LinearRegressionExample").getOrCreate()

  // training data
  val training = spark.read.format("libsvm").load(
    "sample_linear_regression_data.txt")
  training.printSchema()

  // create linear regression object
  var lr = new LinearRegression()
    .setMaxIter(10)
    .setRegParam(0.3)
    .setElasticNetParam(0.8)

  // fit the model to the data
  var lr_model = lr.fit(training)

  // print model coefficients
  println(
    s"Coefficients: ${lr_model.coefficients}, Intercept: ${lr_model.intercept}")

  // summarize model for the training set
  val training_summary = lr_model.summary
  println(s"Iterations: ${training_summary.totalIterations}")
  println(s"Objective history: ${training_summary.objectiveHistory.toList}")
  training_summary.residuals.show()
  println(s"RMSE: ${training_summary.rootMeanSquaredError}")
  println(s"r2: ${training_summary.r2}")

  // stop stession
  spark.stop()
}
main()

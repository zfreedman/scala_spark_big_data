import org.apache.spark.ml.evaluation.RegressionEvaluator
import org.apache.spark.ml.regression.LinearRegression
import org.apache.spark.ml.tuning.{ParamGridBuilder, TrainValidationSplit}

// load data
val data = spark.read.format("libsvm").load("sample_linear_regression_data.txt")

// split data into train/test split
val train_split = 0.7
val random_seed = 12345
val Array(train, test) = data.randomSplit(
  Array(train_split, 1 - train_split), seed=12345)

// show schema
data.printSchema()

// setup linear regression
val lr = new LinearRegression()

// create parameter grid
// note: .fitIntercept is a boolean, which is why it doesn't use an array
val param_grid = new ParamGridBuilder().addGrid(
  lr.regParam, Array(0.1, 0.01)).addGrid(lr.fitIntercept).addGrid(
    lr.elasticNetParam, Array(0.0, 0.5, 1.0)).build()

// split data into train/validation split
val validation_split = 0.2
val train_validation_split = new TrainValidationSplit().setEstimator(
  lr).setEvaluator(new RegressionEvaluator()).setEstimatorParamMaps(
    param_grid).setTrainRatio(1 - validation_split)

// running the train validation split to choose the best parameters
// ... i think this is training each model variant using one parameter
// set from the parameter grid and yielding the best one with respect to
// metric performance
val model = train_validation_split.fit(train)

// run learner against test set
model.transform(test).select("features", "label", "prediction").show(10)

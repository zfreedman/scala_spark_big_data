import org.apache.spark.ml.classification.LogisticRegression
import org.apache.spark.sql.SparkSession

// set minimal debugging
import org.apache.log4j._
Logger.getLogger("org").setLevel(Level.ERROR)

// start sess
val spark = SparkSession.builder().getOrCreate()

// create dataframe
val data = spark.read.option("header", "true").option(
  "inferSchema", "true").format("csv").load("titanic.csv")

// print schema and display data
data.printSchema()
data.show(3)

val logregdataall = data.select(data("Survived").as("label"),
  $"Pclass", $"Name", $"Sex", $"Age", $"SibSp", $"Parch", $"Fare", $"Embarked")

// drop missing values
val logregdata = logregdataall.na.drop()

// import multiple
import org.apache.spark.ml.feature.{
  VectorAssembler, StringIndexer, VectorIndexer, OneHotEncoder}
import org.apache.spark.ml.linalg.Vectors

// index string columns to numerical values
val gender_indexer = new StringIndexer().setInputCol(
  "Sex").setOutputCol("Sex_Index")
val embark_indexer = new StringIndexer().setInputCol(
  "Embarked").setOutputCol("Embark_Index")

// one hot encode numerically encoded categorical values
val gender_encoder = new OneHotEncoder().setInputCol(
  "Sex_Index").setOutputCol("Sex_Vec")
val embark_encoder = new OneHotEncoder().setInputCol(
  "Embark_Index").setOutputCol("Embark_Vec")

// put data into (label, features) form
val assembler = new VectorAssembler().setInputCols(
  Array(
    "Pclass", "Sex_Vec", "Age", "SibSp", "Parch", "Fare", "Embark_Vec"
  )).setOutputCol("features")

// split data into training and testing set
val train_ratio = 0.7
val random_seed = 12345
val Array(training, test) = logregdata.randomSplit(Array(
  train_ratio, 1 - train_ratio), seed=random_seed)

// setup pipeline and logistic regression
import org.apache.spark.ml.Pipeline
val lr = new LogisticRegression()
val pipeline = new Pipeline().setStages(Array(
  gender_indexer,
  embark_indexer,
  gender_encoder,
  embark_encoder,
  assembler,
  lr
))

// fit model
val model = pipeline.fit(training)

// get results
val results = model.transform(test)

// model evaluation (still mostly available for RDDs and NOT dataframes)
import org.apache.spark.mllib.evaluation.MulticlassMetrics

// convert prediction results to an RDD
val predictionsAndLabels = results.select(
  $"prediction", $"label").as[(Double, Double)].rdd

val metrics = new MulticlassMetrics(predictionsAndLabels)
println("Confusion matrix:")
println(metrics.confusionMatrix)
// note: because metrics is currently only available for RDDs (not dataframes),
// some of the behavior is broken. for example, metrics.accuracy,
// metrics.precision, and metrics.recall all return the same values. because
// of this, it's better to calculate precision and recall from the confusion
// matrix

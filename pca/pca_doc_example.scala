import org.apache.spark.sql.SparkSession
import org.apache.spark.ml.feature.PCA
import org.apache.spark.ml.linalg.Vectors

// setup spark spark session
val spark = SparkSession.builder().appName("PCA_example").getOrCreate()

// create data
val data = Array(
  Vectors.sparse(5, Seq((1, 1.0), (3, 7.0))),
  Vectors.dense(2.0, 0.0, 3.0, 4.0, 5.0),
  Vectors.dense(4.0, 0.0, 0.0, 6.0, 7.0)
)

// convert data to features
val df = spark.createDataFrame(data.map(Tuple1.apply)).toDF("features")
// perform principal component analysis
val component_count = 3
val pca = (new PCA()
  .setInputCol("features")
  .setOutputCol("pcaFeatures")
  .setK(component_count)
  .fit(df)
)

// transform data and observe results
val pca_df = pca.transform(df)
val result = pca_df.select("pcaFeatures")
result.show()

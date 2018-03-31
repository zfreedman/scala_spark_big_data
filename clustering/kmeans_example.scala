// set log level
import org.apache.log4j._
Logger.getLogger("org").setLevel(Level.ERROR)

// start spark session
import org.apache.spark.sql.SparkSession
val spark = SparkSession.builder().getOrCreate()

// grab data
val dataset = spark.read.format("libsvm").load("sample_kmeans_data.txt")

// setup clustering algorithm
val clusters = 2
val seed = 1L
import org.apache.spark.ml.clustering.KMeans
val kmeans = new KMeans().setK(clusters).setSeed(seed)
val model = kmeans.fit(dataset)

// evaluate model (witin set sum of squared errors)
val wssse = model.computeCost(dataset)
println(s"Within Set Sum of Squared Errors = $wssse")

// show result
println("cluster centers: ")
model.clusterCenters.foreach(println)

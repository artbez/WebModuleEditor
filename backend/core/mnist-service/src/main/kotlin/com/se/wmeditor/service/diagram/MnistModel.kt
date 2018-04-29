package com.se.wmeditor.service.diagram

import org.datavec.image.loader.NativeImageLoader
import org.deeplearning4j.datasets.iterator.impl.MnistDataSetIterator
import org.deeplearning4j.nn.conf.NeuralNetConfiguration
import org.deeplearning4j.nn.conf.inputs.InputType
import org.deeplearning4j.nn.conf.layers.*
import org.deeplearning4j.nn.multilayer.MultiLayerNetwork
import org.deeplearning4j.nn.weights.WeightInit
import org.deeplearning4j.optimize.listeners.ScoreIterationListener
import org.nd4j.linalg.activations.Activation
import org.nd4j.linalg.dataset.api.iterator.DataSetIterator
import org.nd4j.linalg.learning.config.Nesterovs
import org.nd4j.linalg.lossfunctions.LossFunctions
import org.slf4j.LoggerFactory
import java.awt.Image
import java.awt.image.BufferedImage


class MnistModel {

    private val nChannels = 1 // Number of input channels
    private val outputNum = 10 // The number of possible outcomes
    private val batchSize = 64 // Test batch size
    private val nEpochs = 1 // Number of training epochs
    private val seed = 123 //
    private val model: MultiLayerNetwork

    init {
        logger.info("Load data....")
        val mnistTrain = MnistDataSetIterator(batchSize, true, 12345)
        val mnistTest = MnistDataSetIterator(batchSize, false, 12345)

        logger.info("Build model....")
        model = createModel()

        train(mnistTrain, mnistTest)
    }

    fun predict(image: BufferedImage) = predictImage(image.resize(28, 28), model)

    private fun train(mnistTrain: DataSetIterator, mnistTest: DataSetIterator) {
        logger.info("Train model....")
        model.setListeners(ScoreIterationListener(10)) //Print score every 10 iterations
        for (i in 0 until nEpochs) {
            model.fit(mnistTrain)
            logger.info("*** Completed epoch {} ***", i)

            logger.info("Evaluate model....")
            val eval = model.evaluate(mnistTest)

            logger.info(eval.stats())
            mnistTest.reset()
        }
        logger.info("****************Example finished********************")
    }

    private fun createModel(): MultiLayerNetwork {

        val conf = NeuralNetConfiguration.Builder()
            .seed(seed.toLong())
            .l2(0.0005)
            .weightInit(WeightInit.XAVIER)
            .updater(Nesterovs(0.01, 0.9))
            .list()
            .layer(
                0, ConvolutionLayer.Builder(5, 5)
                    .nIn(nChannels)
                    .stride(1, 1)
                    .nOut(20)
                    .activation(Activation.IDENTITY)
                    .build()
            )
            .layer(
                1, SubsamplingLayer.Builder(PoolingType.MAX)
                    .kernelSize(2, 2)
                    .stride(2, 2)
                    .build()
            )
            .layer(
                2, ConvolutionLayer.Builder(5, 5)
                    //Note that nIn need not be specified in later layers
                    .stride(1, 1)
                    .nOut(50)
                    .activation(Activation.IDENTITY)
                    .build()
            )
            .layer(
                3, SubsamplingLayer.Builder(PoolingType.MAX)
                    .kernelSize(2, 2)
                    .stride(2, 2)
                    .build()
            )
            .layer(
                4, DenseLayer.Builder().activation(Activation.RELU)
                    .nOut(500).build()
            )
            .layer(
                5, OutputLayer.Builder(LossFunctions.LossFunction.NEGATIVELOGLIKELIHOOD)
                    .nOut(outputNum)
                    .activation(Activation.SOFTMAX)
                    .build()
            )
            .setInputType(InputType.convolutionalFlat(28, 28, 1))
            .backprop(true).pretrain(false).build()


        val model = MultiLayerNetwork(conf)
        model.init()

        return model
    }
}

fun BufferedImage.resize(newW: Int, newH: Int): BufferedImage {
    val tmp = getScaledInstance(newW, newH, Image.SCALE_SMOOTH)

    val bufferedImage = BufferedImage(newW, newH, BufferedImage.TYPE_INT_ARGB)

    val g2d = bufferedImage.createGraphics()
    g2d.drawImage(tmp, 0, 0, null)
    g2d.dispose()

    return bufferedImage
}

private fun predictImage(img: BufferedImage, net: MultiLayerNetwork): Int {
    val loader = NativeImageLoader(28, 28, 1, true)
    val image = loader.asRowVector(img)
    return net.predict(image)[0]
}

private val logger = LoggerFactory.getLogger(MnistModel::class.java)
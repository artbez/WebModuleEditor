package com.se.wmeditor.mnist

import com.se.wmeditor.service.diagram.DiagramService
import com.se.wmeditor.service.diagram.EchoHandler
import com.se.wmeditor.service.diagram.MnistModel
import org.junit.Test
import org.junit.runner.RunWith
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.context.annotation.PropertySource
import org.springframework.core.io.ClassPathResource
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner
import org.springframework.test.web.reactive.server.WebTestClient
import java.io.ByteArrayOutputStream
import javax.imageio.ImageIO


@RunWith(SpringJUnit4ClassRunner::class)
@SpringBootTest(classes = [DiagramService::class])
@PropertySource("application.yml")
class MnistRestTest {

    @Autowired
    private lateinit var mnistModel: MnistModel

    private var client = lazy { WebTestClient.bindToController(EchoHandler(mnistModel)).build() }

    @Test
    fun testSimplePredict() {

        // Retrieve image from the classpath.
        val imageBytes = ClassPathResource("9.png").file

        // Prepare buffered image.
        val img = ImageIO.read(imageBytes)

        val baos = ByteArrayOutputStream()
        ImageIO.write(img, "png", baos)
        baos.flush()

        val imageInByte = baos.toByteArray()
        baos.close()

        println(
            client.value.post()
                .uri("api/mnist")
                .syncBody(imageInByte)
                .exchange()
                .expectBody(Int::class.java)
                .returnResult().responseBody
        )

    }
}
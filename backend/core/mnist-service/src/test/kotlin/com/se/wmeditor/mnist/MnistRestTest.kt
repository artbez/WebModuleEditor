package com.se.wmeditor.mnist

import com.se.wmeditor.service.mnist.DiagramService
import org.hamcrest.MatcherAssert.assertThat
import org.hamcrest.core.Is
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.beans.factory.annotation.Qualifier
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.core.io.ClassPathResource
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner
import org.springframework.test.context.web.WebAppConfiguration
import org.springframework.test.web.reactive.server.WebTestClient
import org.springframework.web.reactive.function.server.RouterFunction
import org.springframework.web.reactive.function.server.ServerResponse
import java.io.ByteArrayOutputStream
import javax.imageio.ImageIO


@RunWith(SpringJUnit4ClassRunner::class)
@SpringBootTest(classes = [DiagramService::class])
@WebAppConfiguration
class MnistRestTest {

    @Autowired
    @Qualifier("monoRouterFunction")
    private lateinit var monoRouterFunction: RouterFunction<ServerResponse>

    private lateinit var client: WebTestClient

    @Before
    fun setup() {
        client = WebTestClient
            .bindToController(monoRouterFunction)
            .build()
    }

    @Test
    fun testSimplePredict() {

        // Retrieve image from the classpath.
        val imageBytes = ClassPathResource("img_15.jpg").file

        // Prepare buffered image.
        val img = ImageIO.read(imageBytes)

        val baos = ByteArrayOutputStream()

        ImageIO.write(img, "jpg", baos)
        baos.flush()

        val imageInByte = baos.toByteArray()
        baos.close()

        val prediction = client.post()
            .uri("/api/mnist")
            .syncBody(imageInByte)
            .exchange()
            .expectBody(Int::class.java)
            .returnResult().responseBody

        assertThat("Mnist prediction must be 4", prediction, Is.`is`(4))
    }
}
package example;

import java.io.FileInputStream;

import org.apache.camel.EndpointInject;
import org.apache.camel.Produce;
import org.apache.camel.ProducerTemplate;
import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.component.mock.MockEndpoint;
import org.apache.camel.test.spring.CamelSpringTestSupport;
import org.junit.Test;
import org.springframework.context.support.AbstractXmlApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class TransformationTest extends CamelSpringTestSupport {
    
    @EndpointInject(uri = "mock:person2citizen-test-output")
    private MockEndpoint resultEndpoint;
    
    @Produce(uri = "direct:person2citizen-test-input")
    private ProducerTemplate startEndpoint;
    
    @Test
    public void transform() throws Exception {
        startEndpoint.sendBody(readFile("src/data/message1.xml"));

    }
    
    @Override
    protected RouteBuilder createRouteBuilder() throws Exception {
        return new RouteBuilder() {
            public void configure() throws Exception {
                from("direct:person2citizen-test-input")
                    .log("Before transformation:\n ${body}")
                    .to("ref:person2citizen")
                    .log("After transformation:\n ${body}")
                    .to("mock:person2citizen-test-output");
            }
        };
    }
    
    @Override
    protected AbstractXmlApplicationContext createApplicationContext() {
        return new ClassPathXmlApplicationContext("META-INF/spring/camel-context.xml");
    }
    
    private String readFile(String filePath) throws Exception {
        String content;
        FileInputStream fis = new FileInputStream(filePath);
        try {
             content = createCamelContext().getTypeConverter().convertTo(String.class, fis);
        } finally {
            fis.close();
        }
        return content;
    }
}

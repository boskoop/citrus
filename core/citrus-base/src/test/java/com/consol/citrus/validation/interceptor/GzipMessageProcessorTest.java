package com.consol.citrus.validation.interceptor;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.zip.GZIPInputStream;
import java.util.zip.GZIPOutputStream;

import com.consol.citrus.exceptions.CitrusRuntimeException;
import com.consol.citrus.message.DefaultMessage;
import com.consol.citrus.message.Message;
import com.consol.citrus.message.MessageType;
import com.consol.citrus.testng.AbstractTestNGUnitTest;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.FileSystemResource;
import org.springframework.core.io.Resource;
import org.springframework.util.FileCopyUtils;
import org.springframework.util.StreamUtils;
import org.testng.Assert;
import org.testng.annotations.Test;

import static org.testng.Assert.assertEquals;

/**
 * @author Christoph Deppisch
 */
public class GzipMessageProcessorTest extends AbstractTestNGUnitTest {

    private GzipMessageProcessor processor = new GzipMessageProcessor();

    @Test
    public void testGzipMessageStaysUntouched() throws IOException {
        try (ByteArrayOutputStream zipped = new ByteArrayOutputStream()) {
            try (GZIPOutputStream gzipOutputStream = new GZIPOutputStream(zipped)) {
                StreamUtils.copy("foo".getBytes(StandardCharsets.UTF_8), gzipOutputStream);

                //GIVEN
                final DefaultMessage message = new DefaultMessage(gzipOutputStream);
                message.setType(MessageType.GZIP);

                //WHEN
                final Message interceptedMessage =
                        processor.process(message, context);

                //THEN
                assertEquals(interceptedMessage.getPayload(), gzipOutputStream);
            }
        }
    }

    @Test
    public void testTextMessageIsIntercepted() throws IOException {

        //GIVEN
        final DefaultMessage message = new DefaultMessage("foo");
        message.setType(MessageType.PLAINTEXT);

        //WHEN
        final Message interceptedMessage =
                processor.process(message, context);

        //THEN
        assertEquals(interceptedMessage.getType(), MessageType.GZIP.name());
        ByteArrayOutputStream unzipped = new ByteArrayOutputStream();
        GZIPInputStream gzipInputStream = new GZIPInputStream(
                new ByteArrayInputStream(interceptedMessage.getPayload(byte[].class)));
        StreamUtils.copy(gzipInputStream, unzipped);
        Assert.assertEquals(unzipped.toByteArray(), "foo".getBytes(StandardCharsets.UTF_8));
    }

    @Test
    public void testBinaryMessageIsIntercepted() throws IOException {

        //GIVEN
        final DefaultMessage message = new DefaultMessage("foo".getBytes(StandardCharsets.UTF_8));
        message.setType(MessageType.BINARY);

        //WHEN
        final Message interceptedMessage =
                processor.process(message, context);

        //THEN
        assertEquals(interceptedMessage.getType(), MessageType.GZIP.name());
        ByteArrayOutputStream unzipped = new ByteArrayOutputStream();
        GZIPInputStream gzipInputStream = new GZIPInputStream(
                new ByteArrayInputStream(interceptedMessage.getPayload(byte[].class)));
        StreamUtils.copy(gzipInputStream, unzipped);
        Assert.assertEquals(unzipped.toByteArray(), "foo".getBytes(StandardCharsets.UTF_8));
    }

    @Test
    public void testInputStreamMessageIsIntercepted() throws IOException {

        //GIVEN
        final DefaultMessage message = new DefaultMessage(new ByteArrayInputStream("foo".getBytes()));
        message.setType(MessageType.PLAINTEXT);

        //WHEN
        final Message interceptedMessage =
                processor.process(message, context);

        //THEN
        assertEquals(interceptedMessage.getType(), MessageType.GZIP.name());
        ByteArrayOutputStream unzipped = new ByteArrayOutputStream();
        GZIPInputStream gzipInputStream = new GZIPInputStream(
                new ByteArrayInputStream(interceptedMessage.getPayload(byte[].class)));
        StreamUtils.copy(gzipInputStream, unzipped);
        Assert.assertEquals(unzipped.toByteArray(), "foo".getBytes(StandardCharsets.UTF_8));
    }

    @Test
    public void testResourceMessageIsIntercepted() throws IOException {

        //GIVEN
        final DefaultMessage message = new DefaultMessage(getTestFile());
        message.setType(MessageType.PLAINTEXT);

        //WHEN
        final Message interceptedMessage =
                processor.process(message, context);

        //THEN
        assertEquals(interceptedMessage.getType(), MessageType.GZIP.name());
        ByteArrayOutputStream unzipped = new ByteArrayOutputStream();
        GZIPInputStream gzipInputStream = new GZIPInputStream(
                new ByteArrayInputStream(interceptedMessage.getPayload(byte[].class)));
        StreamUtils.copy(gzipInputStream, unzipped);
        Assert.assertEquals(unzipped.toByteArray(),  FileCopyUtils.copyToByteArray(getTestFile().getInputStream()));
    }

    @Test(expectedExceptions = CitrusRuntimeException.class)
    public void testProcessMessageResourceNotFound() {

        //GIVEN
        final DefaultMessage message = new DefaultMessage(new FileSystemResource("unknown.txt"));
        message.setType(MessageType.PLAINTEXT);

        //WHEN
        processor.process(message, context);

        //THEN should throw exception
    }

    private Resource getTestFile() {
        return new ClassPathResource("foo.txt", GzipMessageProcessor.class);
    }
}

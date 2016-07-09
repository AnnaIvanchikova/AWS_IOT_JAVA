package example;

import com.amazonaws.services.iot.client.AWSIotMessage;
import com.amazonaws.services.iot.client.AWSIotQos;
import com.amazonaws.services.iot.client.AWSIotTopic;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.awt.*;

public class MyTopic extends AWSIotTopic {
    private TextArea textArea;
    private static final Logger LOGGER = LoggerFactory.getLogger(Callback.class);

    public MyTopic(String topic, AWSIotQos qos, TextArea area) {
        super(topic, qos);
        this.textArea = area;
    }

    @Override
    public void onMessage(AWSIotMessage message) {
        LOGGER.info("Message arrived on topic {}. Contents: {}", topic, new String(message.getPayload()));
        textArea.append(">> Message from device: " +  new String(message.getPayload()) + "\n");
    }
}

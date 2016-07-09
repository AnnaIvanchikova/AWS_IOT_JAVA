package example;

import org.eclipse.paho.client.mqttv3.IMqttDeliveryToken;
import org.eclipse.paho.client.mqttv3.MqttCallback;
import org.eclipse.paho.client.mqttv3.MqttMessage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.awt.*;

/**
 *
 * Created by Ozkan Can on 10/04/16.
 */
class Callback implements MqttCallback {
    TextArea textArea;

    Callback(TextArea textArea) {
        super();
        this.textArea = textArea;
    }

    private static final Logger LOGGER = LoggerFactory.getLogger(Callback.class);

    @Override
    public void connectionLost(Throwable cause) {
        LOGGER.info("Connection Lost.", cause);
    }

    @Override
    public void messageArrived(String topic, MqttMessage message) throws Exception {
        LOGGER.info("Message arrived on topic {}. Contents: {}", topic, new String(message.getPayload()));
        textArea.append(">> Message from device: " +  new String(message.getPayload()) + "\n");
    }

    @Override
    public void deliveryComplete(IMqttDeliveryToken token) {
        LOGGER.info("Completed delivery of message with id {}", token.getMessageId());
    }
}

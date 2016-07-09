package example;

import util.IoTConfig;
import util.SslUtil;
import org.eclipse.paho.client.mqttv3.*;
import org.eclipse.paho.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.net.ssl.SSLSocketFactory;

import static util.IoTConfig.ConfigFields.*;

/**
 * Simple MQTT Client Example for Publish/Subscribe on AWS IoT.
 * This example should serve as a starting point for using AWS IoT with Java.
 *
 * <ul>
 *  <li>The client connects to the endpoint specified in the config file.</li>
 *  <li>Subscribes to the topic "MyTopic".</li>
 *  <li>Publishes  a "Hello World" message to the topic "MyTopic.</li>
 *  <li>Closes the connection.</li>
 *  <li>This example should serve as a starting point for using AWS IoT with Java.</li>
 * </ul>
 * Created by Ozkan Can on 04/09/2016.
 */
public class Main {

    private static final Logger LOGGER = LoggerFactory.getLogger(Main.class);

    private static final int QOS_LEVEL = 0;
    private static final String RECEIVE_TOPIC = "TopicFromDevice";
    private static final String PUBLISH_TOPIC = "MyTopic";
    private static final String MESSAGE = "word fromJava";
    private static final long QUIESCE_TIMEOUT = 5000;

    public static void main(String[] args) {

        RobotControlForm form = new RobotControlForm(args[0]);
        form.setVisible(true);
        form.pack();

        if(args.length < 1)
        {
            showHelp();
        }
//
//        try {
//            IoTConfig config = new IoTConfig(args[0]);
//            SSLSocketFactory sslSocketFactory = SslUtil.getSocketFactory(
//                    config.get(AWS_IOT_ROOT_CA_FILENAME),
//                    config.get(AWS_IOT_CERTIFICATE_FILENAME),
//                    config.get(AWS_IOT_PRIVATE_KEY_FILENAME));
//            MqttConnectOptions options = new MqttConnectOptions();
//            options.setSocketFactory(sslSocketFactory);
//            options.setCleanSession(true);
//
//            final String serverUrl = "ssl://"+config.get(AWS_IOT_MQTT_HOST)+":"+config.get(AWS_IOT_MQTT_PORT);
//            final String clientId = config.get(AWS_IOT_MQTT_CLIENT_ID);
//
//            MqttClient client = new MqttClient(serverUrl, clientId);
//            client.setCallback(new Callback());
//            client.connect(options);
//            client.subscribe(TOPIC, QOS_LEVEL);
//            client.publish(TOPIC, new MqttMessage(MESSAGE.getBytes()));
//
//
//
//            // Remove the disconnect and close, if you want to continue listening/subscribing
//            //client.disconnect(QUIESCE_TIMEOUT);
//            //client.close();
//        }
//        catch (Exception e) {
//            LOGGER.error(e.getMessage(), e);
//            System.exit(-1);
//        }
    }

    private static void showHelp()
    {
        System.out.println("Usage: java -jar aws-iot-java-example.jar <config-file>");
        System.out.println("\nSee config-example.properties for an example of a config file.");
        System.exit(0);
    }
}

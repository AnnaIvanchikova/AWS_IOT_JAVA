package example;

import com.amazonaws.services.iot.client.AWSIotMqttClient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import util.IoTConfig;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import util.SslUtil;
import org.eclipse.paho.client.mqttv3.*;

import javax.net.ssl.SSLSocketFactory;

import static util.IoTConfig.ConfigFields.*;

/**
 * Created by user
 */
public class RobotControlForm extends JFrame {
    private static final Logger LOGGER = LoggerFactory.getLogger(Main.class);

    private static final int QOS_LEVEL = 0;
    private static final String RECEIVE_TOPIC = "TopicFromDevice";
    private static final String PUBLISH_TOPIC = "MyTopic";

    private JButton turnLeftButton;
    private JButton goButton;
    private JButton turnRightButton;
    private JButton stopButton;
    private TextArea textLog;
    JPanel buttonsPanel;
    JPanel textPanel;

    MqttClient client;
    AWSIotMqttClient iotClient;

    public RobotControlForm(String arg) {
        super("Robot Controller");
        initComponents();

        try {
            IoTConfig config = new IoTConfig(arg);

           // KeyStorePasswordPair pair = SampleUtil.getKeyStorePasswordPair(certificateFile, privateKeyFile);
            //iotClient = new AWSIotMqttClient(clientEndpoint, clientId, pair.keyStore, pair.keyPassword);

            SSLSocketFactory sslSocketFactory = SslUtil.getSocketFactory(
                    config.get(AWS_IOT_ROOT_CA_FILENAME),
                    config.get(AWS_IOT_CERTIFICATE_FILENAME),
                    config.get(AWS_IOT_PRIVATE_KEY_FILENAME));
            MqttConnectOptions options = new MqttConnectOptions();
            options.setSocketFactory(sslSocketFactory);
            options.setCleanSession(true);

            final String serverUrl = "ssl://" + config.get(AWS_IOT_MQTT_HOST) + ":" + config.get(AWS_IOT_MQTT_PORT);
            final String clientId = config.get(AWS_IOT_MQTT_CLIENT_ID);

            client = new MqttClient(serverUrl, clientId);
            client.setCallback(new Callback(textLog));
            client.connect(options);
            client.subscribe(RECEIVE_TOPIC, QOS_LEVEL);


            //client.disconnect(QUIESCE_TIMEOUT);
            //client.close();
        } catch (Exception e) {
            LOGGER.error(e.getMessage(), e);
            System.exit(-1);
        }

        addListeners();

    }

    private void initComponents() {
        buttonsPanel = new JPanel(new BorderLayout());
        textPanel = new JPanel(new BorderLayout());

        turnLeftButton = new JButton("Turn Left");
        goButton = new JButton("Go");
        turnRightButton = new JButton("Turn Right");
        stopButton = new JButton("Stop");

        textLog = new TextArea();
        textLog.setPreferredSize(new Dimension(400, 500));

        buttonsPanel.add(turnLeftButton, BorderLayout.WEST);
        buttonsPanel.add(goButton, BorderLayout.CENTER);
        buttonsPanel.add(turnRightButton, BorderLayout.EAST);
        buttonsPanel.add(stopButton, BorderLayout.SOUTH);

        textPanel.add(textLog, BorderLayout.CENTER);

        add(buttonsPanel, BorderLayout.CENTER);
        add(textPanel, BorderLayout.WEST);

        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }
    private void addListeners() {
        goButton.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                try {
                    LOGGER.info("go");
                    textLog.append("> go\n");
                    client.publish(PUBLISH_TOPIC, new MqttMessage("Go".getBytes()));
                } catch (Exception ex) {
                    LOGGER.error(ex.getMessage(), ex);
                    System.exit(-1);
                }
            }
        });

        turnLeftButton.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                try {
                    LOGGER.info("left");
                    textLog.append("> left\n");
                    client.publish(PUBLISH_TOPIC, new MqttMessage("Turn Left".getBytes()));
                } catch (Exception ex) {
                    LOGGER.error(ex.getMessage(), ex);
                    System.exit(-1);
                }
            }
        });

        turnRightButton.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                try {
                    LOGGER.info("right");
                    textLog.append("> right\n");
                    client.publish(PUBLISH_TOPIC, new MqttMessage("Turn Right".getBytes()));
                } catch (Exception ex) {
                    LOGGER.error(ex.getMessage(), ex);
                    System.exit(-1);
                }
            }
        });

        stopButton.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                try {
                    LOGGER.info("stop");
                    textLog.append("> stop\n");
                    client.publish(PUBLISH_TOPIC, new MqttMessage("Stop".getBytes()));
                } catch (Exception ex) {
                    LOGGER.error(ex.getMessage(), ex);
                    System.exit(-1);
                }
            }
        });
    }
}

/*************************************************************
* Jenkins stop light for IOT Demo
* 
* A basic stop light (red/yellow/green) that is controlled by * a remote Jenkins installation via a REST API in order to 
* reflect a built job status.
* 
* The board runs a basic HTTP Server, that allows to control a 3 color 24V StackLight.
* 
* The stacklight is connected via a relay module and it is controlled via simple digital outputs.
* 
* The board also has a infrared obstacle detection sensor (with digital output) that is used as a reset button.
* 
* Ethernet uses DHCP. At startup it will output the assigned IP into the serial monitor.
* 
* REST API:
* | Request | URI                    | Reply                       | Result  
* | GET     | HTTP://board_IP/stop/  | HTTP 200 OK                 | Turn stoplight OFF 
* | GET     | HTTP://board_IP/start/ | HTTP 200 OK                 | Turn only YELLOW color on 
* | GET     | HTTP://board_IP/ok/    | HTTP 200 OK                 | Turn only GREEN color on 
* | GET     | HTTP://board_IP/nok/   | HTTP 200 OK                 | Turn only RED color on 
* | GET     | HTTP://board_IP/xmas/  | HTTP 200 OK                 | Turn all 3 colors on in a pattern
* | GET     | HTTP://board_IP/all/   | HTTP 200 OK                 | Turn all 3 colors on 
* | GET     | HTTP://board_IP/warn/  | HTTP 200 OK                 | Alert yellow color for deploy
* | ANY     | any                    | HTTP 405 Method Not Allowed | Any other call is not allowed, board won't do anything
* 
*************************************************************/
#include <SPI.h>
#include <Ethernet.h>


// The max request length
#define MAX_REQ_LEN 45

// Pins used for the stoplight
#define  GREEN_LED 5
#define  YELLOW_LED 6
#define  RED_LED 7

// Pins used reset sensor and led
#define  RESET_SENSOR 8
#define  SENSOR_LED 9

//////////////////////////////////////////////////////////////
// Global Variables 
/////////////////////////////////////////////////////////////

// Enter a MAC address and IP address for your controller below.
// The IP address will be dependent on your local network:
byte mac[] = {
  0xDE, 0xAD, 0xBE, 0xEF, 0xFE, 0xED
};
IPAddress ip(172, 17, 41, 168);

// Initialize the Ethernet server library
// with the IP address and port you want to use
// (port 80 is default for HTTP):
EthernetServer server(80);


// Buffer to store incoming message
char request[MAX_REQ_LEN];

int sensorState = 0;         // variable for reading the sensor status

///////////////////////////////////////////////////////////////
// Method definitions
///////////////////////////////////////////////////////////////
/////////////////////////////////////////////////////////////
// Setup
/////////////////////////////////////////////////////////////
void setup() {
  // Open serial communications and wait for port to open:
  Serial.begin(9600);
  while (!Serial) {
    ; // wait for serial port to connect. Needed for native USB port only
  }
  Serial.println("Jenkins Stop Light IOT Demo");

  // Init stoplight
  initStopLight();

  // Init reset sensor
  initResetSensor();

  // Init Ethernet and WebServer
  initWebServer();
}


/////////////////////////////////////////////////////////////
// Main loop
/////////////////////////////////////////////////////////////
void loop() {
  // Handle the ethernet connections
  listenForConnection();

  // Handle the Reset Sensor
  handleResetSensor();
}

/////////////////////////////////////////////////////////////
// Initialize the stop light
/////////////////////////////////////////////////////////////
void initStopLight() {
  pinMode(GREEN_LED, OUTPUT);
  pinMode(YELLOW_LED, OUTPUT);
  pinMode(RED_LED, OUTPUT);

  testStopLight();
}

/////////////////////////////////////////////////////////////
// Initialize the reset sensor and indication led
/////////////////////////////////////////////////////////////
void initResetSensor() {
  pinMode(RESET_SENSOR, INPUT);
  pinMode(SENSOR_LED, OUTPUT);
  digitalWrite(SENSOR_LED, LOW);
}

/////////////////////////////////////////////////////////////
// Initialize the stop light
/////////////////////////////////////////////////////////////
void initWebServer() {
  // start the Ethernet connection:
  Serial.println("Trying to get an IP address using DHCP");
    // initialize the Ethernet device not using DHCP:
  Ethernet.begin(mac, ip);
  // start the server
  server.begin();
  Serial.print("server is at ");
  Serial.println(Ethernet.localIP());
}

/////////////////////////////////////////////////////////////
// Listen for incoming HTTP connections and handle them
/////////////////////////////////////////////////////////////
void listenForConnection() {
  // listen for incoming clients
  EthernetClient client = server.available();
  if (client) {
    Serial.println("new client");
    // an http request ends with a blank line
    boolean currentLineIsBlank = true;
    int totalRead = 0;
    request[0] = '\0';
    boolean requestRegistered = false;
    while (client.connected()) {
      if (client.available()) {
        
        char c = client.read();
        Serial.write(c);
        
        // We are interested in current line only for the actual request
        if(totalRead < MAX_REQ_LEN && !requestRegistered) {
          if(c =='\r') {
            request[totalRead] = '\0';
            requestRegistered = true;
          }
          else {
            request[totalRead] = c;
            totalRead++;
          }
        }
        
        // if you've gotten to the end of the line (received a newline
        // character) and the line is blank, the http request has ended,
        // so you can send a reply
        if (c == '\n' && currentLineIsBlank) {

          boolean reqResult = handleRequest(request);
          if(reqResult) {
            // send a standard http response header
            client.println("HTTP/1.1 200 OK");
            client.println("Content-Type: text/plain");
            client.println("Connection: close");  // the connection will be closed after completion of the response
            client.println();
            client.println("200 OK");
          }
          else {
            // send a standard http response header
            client.println("HTTP/1.1 405 Method Not Allowed");
            client.println("Content-Type: text/plain");
            client.println("Connection: close");  // the connection will be closed after completion of the response
            client.println();
            client.println("405 Method Not Allowed");
          }
          break;
        }
        if (c == '\n') {
          // you're starting a new line
          currentLineIsBlank = true;
        } else if (c != '\r') {
          // you've gotten a character on the current line
          currentLineIsBlank = false;
        }
      }
    }
    // give the web browser time to receive the data
    delay(1);
    // close the connection:
    client.stop();
    Serial.println("client disconnected");

    Serial.println("Detected request:");
    Serial.println(request);
  }
}

/////////////////////////////////////////////////////////////
// Method that decodes the HTTP request and calls the correct handle function
//
// RETVAL: false -> request is invalid, true - request is valid and it was handled
/////////////////////////////////////////////////////////////
boolean handleRequest (char* msg) {
  // Flag for request validity: false - req invalid; true - req valid
  boolean result = false;
  if(strcmp(msg, "GET /ok/ HTTP/1.1") == 0) {
    handleOkRequest();
    result = true;
  } else if(strcmp(msg, "GET /nok/ HTTP/1.1") == 0) {
    handleNokRequest();
    result = true;
  } else if(strcmp(msg, "GET /start/ HTTP/1.1") == 0) {
    handleStartRequest();
    result = true;
  } else if(strcmp(msg, "GET /stop/ HTTP/1.1") == 0) {
    handleStopRequest();
    result = true;
  } else if(strcmp(msg, "GET /xmas/ HTTP/1.1") == 0) {
    handleXmasRequest();
    result = true;
  } else if(strcmp(msg, "GET /all/ HTTP/1.1") == 0) {
    handleAllRequest();
    result = true;
  } else if(strcmp(msg, "GET /warn/ HTTP/1.1") == 0) {
    handleWarnRequest();
    result = true;
  } else {
    result = false;
  }

  return result;
}

/////////////////////////////////////////////////////////////
// Handle the RESET sensor
/////////////////////////////////////////////////////////////
void handleResetSensor() {
  // read the state of the pushbutton value:
  sensorState = digitalRead(RESET_SENSOR);

  // check if the is activated:
  if (sensorState == LOW) {
    // turn LED on:
    digitalWrite(SENSOR_LED, HIGH);
    disableStopLight();
  } else {
    // turn LED off:
    digitalWrite(SENSOR_LED, LOW);
  }
}

/////////////////////////////////////////////////////////////
// TEST routine for the stop light
/////////////////////////////////////////////////////////////
void testStopLight() {
  disableStopLight();

  // Leds are connected view a relay, outputs are inverted
  // Enable each color for 1 sec -> GREEN -> YELLOW -> RED
  digitalWrite(GREEN_LED, LOW);   // turn the LED on
  delay(1000);                       // wait for a second
  digitalWrite(GREEN_LED, HIGH);    // turn the LED off
  digitalWrite(YELLOW_LED, LOW);   // turn the LED on
  delay(1000);                       // wait for a second
  digitalWrite(YELLOW_LED, HIGH);    // turn the LED off
  digitalWrite(RED_LED, LOW);   // turn the LED on
  delay(1000);
  digitalWrite(RED_LED, HIGH);    // turn the LED off 

  disableStopLight();
}

/////////////////////////////////////////////////////////////
// Disable the stop light
/////////////////////////////////////////////////////////////
void disableStopLight() {
  digitalWrite(GREEN_LED, HIGH);    // turn the LED off
  digitalWrite(YELLOW_LED, HIGH);    // turn the LED off
  digitalWrite(RED_LED, HIGH);    // turn the LED off
}

/////////////////////////////////////////////////////////////
// Handle the OK request by enabling the GREEN light
/////////////////////////////////////////////////////////////
void handleOkRequest() {
  disableStopLight();
  digitalWrite(GREEN_LED, LOW);    // turn the LED on
}

/////////////////////////////////////////////////////////////
// Handle the NOK request by enabling the RED light
/////////////////////////////////////////////////////////////
void handleNokRequest() {
  disableStopLight();
  digitalWrite(RED_LED, LOW);    // turn the LED on
}

/////////////////////////////////////////////////////////////
// Handle the XMAS request by enabling the lights
/////////////////////////////////////////////////////////////
void handleXmasRequest() {
  disableStopLight();
    for (int i = 0; i <= 10; i++) {
        delay(100);
    digitalWrite(YELLOW_LED, HIGH);    // turn the LED on
    digitalWrite(RED_LED, LOW);    // turn the LED on
        delay(100);
    digitalWrite(RED_LED, HIGH);    // turn the LED on
    digitalWrite(GREEN_LED, LOW);    // turn the LED on
        delay(100);
    digitalWrite(GREEN_LED, HIGH);    // turn the LED on
    digitalWrite(YELLOW_LED, LOW);    // turn the LED on
    }
      disableStopLight();
}

/////////////////////////////////////////////////////////////
// Handle the ALL request by enabling the lights
/////////////////////////////////////////////////////////////
void handleAllRequest() {
  disableStopLight();
    for (int i = 0; i <= 10; i++) {
        delay(100);
    digitalWrite(YELLOW_LED, HIGH);    // turn the LED on
    digitalWrite(RED_LED, HIGH);    // turn the LED on
    digitalWrite(GREEN_LED, HIGH);    // turn the LED on
        delay(100);
    digitalWrite(RED_LED, LOW);    // turn the LED on
    digitalWrite(GREEN_LED, LOW);    // turn the LED on
    digitalWrite(YELLOW_LED, LOW);    // turn the LED on
    }
      disableStopLight();
}

/////////////////////////////////////////////////////////////
// Handle the WARN request by enabling the lights
/////////////////////////////////////////////////////////////
void handleWarnRequest() {
  disableStopLight();
    for (int i = 0; i <= 10; i++) {
        delay(100);
    digitalWrite(YELLOW_LED, HIGH);    // turn the LED on
        delay(100);
    digitalWrite(YELLOW_LED, LOW);    // turn the LED on
    }
      disableStopLight();
}

/////////////////////////////////////////////////////////////
// Handle the START request by enabling the RED light
/////////////////////////////////////////////////////////////
void handleStopRequest() {
  disableStopLight();
}

/////////////////////////////////////////////////////////////
// Handle the START request by enabling the RED light
/////////////////////////////////////////////////////////////
void handleStartRequest() {
  disableStopLight();
  digitalWrite(YELLOW_LED, LOW);    // turn the LED on
}

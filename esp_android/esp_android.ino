#include <ESP8266WiFi.h>
WiFiServer server(80);

const char* ssid = "PUT_YOUR_SSID_HERE";
const char* password = "PUT_YOUR_PASSWORD_HERE";

#define led D1
boolean nyala = false;
void setup() {
  // put your setup code here, to run once:
  Serial.begin(115200);
  pinMode(led, OUTPUT);
  connectWiFi();
  server.begin();
}

void loop() {
  // put your main code here, to run repeatedly

  WiFiClient client = server.available();
  if (!client) {
    return;
  }

  Serial.println("new client");
  while(!client.available()){
    delay(1);
  }
  String req = client.readStringUntil('\r');
  Serial.println(req);
  client.flush();

  if(req.indexOf("/on") > 0){
    Serial.println("Nyala");
    nyala = true;
    digitalWrite(led,HIGH);
  }else if(req.indexOf("/off") > 0){
    Serial.println("Mati");
    nyala = false;
    digitalWrite(led,LOW);
  }

  client.flush();

   // Return the response
  client.println("HTTP/1.1 200 OK");
  client.println("Content-Type: text/html");
  client.println(""); //  do not forget this one
 
  if(nyala) {
    client.print("on");
  } else {
    client.print("off");
  }
  Serial.println("Client disonnected");
}

void connectWiFi()
{
  Serial.println("Connecting to WIFI");
  WiFi.begin(ssid, password);
  while ((!(WiFi.status() == WL_CONNECTED)))
  {
    delay(300);
    Serial.print("..");
  }
  Serial.println("");
  Serial.println("WiFi connected");
  Serial.println("NodeMCU Local IP is : ");
  Serial.print((WiFi.localIP()));
}

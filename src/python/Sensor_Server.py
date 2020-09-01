import socket
import Adafruit_DHT

HOST = '127.0.0.1'
PORT = 8000
gpio=17

humidity, temperature = Adafruit_DHT.read_retry(11, gpio)
if humidity is not None and temperature is not None:
  print('Temp={0:0.1f}*C  Humidity={1:0.1f}%'.format(temperature, humidity))
else:
  print('Failed to get reading. Try again!')

humidity = 12
temperature = 15
humidity2 = str(humidity).encode("utf-8")
temperature2 = str(temperature).encode("utf-8")
with socket.socket(socket.AF_INET, socket.SOCK_STREAM) as s:
    s.bind((HOST, PORT))
    s.listen()
    conn, addr = s.accept()
    with conn:
        print('Connected by', addr)
        conn.send(humidity2)
        conn.send('\n'.encode("utf-8"))
        conn.send(temperature2)
        print(humidity)
        print(temperature)
        
            
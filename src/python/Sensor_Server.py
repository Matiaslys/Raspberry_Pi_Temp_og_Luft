import socket
import Adafruit_DHT

HOST = '127.0.0.1'
PORT = 8000
gpio=17

humidity, temperature = Adafruit_DHT.read_retry(sensor, gpio)
if humidity is not None and temperature is not None:
  print('Temp={0:0.1f}*C  Humidity={1:0.1f}%'.format(temperature, humidity))
else:
  print('Failed to get reading. Try again!')

with socket.socket(socket.AF_INET, socket.SOCK_STREAM) as s:
    s.bind((HOST, PORT))
    s.listen()
    conn, addr = s.accept()
    with conn:
        print('Connected by', addr)
        conn.sendall(humidity)
        conn.sendall(temperature)
        print(humidity)
        print(temperature)
        
            
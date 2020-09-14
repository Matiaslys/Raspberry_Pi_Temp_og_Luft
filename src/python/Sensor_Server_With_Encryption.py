import socket
import threading
import time
from Crypto.Cipher import AES
import Adafruit_DHT

HOST = '192.168.43.125'
PORT = 8000
gpio=22

delay = 2

with socket.socket(socket.AF_INET, socket.SOCK_STREAM) as s:
    s.bind((HOST, PORT))
    s.listen()
    conn, addr = s.accept()
    with conn:
        obj = AES.new('This is a key123', AES.MODE_CFB, 'This is an IV4567')
        humidity, temperature = Adafruit_DHT.read_retry(11, gpio)
        if humidity is not None and temperature is not None:
            print('Temp={0:0.1f}*C  Humidity={1:0.1f}%'.format(temperature, humidity))
        else:
            print('Failed to get reading. Try again!')
        print('Connected by', addr)
        count = 0
        humidity2 = str(humidity).encode("utf-8")
        temperature2 = str(temperature).encode("utf-8")
        ciphertext = obj.encrypt(humidity2)
        print(ciphertext)
        ciphertext2 = obj.encrypt(temperature2)
        print(ciphertext2)
        conn.send(ciphertext)
        conn.send('\n'.encode("utf-8"))
        conn.send(ciphertext2)
        conn.send('\n'.encode("utf-8"))
        print(humidity)
        print(temperature)
        
        while True:
            time.sleep(delay)
            humidity, temperature = Adafruit_DHT.read_retry(11, gpio)
            if humidity is not None and temperature is not None:
                print('Temp={0:0.1f}*C  Humidity={1:0.1f}%'.format(temperature, humidity))
            else:
                print('Failed to get reading. Try again!')
            count += 1
            humidity2 = str(ciphertext2).encode("utf-8")
            temperature2 = str(ciphertext).encode("utf-8")
            ciphertext = obj.encrypt(humidity2)
            ciphertext2 = obj.encrypt(temperature2)
            conn.send(ciphertext)
            conn.send('\n'.encode("utf-8"))
            conn.send(ciphertext2)
            conn.send('\n'.encode("utf-8"))
            print(humidity)
            print(temperature)
        
            
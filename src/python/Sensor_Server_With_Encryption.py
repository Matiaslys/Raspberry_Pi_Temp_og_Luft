import socket
import threading
import time
import base64
from Crypto.Cipher import AES
import Adafruit_DHT

HOST = '192.168.43.125'
PORT = 8000
gpio = 22

delay = 2

def pad(byte_array):
    BLOCK_SIZE = 16
    pad_len = BLOCK_SIZE - len(byte_array) % BLOCK_SIZE
    return byte_array + (bytes([pad_len]) * pad_len)

def encrypt(message):

    byte_array = message.encode("UTF-8")

    padded = pad(byte_array)

    obj = AES.new('This is a key123', AES.MODE_CFB, 'This is an IV456')

    
  #  humidity2 = str(humidity).encode("UTF-8")
   # temperature2 = str(temperature).encode("UTF-8")

    #padded = pad(humidity2)
    #padded = pad(temperature2)

    encrypted = obj.encrypt(padded)
    message = obj.encrypt(padded)
    print(message)
    return base64.b64encode('This is an IV456'+encrypted).decode("UTF-8")

with socket.socket(socket.AF_INET, socket.SOCK_STREAM) as s:
    s.bind((HOST, PORT))
    s.listen()
    conn, addr = s.accept()

    with conn:
        obj = AES.new('This is a key123', AES.MODE_CFB, 'This is an IV456')
        humidity, temperature = Adafruit_DHT.read_retry(11, gpio)
        if humidity is not None and temperature is not None:
            print('Temp={0:0.1f}*C  Humidity={1:0.1f}%'.format(temperature, humidity))
        else:
            print('Failed to get reading. Try again!')
        print('Connected by', addr)
        count = 0


        conn.send(encrypt(temperature))
        conn.send('\n'.encode("UTF-8"))
        conn.send(encrypt(humidity))
        conn.send('\n'.encode("UTF-8"))
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
            conn.send(encrypt(temperature))
            conn.send('\n'.encode("UTF-8"))
            conn.send(encrypt(humidity))
            conn.send('\n'.encode("UTF-8"))
            print(humidity)
            print(temperature)
        
            
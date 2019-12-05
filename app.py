from flask import *
import RPi.GPIO as GPIO
from gpiozero import MotionSensor
from picamera import PiCamera
import time
from threading import Thread
from datetime import datetime

pir = MotionSensor(14)
camera = PiCamera()
camera.rotation = 180

app = Flask(__name__)

if __name__ == "__main__":
	app.run()


# initialize security system.
@app.route('/init', methods=["POST"])
def initGerald():
	global detect;
	detect = True;

	# create thread to constantly check for motion detection to keep server responsive
	global detected
	detected = False
	# add method to motion sensor to set detected bool to true on motion detection
	pir.when_motion = setDetected
	#bgThread = Thread(target=detectMotion)
	#bgThread.start()

	return "Connection okay. Security System Initialized"


# route returns boolean on if motion has been detected to app
@app.route('/checkDetection', methods=['GET'])
def checkDetection():
	global detected
	print (detected)
	return str(detected)

# method sets detected variable to true on motion detection
def setDetected():
	global detected
	detected = True


# route to stop motion detection
@app.route('/stopDetection', methods=['POST'])
def stopDetection():
	global detect
	detect = False
	return "Stopped Motion Detection"

# uses the attached pi camera to take picture
# return its unique ID (exact time taken) to user
@app.route('/getImage/<string:key>', methods=['GET'])
def getImage(key):
	# check validity of request
	# only android app has access to encrypted key
	# key encrypted with SHA-256 alg
	if key == "d2f747a10ab09354956653c28dd09fb03880990b211ce8e53b7c5d6507e8b1c9":
		timestamp = datetime.now()
		imgID = timestamp.strftime("%d-%m-%Y_%H:%M:%S")
		fileLoc = "/home/pi/Desktop/final/static/" + imgID + ".jpg"
		camera.capture(fileLoc)
		return str("/static/" + imgID + ".jpg")
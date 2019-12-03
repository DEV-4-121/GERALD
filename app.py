from flask import *
import RPi.GPIO as GPIO
from gpiozero import MotionSensor
import Record
import time
from threading import Thread
import random

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
	bgThread = Thread(target=detectMotion)
	bgThread.start()

	return "Connection okay. Security System Initialized"


# route returns boolean on if motion has been detected to app
@app.route('/checkDetection', methods=['GET'])
def checkDetection():
	global detected
	return str(detected)


# route to stop motion detection
@app.route('/stopDetection', methods=['POST'])
def stopDetection():
	global detect
	detect = False
	return "Stopped Motion Detection"



# run method in background to check for motion detection while GERALD is running
def detectMotion():
	while detect:
		# code to check if pi sensor has detected motion
		# if it has, set global bool to true
		if random.uniform(0, 1) > .95:
			global detected
			detected = True
			return "motion detected"

		# update detection every 2 seconds
		time.sleep(2)


# app route used to get snapshot from camera module
@app.route('/getimg', methods=['GET'])
def getImg():
	fileLoc = Record.get_photo()
	return send_file(fileLoc, mimetype='image/jpg') 
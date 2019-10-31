# DEV4
# 10/15/19
# Code to be used to run the motion sensor

# Imports
import RPi.GPIO as GPIO
import time
import Camera

# Pin of motion sensor (CHANGE WHEN HARDWARE ACQUIRED)
pin_motion = 14

# Setup GPIO
GPIO.setmode(GPIO.BCM)
GPIO.setwarnings(False)
GPIO.setup(pin_motion, GPIO.IN)


# Define motion
def motion(pin_motion):
    print("Motion Sensed")
    Camera.beginStream()


# If motion sensed call MOTION method
# Loop to continue sensing infinitely
try:
    GPIO.add_event_detect(pin_motion, GPIO.RISING, callback=motion)
    while 1:
        time.sleep(10)

# Clean up GPIO when done
except KeyboardInterrupt:
    print("INTERRUPT")
    GPIO.cleanup()


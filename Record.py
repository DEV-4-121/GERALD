# DEV4
# Last Updated: 11/01/19
# Source: https://randomnerdtutorials.com/raspberry-pi-motion-detector-photo-capture/
# Code to take photo upon motion sense

# Import the necessary packages
from gpiozero import MotionSensor
from picamera import PiCamera
from time import sleep
from signal import pause

# Create objects that refer to a motion sensor and the PiCamera
pir = MotionSensor(14)
camera = PiCamera()

# Start the camera
camera.rotation = 180
camera.start_preview()

# Image names
i = 0

# Toggle between video and photo burst (0 = video, 1 = photo)
option = 0

# Stop the camera when the pushbutton is pressed
def stop_camera():
    camera.stop_preview()
    # Exit the program
    exit()


# Take photo when motion is detected
def take_photo():
    global i
    i = i + 1
    t = 1
    if option == 0:
        camera.start_recording('/home/pi/Desktop/video_%s.h264' % i)
        camera.wait_recording(5)
        camera.stop_recording()
        print("a video has been taken")
    if option == 1:
        camera.capture('/home/pi/Desktop/burst%s_image%s.jpg' % (i, t))
        sleep(.5)
        t += 1
        camera.capture('/home/pi/Desktop/burst%s_image%s.jpg' % (i, t))
        sleep(.5)
        t += 1
        camera.capture('/home/pi/Desktop/burst%s_image%s.jpg' % (i, t))
        sleep(.5)
        t += 1
        print('A photo burst has been taken')
    sleep(7)


# Assign a function that runs when motion is detected
pir.when_motion = take_photo

pause()

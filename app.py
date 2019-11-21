from flask import *
import Record
#import Live

app = Flask(__name__)

if __name__ == "__main__":
	app.run()


# initialize security system.
# return if motion is detected
@app.route('/init', methods=["POST"])
def recieveCommand():
    print("Connected successfully")
    return "Connection okay"


@app.route('/getimg', methods=['GET'])
def getImg():
	fileLoc = Record.get_photo()
	return send_file(fileLoc, mimetype='image/jpg')
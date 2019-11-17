from flask import *

app = Flask(__name__)

if __name__ == "__main__":
	app.run()

@app.route('/connect', methods=["POST"])
def recieveCommand():
    print("Connected successfully")
    return "Connection okay"
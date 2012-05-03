#!/usr/bin/env python
from subprocess import Popen, PIPE
from plistlib import readPlist
from pprint import pprint

class Fingerprint:
	def __init__(self, macAddress, signalStrength):
		self.macAddress = macAddress
		self.signalStrength = signalStrength
	
	def __str__(self):
		return self.macAddress + " " +  str(self.signalStrength)

def measurePrints():
	AirportPath = '/System/Library/PrivateFrameworks/Apple80211.framework/Versions/Current/Resources/airport'

	proc = Popen([AirportPath, '-s', '-x'], stdout=PIPE)

	ssid_data = readPlist(proc.stdout)
	fingerprints = []

	for element in ssid_data:
		fingerprints.append(Fingerprint(element['BSSID'], element['RSSI']))
	
	return fingerprints
	
for p in measurePrints():
	print p
